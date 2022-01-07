package com.pjh.kotlinsample

import java.io.InputStreamReader
import java.net.URL
import kotlin.properties.Delegates

class User {
    // 클래스 위임
    // 프로퍼티는 Field 로 정해지는 것이 아니라, 접근자인 Getter/Setter 에 의해 결정
    var nickname by DelegateString()

    // val 로 선언된 경우, Val cannot be reassigned 오류 발생
    // 위임 클래스에 getValue만 operator 선언 되어야 함.
    val nickvalue by DelegateString()

    // lazy : 게으른 초기화 시점 => 인스턴스 생성이 아니라 프로퍼티 사용 시점 (성능 고려)
    // lazy는 반드시 val 키워드로 선언되어야 함
    val httpText by lazy {
        println("lazy init start")
        InputStreamReader(URL("https://www.naver.com").openConnection().getInputStream()).readText()
    }

    // lateinit
    // observer pattern : 관찰 대상에 변경 사항이 생길 경우, 변경 사실을 관찰자에게 공유
    // name 프로퍼티가 변경될 때마다 자동으로 obserable 코드 실행
    var name: String by Delegates.observable("") {
        property, oldValue, newValue ->
        println("old: ${oldValue}, new: ${newValue}")
    }
}