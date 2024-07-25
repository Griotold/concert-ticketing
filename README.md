# 1. 동시성 문제 발생 가능 로직
1. 좌석 예약
2. 예약 좌석 결제
3. 포인트 충전, 사용

- 좌석 예약을 기준으로 락을 분석하고자 한다.
- 예약 좌석 결제, 포인트 충전, 포인트 사용 기능은 돈이 걸린 문제라 비즈니스 적으로 굉장히 중요하지만,
- 동시성 문제가 빈번하게 일어날 것 같진 않다고 판단했다.
- 따라서, 좌석 예약에 초점을 맞춰서 락을 분석하겠다.

# 2. 트랜잭션 범위
```kotlin

@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {

    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = getSeat(command.seatId) // 좌석 가져오기
        performanceService.reserve(seat) // 좌석 상태 변경
        reservationService.reserve(command.userId, command.seatId, seat.price) // 예약 생성
    }
}
```
- 좌석을 가져오고 좌석 상태를 변경, 예약을 생성하는 invoke 함수를 하나의 트랜잭션 범위로 본다.
- getSeat()에 락을 걸 것이고, 락을 바꿔보며 실험한다.
- 첫 번째 트랜잭션이 좌석의 상태를 변경하면 나머지는 예외가 발생하여 실패한다.

# 3. 락 분석 - 비관적 락, 낙관적 락, 분산락(Redisson)

## 3 - 1. 비관적 락
- 동작 방식: 실제 DB의 락을 이용하여 하나의 트랜잭션이 락을 들고 있으면 나머지 트랜잭션들은 기다리는 형태
- 장점: 데이터 충돌을 사전에 방지할 수 있어서 동시성 문제를 깔끔하게 해결
- 단점: 락을 유지하는 동안 다른 트랜잭션이 해당 데이터에 접근할 수 없어 성능 저하가 발생할 수 있다.
```kotlin
interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    // 비관적락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SeatEntity s where s.id =:seatId")
    fun findByIdForUpdate(seatId: Long): SeatEntity?

}
```
### 테스트 코드
```kotlin
@Test
fun `좌석 예약 동시성 테스트 3,000명`() {
    // given
    val seatEntity = SeatEntity(
        performanceScheduleId = 1L,
        seatNo = 1,
        price = 10000,
        SeatStatus.OPEN
    )
    seatJpaRepository.save(seatEntity)

    // when
    val userCount = 3_000
    val futures = Array(userCount) { i ->
        runAsync { sut(ReservationCommand.Reserve(userId = i.toLong() + 1, seatId = 1L)) }
    }

    allOf(*futures)
        .exceptionally { null }
        .join()

    // then
    val errorCount = futures.count { it.isCompletedExceptionally }
    assertThat(errorCount).isEqualTo(userCount - 1)

    val reservationCount = reservationJpaRepository.count()
    assertThat(reservationCount).isEqualTo(1)
}
```

- 3000 명이 동시에 좌석을 예약하려고 할 때 상황을 가정한다.
- 1명만 좌석 예약에 성공하고 나머지는 실패한다.
### 결과 - 비관적락
![좌석_예약_비관적락_2024-07-24 171816](https://github.com/user-attachments/assets/ed18de5a-5f5c-4848-a2fc-29660d0847d5)
- 3.541초

## 3 - 2. 낙관적 락
- 동작방식: 실제 DB 락을 사용하는 게 아니라 엔티티에 Version 정보를 저장하여 업데이트시 Version이 다르면 예외를 발생시켜 동시성 문제를 해결
- 장점: DB 락을 사용하지 않기 때문에 성능적으로 좋다.
- 단점: 데이터 충돌이 발생했을 때, 트랜잭션이 실패할 수 있으므로 재시도 처리가 필요할 수 있다.
  - 좌석 예약에 경우 재시도처리가 필요하지 않긴 하다.
  - 나머지 트랜잭션이 실패하는 게 의도니까
### @Version
```kotlin
...
@Entity
@Table(name = "seat")
@EntityListeners(AuditingEntityListener::class)
class SeatEntity(
    ...
) {
    ...
    @Version // 엔티티의 버젼을 관리 --> 낙관적 락
    var version: Long = 0
}
```
- 낙관적 락을 사용하기 위해 엔티티 클래스에 @Version을 넣어준다.
### 테스트 코드
- 테스트 코드는 비관적 락의 테스트코드와 동일하다.
### 결과 - 낙관적락
![좌석_예약_낙관적락_2024-07-24 173014](https://github.com/user-attachments/assets/47a81021-b6a9-48e8-a874-5bd870da59c4)
- 테스트를 돌릴 때마다 2초 ~ 5초를 왔다갔다 했다.

## 3 - 3. 분산락 - Redisson (pub/sub)
- 분산락은 Redis - Redisson 사용하여 구현했다.
- Redisson 라이브러리는 pub/sub 방식을 사용하여 분산락을 구현한다.
- 락이 해제되면 락을 subscribe 하는 클라이언트는 락이 해제되었다는 신호를 받고 락 획득을 시도하는 방식이다.
- 참고자료: https://helloworld.kurly.com/blog/distributed-redisson-lock/
```kotlin
// @Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {

    @DistributedLock(key = "#lockName")
    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = performanceService.getSeat(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }
}
```
- @DistributedLock 어노테이션 만들어서 트랜잭션 범위에 달아준다.
- AOP가 해당 어노테이션을 감지하여 분산락을 적용해주고 로직을 실행시킨다.
- 자세한 사항은 참고자료 확인
### 테스트 코드
- 테스트 코드는 역시 비관적 락, 낙관적 락과 동일하다.
### 결과
![좌석_예약_분산락_Redisson_2024-07-25 101738](https://github.com/user-attachments/assets/3b396f4b-39ce-4b86-94ac-704306b45b34)
- 14.47초
- 이거 왜 이렇게 오래 걸리는걸까? 분산락이 원래 느린가? 뭔가 잘못된 것 같다.
- 아무래도 메서드 전체에 분산락을 걸어 놓아서 오래 걸리는 것 같다.

## 3 - 4. 분산락 - 범위를 좁혀서
- 좌석을 가져오는 로직에만 분산락을 적용해보고 실험해보자.
```kotlin
@Transactional
@UseCase
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService
) {

    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = getSeat(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }

    // 분산 락 여기에만 적용!
    @DistributedLock(key = "#lockName")
    private fun getSeat(seatId: Long) : Seat {
        return performanceService.getSeat(seatId)
    }
}
```
- @DistributedLock 어노테이션이 붙은 메서드는 Propagation.REQUIRES_NEW 옵션을 지정해 부모 트랜잭션의 유무에 관계없이 별도의 트랜잭션으로 동작하게끔 설정했다.
```kotlin
/**
 * AOP에서 트랜잭션 분리를 위한 클래스
 */
@Component
class AopForTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Throws(Throwable::class)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}
```
### 테스트 코드
- 위와 동일
### 결과 - 분산락 - 범위 좁혀서
![좌석_예약_분산락_범위좁혀서_2024-07-25 110632](https://github.com/user-attachments/assets/79ab7858-680f-4060-97e3-d51659d6a524)
- 2~3초

# 4. 결론
- DB 락만 놓고 보자면, 좌석 예약에 경우 충돌이 빈번하게 발생할 여지가 크므로 낙관적 락 보다는 비관적 락이 적당해 보인다.
- 분산락의 경우 트랜잭션 범위 전체에 걸 경우 성능이 매우 안좋았지만, 좌석을 가져오는 로직에만 분산락을 걸도록 하면 성능이 나쁘지 않았다.
- 좌석 예약은 비관적 락이면 충분해 보인다.
- 하지만, 이번 프로젝트에서는 학습차원에서 분산락을 적용해 보려고 한다.

# 5. 앞으로 더 해야할 것
- redis로 분산락을 적용할 때 pub/sub 말고도 spin lock, simple lock 방식으로 할 수도 있다.
  - 다음엔 두 가지 방식도 시도해 보면 좋을 듯 하다.
- @Transactional 에 대한 공부가 더 필요해 보인다.
  - `@Transactional(propagation = Propagation.REQUIRES_NEW)` 는 처음 접해보았다.
  - https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-db-2/dashboard
  - 이 강의 사놓고 안봤는데 스프링 트랜잭션 전파에 대해서 강의좀 보고 학습해야겠다.
- redis 에 대한 공부도 좀 해야겠다.
  - redis도 강의 사놓고 안 본게 있는데 학습해야겠다.
