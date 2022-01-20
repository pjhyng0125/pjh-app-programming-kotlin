package com.pjh.kotlinsample

// 코틀린은 기본적으로 클래스 상속 불가
// open 키워드를 통해 상속 허용 가능
// 캡슐화 위반 방지
open class ExtendKotlin {
    var property1 = 0

    // 메소드 default final 이며, 오버라이드 불가능
    fun disable() {}

    // 상속 가능
    open fun able() {}

    // 오버라이드 가능
    override fun toString(): String {
        return super.toString()
    }
}