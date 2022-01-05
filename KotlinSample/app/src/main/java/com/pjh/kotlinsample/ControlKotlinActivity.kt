package com.pjh.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ControlKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_control)

        val button = findViewById<Button>(R.id.button6)
        val numberField = findViewById<TextView>(R.id.numberField)
        button.setOnClickListener { 
            val number = numberField.text.toString().toInt()

            when {
                number % 2 == 0 -> toast("[kotlin] ${number} 는 2의 배수 입니다!");
                number % 3 == 0 -> toast(msg = "[kotlin] ${number} 는 3의 배수 입니다!", length = Toast.LENGTH_LONG);
                else -> toastShort("[kotlin] ${number}");
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