package com.pjh.kotlinsample

class Animal(val map:MutableMap<String, Any?>) {
    // Getter/Setter 역할 Map 위임
    var name: String by map
    var age: Int by map
}