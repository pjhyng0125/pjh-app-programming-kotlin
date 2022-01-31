package com.pjh.quizlocker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class PreExActivity : AppCompatActivity() {
    // 데이터 저장 key 선언
    val nameFieldKey = "nameField"
    val pushCheckBoxKey = "pushCheckBox"
    // shared preference 객체, Activity 초기화 이후에 사용해야 하기 때문에 lazy 위임 활용
    // 이름으로 서로 공유
    val preference by lazy { getSharedPreferences("PreExActivity", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_ex)

        // 저장 버튼 클릭
        findViewById<Button>(R.id.saveButton2).setOnClickListener {
            preference.edit().putString(nameFieldKey, findViewById<TextView>(R.id.nameField).text.toString()).apply()
            preference.edit().putBoolean(pushCheckBoxKey, findViewById<CheckBox>(R.id.pushCheckBox).isChecked).apply()
        }

        // 불러오기 버튼 클릭
        findViewById<Button>(R.id.loadButton2).setOnClickListener {
            findViewById<TextView>(R.id.nameField).setText(preference.getString(nameFieldKey, ""))
            findViewById<CheckBox>(R.id.pushCheckBox).isChecked = preference.getBoolean(pushCheckBoxKey, false)
        }
    }
}