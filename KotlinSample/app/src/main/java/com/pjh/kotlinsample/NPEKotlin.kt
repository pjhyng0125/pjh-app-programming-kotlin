package com.pjh.kotlinsample

// 코틀린은 기본적으로 null 허용 X
fun strLenNonNull(str: String): Int {
    return str.length
}

// ? : Null 가능성이 있는 타입
fun strLenNullable(str: String?): Int {
    // null 체크하면 Type? -> Type 스마트 캐스팅
    if (str != null) {
        return str.length
    } else {
        return 0
    }
}

fun strLastCharNullable(str: String): Char? {
    // ?. 연산자는 str이 NULL이면 null이 반환된다.
    // typeScript에서는 undefined 반환
    return str?.get(str.length - 1)
}

fun strLastCharNullable(str: String?): Char {
    // ?: 엘비스 연산자
    return str?.get(str.length - 1) ?: "".single()
}

fun strPrintLen(str: String) {
    // ?.let : 반환객체 검사하고 람다 param 전달
    // str이 null일 경우, 람다 실행 X
    str?.let { print(strLenNonNull(it)) }
}