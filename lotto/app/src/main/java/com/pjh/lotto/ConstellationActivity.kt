package com.pjh.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class ConstellationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)
//        showToast("ConstellationActivity 입니다.")
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val textView = findViewById<TextView>(R.id.textView)

        // 결과화면 이동
        findViewById<View>(R.id.goResultButton).setOnClickListener {
            // ResultActivity 시작하는 Intent를 생성
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(LottoNumberMaker.getLottoNumbersFromHash(makeConstellationStr(datePicker.month, datePicker.dayOfMonth))))
            intent.putExtra("constellation", makeConstellationStr(datePicker.month, datePicker.dayOfMonth))
            startActivity(intent)
        }
        textView.text = makeConstellationStr(datePicker.month, datePicker.dayOfMonth)
        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        object : CalendarView.OnDateChangeListener, DatePicker.OnDateChangedListener {
            override fun onDateChanged(
                view: DatePicker?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
                // 변경된 시점의 DatePicker 월, 일 정보 텍스트 변경
                textView.text = makeConstellationStr(datePicker.month, datePicker.dayOfMonth)
            }

            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
            }
        })
    }

    /**
     * 월, 일 정보 기준 별자리 반환
     */
    fun makeConstellationStr(month: Int, day: Int): String {
        // 1월29일 --> 129
        val target = "${month + 1}${String.format("%02d", day)}".toInt()
        when(target) {
            in 101..119 -> return "염소자리"
            in 120..218 -> return "물병자리"
            in 219..320 -> return "물고기자리"
            in 321..419 -> return "양자리"
            in 420..520 -> return "황소자리"
            in 521..621 -> return "쌍둥이자리"
            in 622..722 -> return "게자리"
            in 723..822 -> return "사자자리"
            in 823..922 -> return "처녀자리"
            in 924..1022 -> return "천칭자리"
            in 1023..1122 -> return "전갈자리"
            in 1123..1224 -> return "사수자리"
            in 1225..1231 -> return "염소자리"
            else -> return "기타별자리"
        }
    }
}