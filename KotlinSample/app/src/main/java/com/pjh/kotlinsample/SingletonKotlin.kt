package com.pjh.kotlinsample

// object -> 해당 클래스가 싱글턴임 => 복잡한 코드 생략 가능
object SingletonKotlin {
    fun log(text: String) {
        println(text)
    }
}