# API 명세
## 1. 대기열 토큰 발급
- /queue
- POST
### Request Body
```
{

        "memberId" : number

}
```
### Response Body
```
{

        "token" : string,
        "memberId": number,
        "waitingNum": number

}
```
## 2. 예약 가능 날짜 조회
- /concerts/{concertId}
- GET
### Request Header
- token 포함
### Response Body
```
{

        "concertId" : number,
        "dates": [
                {"concertScheduleId" : number, "concertStartAt" : string},
                {"concertScheduleId" : number, "concertStartAt" : string}
        ]
}
```
## 2 - 2. 예약 가능 좌석 조회
- /concerts/{concertId}/{concertScheduleId}
- GET
### Request Header
- token 포함
### Response Body
```
{

        "concertScheduleId" : number,
        "seats": [
                {"seatId" : number, "seatNum" : number},
                {"seatId" : number, "seatNum" : number}
        ]
}
```
## 3. 좌석 예약 
- /reservations
- POST
### Request Header
- token 포함
### Request Body
```
{
  "concertId" : number,
  "concertScheduleId" : number,
  "seatId" : number,
  "memberId" : number
}
```
### Response Body
```
{
  "reservationId" : number,
  "concertInfo" : {
                            "concertId" : number,
                            "concertScheduleId" : number,
                            "name" : string,
                            "concertStartAt" : string,
                            "seatId" : number,
                            "seatNum" : number
                         }
}
```
## 4. 잔액 충전
- /members/charge/{memberId}
- PUT
### Request Body
```
{
  "amount" : number
}
```
### Response Body
```
{
  "memberId" : number,
  "transaction_type" : string,
   "amount" : number
}
```
## 4 - 2. 잔액 조회
- /members/points/{memberId}
- GET
### Response Body
```
{
  "memberId" : number,
  "points" : number
}
```
## 5. 결제
- /payments/{reservationId}
- POST
### Request Header
- token 포함
### Request Body
```
{
  "memberId" : number
}
```
### Response Body
```
{
  "paymentId" : number
  "paymentPrice" : number
  "status" : string,
  "paidAt" : String
}
```
