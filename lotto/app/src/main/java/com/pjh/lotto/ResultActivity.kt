package com.pjh.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    val lottoImageStartId = R.drawable.ball_01
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
//        showToast("ResultActivity 입니다.")
        val resultLabel = findViewById<TextView>(R.id.resultLabel)
        val result = intent.getIntegerArrayListExtra("result")
        val name = intent.getStringExtra("name")
        val constellation = intent.getStringExtra("constellation")
        resultLabel.text = "랜덤으로 생성된\n로또번호입니다."
        // 이름 화면 전달
        if (!TextUtils.isEmpty(name)) {
            resultLabel.text = "${name} 님의\n${SimpleDateFormat("yyyy년 MM월 dd일").format(Date())}\n로또번호입니다."
        }
        // 별자리 화면 전달
        if (!TextUtils.isEmpty(constellation)) {
            resultLabel.text = "${constellation} 의\n${SimpleDateFormat("yyyy년MM월dd일").format(Date())}\n로또번호입니다."
        }
        result?.let {
            updateLottoBallImage(result.sortedBy {it} )
        }
    }

    /**
     * 결과에 따라 공 이미지 업데이트
     */
    fun updateLottoBallImage(result: List<Int>) {
        // 개수 예외처리
        if (result.size < 6) return

        // 변수 설정
        val lottoNumber1 = findViewById<ImageView>(R.id.lottoNumber1)
        val lottoNumber2 = findViewById<ImageView>(R.id.lottoNumber2)
        val lottoNumber3 = findViewById<ImageView>(R.id.lottoNumber3)
        val lottoNumber4 = findViewById<ImageView>(R.id.lottoNumber4)
        val lottoNumber5 = findViewById<ImageView>(R.id.lottoNumber5)
        val lottoNumber6 = findViewById<ImageView>(R.id.lottoNumber6)

        // 이미지 설정
        lottoNumber1.setImageResource(lottoImageStartId + (result[0] - 1))
        lottoNumber2.setImageResource(lottoImageStartId + (result[1] - 1))
        lottoNumber3.setImageResource(lottoImageStartId + (result[2] - 1))
        lottoNumber4.setImageResource(lottoImageStartId + (result[3] - 1))
        lottoNumber5.setImageResource(lottoImageStartId + (result[4] - 1))
        lottoNumber6.setImageResource(lottoImageStartId + (result[5] - 1))
    }
}