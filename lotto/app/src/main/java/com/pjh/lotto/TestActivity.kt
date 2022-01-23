package com.pjh.lotto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        // Main click event 설정
        findViewById<View>(R.id.button).setOnClickListener {
            // MainActivity를 시작하는 Intent를 생성
            val intent = Intent(this@TestActivity, MainActivity::class.java)
            // intent를 사용하여 Activity 시작
            startActivity(intent)
        }

        // Constellation click event 설정
        findViewById<View>(R.id.button2).setOnClickListener {
            val intent = Intent(this@TestActivity, ConstellationActivity::class.java)
            startActivity(intent)
        }

        // Name click event 설정
        findViewById<View>(R.id.button3).setOnClickListener {
            val intent = Intent(this@TestActivity, NameActivity::class.java)
            startActivity(intent)
        }

        // Result click event 설정
        findViewById<View>(R.id.button4).setOnClickListener {
            val intent = Intent(this@TestActivity, ResultActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * xml에서 참조할 수 있게 메소드 정의
     */
    fun goConstellation(view: View) {
        val intent = Intent(this@TestActivity, ConstellationActivity::class.java)
        startActivity(intent)
    }

    /**
     * 암시적 intent를 사용해 web 호출
     */
    fun callWeb(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://naver.com"))
        startActivity(intent)
    }
}