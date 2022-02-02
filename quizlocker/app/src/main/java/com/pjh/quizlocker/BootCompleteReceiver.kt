package com.pjh.quizlocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast

class BootCompleteReceiver : BroadcastReceiver() {
    // 브로드캐스트 메세지 수신 콜백
    override fun onReceive(context: Context?, intent: Intent?) {
        // 부팅 완료 메세지 확인
        when {
            intent?.action == Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("quizlocker", "[퀴즈 잠금화면] 부팅 완료!")
//                Toast.makeText(context, "[퀴즈 잠금화면] 부팅 완료!", Toast.LENGTH_LONG).show()
//                CmnUtil.showToast("[퀴즈 잠금화면] 부팅 완료!")

                context?.let {
                    // 퀴즈잠금화면 설정값 ON 확인
                    val pref = PreferenceManager.getDefaultSharedPreferences(context)
                    val useLockScreen = pref.getBoolean("useLockScreen", false)
                    if (useLockScreen) {
                        // LockScreenService 시작
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            it.startForegroundService(Intent(context, LockScreenService::class.java))
                        } else {
                            it.startActivity(Intent(context, LockScreenService::class.java))
                        }
                    }
                }
            }
        }
    }
}