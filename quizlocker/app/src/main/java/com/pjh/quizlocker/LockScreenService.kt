package com.pjh.quizlocker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder

class LockScreenService : Service() {
    // 화면이 꺼질 때 브로드캐스트 메세지 수신 receiver
    var receiver: ScreenOffReceiver? = null

    private val ANDROID_CHANNEL_ID = "com.pjh.quizlocker"
    private val NOTIFICATION_ID = 9999

    // 서비스 최소 실행 콜백
    override fun onCreate() {
        super.onCreate()
        if (receiver == null) {
            receiver = ScreenOffReceiver()
            val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
            registerReceiver(receiver, filter)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        if (intent != null){
            if (intent?.action == null) {
                // 서비스 최초 실행이 아닐 경우 예외 처리
                if (receiver == null) {
                    receiver = ScreenOffReceiver()
                    val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
                    registerReceiver(receiver, filter)
                }
            }
        }

        // Oreo 버전부터 백그라운드 제약 있기 때문에, 포그라운드 서비스 실행 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 상단 알림 채널 생성
            val chan = NotificationChannel(ANDROID_CHANNEL_ID, "LockScreenService", NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            // Notification 서비스 객체 설정
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)

            // Notification 알림 객체 생성
            val builder = Notification.Builder(this, ANDROID_CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("SmartTracker Running")
            val notification = builder.build()

            // Notification 알림과 함께 포그라운드 서비스 시작
            startForeground(NOTIFICATION_ID, notification)
        }
        return Service.START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()

        // 서비스 종료 시 브로드캐스트 리시버 등록 해제
        if (receiver != null) {
            unregisterReceiver(receiver)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}