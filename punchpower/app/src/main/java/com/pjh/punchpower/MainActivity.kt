package com.pjh.punchpower

import android.animation.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var maxPower = 0.0
    var isStart = false
    var startTime = 0L

    // Sensor 관리자 객체 lazy 선언 -> 실제 사용 시 초기화
    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // Sensor 이벤트 처리 리스너
    val eventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                // 측정된 Sensor 값이 선형 가속도 타입이 아니면 반환
                if (event.sensor.type != Sensor.TYPE_LINEAR_ACCELERATION) return@let
                // 각 좌표 제곱 -> 음수 제거, 값 차이 극대화
                val power = Math.pow(event.values[0].toDouble(), 2.0) + Math.pow(event.values[1].toDouble(), 2.0) + Math.pow(event.values[2].toDouble(), 2.0)
                // 측정 펀치력이 20을 넘고 측정이 시작되지 않은 경우
                if (power > 20 && !isStart) {
                    // 측정 시간
                    startTime = System.currentTimeMillis()
                    isStart = true
                }
                // 측정 시간된 경우
                if (isStart) {
                    // Animation 제거
                        imageView.clearAnimation()
                    // 5초간 최대값을 측정. 현재측정된 값이 지금까지 측정된 최대 값보다 크면 최대값을 현재 값으로 변경.
                    if (maxPower < power) maxPower = power
                    // 측정 중인 것을 사용자에게 알려줌
                    stateLabel.text = "펀치력을 측정하고 있습니다"
                    // 최초 측정후 3초가 지났으면 측정을 끝낸다.
                    if (System.currentTimeMillis() - startTime > 3000) {
                        isStart = false
                        punchPowerTestComplete(maxPower)
                    }
                }
            }
        }
    }

    // Activity 최초 로시 시 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Activity 재로드 시 호출
    override fun onStart() {
        super.onStart()
        initGame()
    }

    // 게임 초기화
    fun initGame() {
        maxPower = 0.0
        isStart = false
        startTime = 0L
        stateLabel.text = "핸드폰을 손에쥐고 주먹을 내지르세요!!"
        // Sensor 변화값 처리 리스너 등록
        // TYPE_LINEAR_ACCELERATION : 중력값 제외, x/y/z 측정된 가속도만 계산
        sensorManager.registerListener(
            eventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        // Animation 시작
//        imageView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.tran))
//        imageView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.rotate))
//        imageView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.alpha_scale))
//        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.alpha_scale)
//        imageView.startAnimation(animation)

        // Animation 콜백 함수 설정
//        animation.setAnimationListener(object: Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {
//                // 반복
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                // 종료
//            }
//
//            override fun onAnimationStart(animation: Animation?) {
//                // 시작
//            }
//        })

        // Animator 시작
//        AnimatorInflater.loadAnimator(this@MainActivity, R.animator.property_animation).apply {
        AnimatorInflater.loadAnimator(this@MainActivity, R.animator.color_anim).apply {
            // 배경색 변경 Animator 적용
            val colorAnimator = this@apply as? ObjectAnimator
            colorAnimator?.apply {
                setEvaluator(ArgbEvaluator())
                target = window.decorView.findViewById(android.R.id.content)
            }
            start()
//            addListener(object : AnimatorListenerAdapter() {
//                // Animation 종료 시
//                override fun onAnimationEnd(animation: Animator?) {
////                    super.onAnimationEnd(animation)
//                    start()
//                }
//            })
//            // 속성 애니메이션 target을 글로브 이미지 뷰로 지정
//            setTarget(imageView)
//            start()
        }
    }

    // 펀치력 측정 완료
    fun punchPowerTestComplete(power: Double) {
        Log.d("MainActivity", "측정완료 : power ${String.format("%.5f", power)}")
        sensorManager.unregisterListener(eventListener)
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("power", power)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        try {
            sensorManager.unregisterListener(eventListener)
        } catch (e: Exception) {
            Log.e("MainActivity", "onStop Exception message : ${e.message}")
        }
    }
}