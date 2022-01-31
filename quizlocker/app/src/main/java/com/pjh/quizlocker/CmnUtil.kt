package com.pjh.quizlocker

import android.widget.Toast

object CmnUtil {
    fun showToast(msg:String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(MainApplication.getAppContext(), msg, length).show()
    }
}