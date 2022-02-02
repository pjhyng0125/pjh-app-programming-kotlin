package com.pjh.quizlocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScreenOffReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when {
            // 화면이 꺼질 때 브로드캐스트 메세지
            intent?.action == Intent.ACTION_SCREEN_OFF -> {
//                Log.d("ScreenOffReceiver", "[퀴즈 잠금] 화면 off")
//                CmnUtil.showToast("[퀴즈 잠금] 화면 off")
                // 화면 off 시, QuizLockerActivity 실행
                val intent = Intent(context, QuizLockerActivity::class.java)
                // 새로운 Activity 실행
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                // 기존 Activity Stack 제거
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context?.startActivity(intent)
            }
        }
    }
}