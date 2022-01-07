package com.pjh.kotlinsample

// Decorator Pattern : 특정 클래스의 기능에 추가 기능을 덧붙이는 방법   
// by : Collection 인터페이스의 기능을 innerList에 위임
// [Java] Collection - ArrayList (kotlin 에서 불변성 )
// [Kotlin] MutableCollection - MutableArrayList (가변성)
class DelegatingArrayList<T>(val innerList: MutableCollection<T> = mutableListOf()) : MutableCollection<T> by innerList {
    override fun add(element: T): Boolean {
        // 확장 기능을 실행
        printItem(element)
        return innerList.add(element)
    }

    fun printItem(item:T) {
        println(item.toString())
    }
}