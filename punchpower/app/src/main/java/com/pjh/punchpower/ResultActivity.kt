package com.pjh.punchpower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    // 펀치력 저장 변수
    val power by lazy {
        intent.getDoubleExtra("power", 0.0) * 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        title = "펀치력 결과"

        scoreLabel.text = "${String.format("%.0f", power)} 점"

        // 다시하기 버튼 클릭 시, 현재 Activity 종료
        restartButton.setOnClickListener {
            finish()
        }
    }
}