package com.pjh.kotlinsample

class Sample {
    val field1 = 0
    
    // class : 중첩 클래스 선언
    class NestedClass {
        // Unresolved reference: field1
        // 중첩 클래스에서 외부 클래스 속성 접근 불가
//        val myF = field1
    }
    
    // inner class : 내부 클래스 선언
    inner class InnerClass {
        // 내부 클래스에서 외부 클래스 속성 접근 가능
        val myF = field1
    }
}