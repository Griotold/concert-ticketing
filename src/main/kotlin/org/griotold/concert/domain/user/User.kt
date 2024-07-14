package org.griotold.concert.domain.user

data class User(
    val userId: Long,
    val name: String,
    val point: UserPoint,
) {

    /**
     * copy 를 쓰면 객체의 특정 필드만 변경하여 새로운 객체를 생성하는데 유용하다.
     * */
    fun chargePoint(amount: Int): User {
        return this.copy(point = point.charge(amount))
    }

    fun usePoint(amount: Int): User {
        return this.copy(point = point.use(amount))
    }
}