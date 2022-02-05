package com.pjh.quizlocker

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.MultiSelectListPreference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.util.Log
import android.widget.Button
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val fragment = MyPreferenceFragment()
    val selectedCategorySet = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager.beginTransaction().replace(R.id.preferenceContent, fragment).commit()

        findViewById<Button>(R.id.initButton).setOnClickListener { initAnswerCnt() }
    }

    fun initAnswerCnt() {
        // 정답/오답 횟수 설정 정보 get
        val correctAnswerPref = getSharedPreferences("correctAnswer", Context.MODE_PRIVATE)
        val wrongAnswerPref = getSharedPreferences("wrongAnswer", Context.MODE_PRIVATE)

        // 초기화
        correctAnswerPref.edit().clear().apply()
        wrongAnswerPref.edit().clear().apply()
    }

    class MyPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref)
            val categoryPref = findPreference("category") as MultiSelectListPreference
            categoryPref.summary = categoryPref.values.joinToString(", ")
            categoryPref.setOnPreferenceChangeListener { preference, newValue ->
                val newValueSet = newValue as? HashSet<*>
                    ?: return@setOnPreferenceChangeListener true
                categoryPref.summary = newValue.joinToString(", ")
                true
            }

            // 퀴즈 잠금화면 사용 스위치 객체 설정
            val useLockScreenPref = findPreference("useLockScreen") as SwitchPreference
            // 클릭 이벤트 리너스 등록
            useLockScreenPref.setOnPreferenceClickListener {
                when {
                    // 퀴즈 잠금화면 check
                    useLockScreenPref.isChecked -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            activity.startForegroundService(Intent(activity, LockScreenService::class.java))
                        } else {
                            activity.startService(Intent(activity, LockScreenService::class.java))
                        }
                    }
                    else -> activity.stopService(Intent(activity, LockScreenService::class.java))
                }
                true
            }
            // 앱 시작 시, 퀴즈 잠금화면 체크 시 서비스 실행
            if (useLockScreenPref.isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity.startForegroundService(Intent(activity, LockScreenService::class.java))
                } else {
                    activity.startService(Intent(activity, LockScreenService::class.java))
                }
            }
        }
    }
}