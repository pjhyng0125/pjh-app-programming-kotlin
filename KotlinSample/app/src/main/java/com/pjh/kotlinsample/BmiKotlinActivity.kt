package com.pjh.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class BmiKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_view_binding)

        val tall = findViewById<EditText>(R.id.tall)
        val weight = findViewById<EditText>(R.id.weight)
        val resultLabel = findViewById<TextView>(R.id.resultLabel)
        val bmiBtn = findViewById<Button>(R.id.bmiBtn)

        bmiBtn.setOnClickListener {
            val bmi = weight.text.toString().toDouble() / Math.pow(tall.text.toString().toDouble() / 100, 2.0)
            resultLabel.text = "키: ${tall.text.toString().toDouble()}, 체중: ${weight.text.toString().toDouble()}, BMI: $bmi"
        }
    }
}