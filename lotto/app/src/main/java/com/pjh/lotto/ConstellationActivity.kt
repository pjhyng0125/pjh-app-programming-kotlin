package com.pjh.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ConstellationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)
        showToast("ConstellationActivity 입니다.")
    }
}