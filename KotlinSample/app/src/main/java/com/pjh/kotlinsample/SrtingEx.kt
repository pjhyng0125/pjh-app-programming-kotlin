@file:JvmName("StringUtil")
package com.pjh.kotlinsample
// String 클래스에 lastString 확장함수 추가 정의
// public 선언 -> 어디서나 접근 가능 -> 최상위 선언
fun String.lastString():String {
    // String : 수신 객체의 타입 (this)
    return this.get(this.length - 1).toString()

    class ExtTest {
        // ExtTest 내부 선언 -> 클래스 외부 사용 불가
        fun String.extFunc() {
            println(this.lastString())
        }

        fun strTest() {
            "test".extFunc()
        }
    }

    fun outTest() {
        "test".lastString()
        // 확장 함수 extFunc 접근 불가
        // "test".extFunc() // Unresolved reference: extFunc
    }
}