@file:JvmName("ToastUtil")
/* 코틀린 최상위 함수 선언 및 util 클래스명 지정 */
package com.pjh.kotlinsample

import android.widget.Toast

    fun toastShort(msg:String) {
        Toast.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg:String) {
        Toast.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show()
    }

    @JvmOverloads
    fun toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(MainApplication.getAppContext(), msg, length).show()
    }