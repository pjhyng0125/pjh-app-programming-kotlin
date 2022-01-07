package com.pjh.kotlinsample

import java.util.*
import kotlin.reflect.KProperty

class DelegateString {
    var text = ""

    // operator : 연산자 취급
    operator fun getValue(thisRef: Any?, proprty: KProperty<*>): String {
        return text
    }

    operator fun setValue(thisRef: Any?, proprty: KProperty<*>, value: String) {
        text = value.uppercase(Locale.getDefault())
        println("$value ==> ${text}")
    }
}