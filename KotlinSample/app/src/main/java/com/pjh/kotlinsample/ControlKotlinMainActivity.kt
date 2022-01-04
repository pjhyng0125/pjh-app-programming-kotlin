package com.pjh.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ControlKotlinMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_control)

        val button = findViewById<Button>(R.id.button7)
        val numberField = findViewById<TextView>(R.id.numberField)
        button.setOnClickListener { 
            val number = numberField.text.toString().toInt()
            
            if (number % 2 == 0) {
                Toast.makeText(applicationContext, "${number} 는 2의 배수 입니다!", Toast.LENGTH_SHORT).show()
            } else if (number % 3 == 0) {
                Toast.makeText(applicationContext, "${number} 는 3의 배수 입니다!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "${number}", Toast.LENGTH_SHORT).show()
            }
            
            when (number) {
                in 1..4 -> button.text = "실행 - 4"
                9, 18 -> {
                    button.text = "실행 - 9"
                }
                else -> button.text = "실행"
            }
        }
    }
}