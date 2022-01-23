package com.pjh.lotto
import android.widget.Toast
@JvmOverloads
fun showToast(msg:String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MainApplication.getAppContext(), msg, length).show()
}