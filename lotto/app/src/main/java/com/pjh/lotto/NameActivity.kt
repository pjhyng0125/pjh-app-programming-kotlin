package com.pjh.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
//        showToast("NameActivity 입니다.")

        // 결과화면 이동
        findViewById<View>(R.id.goButton).setOnClickListener {
            // name 예외 처리
            val et = findViewById<TextView>(R.id.editText)
            if (TextUtils.isEmpty(et.text.toString())) {
                showToast("이름을 입력해주세요.");
                return@setOnClickListener
            }
            // ResultActivity 시작하는 Intent를 생성
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(LottoNumberMaker.getLottoNumbersFromHash(et.text.toString())))
            intent.putExtra("name", et.text.toString())
            startActivity(intent)
        }

        // 뒤로가기
        findViewById<View>(R.id.backButton).setOnClickListener {
            // Activity 종료
            finish()
        }
    }
}