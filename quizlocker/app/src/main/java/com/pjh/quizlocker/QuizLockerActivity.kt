package com.pjh.quizlocker

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class QuizLockerActivity : AppCompatActivity() {
    var quiz:JSONObject? = null

    // 정답 횟수 저장 SharedPreference
    val correctAnswerPref by lazy { getSharedPreferences("correctAnswer", Context.MODE_PRIVATE) }
    // 오답 횟수 저장 SharedPreference
    val wrongAnswerPref by lazy { getSharedPreferences("wrongAnswer", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz_locker)

        // 잠금화면보다 상단에 위치 설정 (버전에 따라 사용법 다름)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // 잠금화면에서 보여지도록 설정
            setShowWhenLocked(true)
            // 잠금 해제
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        }
        // 화면 켜진 상태 유지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_quiz_locker)

        // 퀴즈 분류 get
        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.getAppContext())
        val quizCategoryArr = pref.getStringSet("category", hashSetOf())
        Log.d("quizlocker", "[quizCategoryArr] 퀴즈 분류 get 테스트 : ${quizCategoryArr}")

        // 퀴즈 분류 분기
        // TODO: JSONArray 병합 방법 찾아보기
        var mergedList = ArrayList<Any>() as List<Any>
        quizCategoryArr?.let {
            for (item in quizCategoryArr) {
                if (item.contains("수도")) {
                    val jsonArr = getQuizArr("capital.json")
                    mergedList = mergedList.plus(ArrayUtil.convert(jsonArr))
                } else if (item.contains("역사")) {
                    val jsonArr = getQuizArr("history.json")
                    mergedList = mergedList.plus(ArrayUtil.convert(jsonArr))
                } else if (item.contains("일반상식")) {
                    val jsonArr = getQuizArr("commonSense.json")
                    mergedList = mergedList.plus(ArrayUtil.convert(jsonArr))
                }
            }
        }

        Log.d("quizlocker", "[mergedList] 병합 List : ${mergedList}")

        // 퀴즈 선택
        val convertedArr = ArrayUtil.convert(mergedList)
        quiz = convertedArr.getJSONObject(Random().nextInt(convertedArr.length()))

        // 퀴즈 show
        findViewById<TextView>(R.id.quizLabel).text = quiz?.getString("question")
        findViewById<TextView>(R.id.choice1).text = quiz?.getString("choice1")
        findViewById<TextView>(R.id.choice2).text = quiz?.getString("choice2")

        // 정답/오답 횟수 show
        val id = quiz?.getInt("id").toString() ?: ""
        findViewById<TextView>(R.id.correctCountLabel).text = "정답횟수 : ${correctAnswerPref.getInt(id, 0)}"
        findViewById<TextView>(R.id.wrongCountLabel).text = "정답횟수 : ${wrongAnswerPref.getInt(id, 0)}"

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val leftImageView = findViewById<ImageView>(R.id.leftImageView)
        val rightImageView = findViewById<ImageView>(R.id.rightImageView)

        // SeekBar 변경 이벤트 등록
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when {
                    // SeekBar 우측 선택
                    progress > 95 -> {
                        leftImageView.setImageResource(R.drawable.padlock)
                        rightImageView.setImageResource(R.drawable.unlock)
                    }
                    // SeekBar 좌측 선택
                    progress < 5 -> {
                        leftImageView.setImageResource(R.drawable.unlock)
                        rightImageView.setImageResource(R.drawable.padlock)
                    }
                    else -> {
                        leftImageView.setImageResource(R.drawable.padlock)
                        rightImageView.setImageResource(R.drawable.padlock)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar?.progress ?: 50
                when {
                    // 우측 답 선택
                    progress > 95 -> checkChoice(quiz?.getString("choice2") ?: "")
                    // 좌측 답 선택
                    progress < 5 -> checkChoice(quiz?.getString("choice1") ?: "")
                    else -> seekBar?.progress = 50
                }
            }
        })
    }

    // 퀴즈 데이터 JSONArray get
    fun getQuizArr(jsonFileNm: String): JSONArray {
        var json = assets.open(jsonFileNm).reader().readText()
        val quizJsonArr = JSONArray(json)
        return quizJsonArr
    }

    // 정답 체크
    fun checkChoice(choice: String) {
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val leftImageView = findViewById<ImageView>(R.id.leftImageView)
        val rightImageView = findViewById<ImageView>(R.id.rightImageView)
        // if (quiz != null)
        quiz?.let {
            when {
                // 정답일 경우 Activity 종료
                choice == it.getString("answer") -> {
                    // 정답 횟수 증가
                    val id = it.getInt("id").toString()
                    var cnt = correctAnswerPref.getInt(id, 0)
                    cnt++
                    correctAnswerPref.edit().putInt(id, cnt).apply()
                    findViewById<TextView>(R.id.correctCountLabel).text = "정답횟수 : ${cnt}"

                    finish()
                }

                else -> {
                    // 오답 횟수 증가
                    val id = it.getInt("id").toString()
                    var cnt = wrongAnswerPref.getInt(id, 0)
                    cnt++
                    wrongAnswerPref.edit().putInt(id, cnt).apply()
                    findViewById<TextView>(R.id.wrongCountLabel).text = "오답횟수 : ${cnt}"

                    // 정답이 아닌 경우 UI 초기화
                    leftImageView.setImageResource(R.drawable.padlock)
                    rightImageView.setImageResource(R.drawable.padlock)
                    seekBar?.progress = 50

                    // 진동 알람 추가
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    // SDK 버전 26 이상의 경우 진동 횟수, 강도 설정 가능
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, 100))
                    } else {
                        vibrator.vibrate(1000)
                    }
                }
            }
        }
    }
}