package com.pjh.kotlinsample

class Truck(val id: Int, val name: String) {
    // equlas 오버라이드, id가 같으면 같은 객체 취급
    override fun equals(other: Any?): Boolean {
        // as : 타입 캐스팅
        // as? : 연산자를 사용하면 타입이 같은경우 캐스팅이 정상적으로 실행
        // 캐스팅 실패 시 null 반환
        val otherTruck = other as? Truck ?: return false
        return otherTruck.id === id
    }
}