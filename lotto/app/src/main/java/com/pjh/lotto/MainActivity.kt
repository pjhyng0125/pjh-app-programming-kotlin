package com.pjh.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        showToast("MainActivity 입니다.")
        // 랜덤번호생성 카드 클릭
        findViewById<View>(R.id.randomCard).setOnClickListener {
            // ResultActivity 시작하는 Intent를 생성
            val intent = Intent(this, ResultActivity::class.java)
//            intent.putIntegerArrayListExtra("result", ArrayList(getRandomLottoNumbers()))
            intent.putIntegerArrayListExtra("result", ArrayList(LottoNumberMaker.getShuffleLottoNumbers()))
            startActivity(intent)
        }
        // 별자리번호생성 카드 클릭
        findViewById<View>(R.id.constellationCard).setOnClickListener {
            // ConstellationActivity 시작하는 Intent를 생성
            val intent = Intent(this, ConstellationActivity::class.java)
            startActivity(intent)
        }
        // 이름번호생성 카드 클릭
        findViewById<View>(R.id.nameCard).setOnClickListener {
            // NameActivity 시작하는 Intent를 생성
            val intent = Intent(this, NameActivity::class.java)
            startActivity(intent)
        }
    }
}