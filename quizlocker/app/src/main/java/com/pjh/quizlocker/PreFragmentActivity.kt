package com.pjh.quizlocker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceFragment

class PreFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_fragment)
        // Activity Content View를 MyPreFragment로 변경
//        supportFragmentManager.beginTransaction().replace(android.R.id.content, MyPreFragment()).commit()
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreFragment()).commit()
    }

    // PreferenceFragment : xml로 작성한 Preference를 UI로 보여주는 class
    class MyPreFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pre_ex)
        }
    }
}