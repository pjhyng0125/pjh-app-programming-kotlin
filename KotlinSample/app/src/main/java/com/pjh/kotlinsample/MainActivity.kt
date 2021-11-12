package com.pjh.kotlinsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       findViewById<Button>(R.id.button1).setOnClickListener {
            // 버튼1 클릭 시 수행
            startActivity(Intent(this@MainActivity, BmiJavaActivity::class.java))
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            // 버튼2 클릭 시 수행
            startActivity(Intent(this@MainActivity, BmiKotlinActivity::class.java))
        }
    }
}