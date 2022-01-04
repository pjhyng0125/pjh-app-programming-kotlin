package com.pjh.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class VariableKotlinActivity : AppCompatActivity() {
    var clickCount = 0
    val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_variable)
        val timeText = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(startTime)
        val startTimeLabel = findViewById<TextView>(R.id.startTimeLabel)
        val clickCountLabel = findViewById<TextView>(R.id.clickCountLabel)
        val button = findViewById<Button>(R.id.button)

        startTimeLabel.text = "Activity 시작시간: ${timeText}"
        clickCountLabel.text = "버튼클릭횟수 1: ${clickCount}"

        button.setOnClickListener {
            clickCount = clickCount + 1
            clickCountLabel.text = "버튼클릭횟수 2: ${clickCount}"
        }
    }
}