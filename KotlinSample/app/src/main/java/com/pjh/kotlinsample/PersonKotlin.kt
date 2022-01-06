package com.pjh.kotlinsample

import java.util.*

class PersonKotlin(val name: String) {
    var age: Int = 0 // var : get/set 전부 생성
//    val name: String // val : get만 생성 (final, const 와 동일한 개념)
//    constructor(name: String) {
//        this.name= name
//    }
    var nickname: String = ""
        set(value) {
            field = value.lowercase(Locale.getDefault())
        }
}