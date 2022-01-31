package com.pjh.quizlocker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class FileExActivity : AppCompatActivity() {
    // 변수 선언
    val fileName = "data.txt"
    var granted = false
    val MY_PERMISSION_REQUEST = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_ex)
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val text = findViewById<TextView>(R.id.textField).text.toString()
            when {
                // text 유효성 체크
                TextUtils.isEmpty(text) -> {
                    CmnUtil.showToast("텍스트가 비어있습니다.")
                }
//                !Environment.isExternalStorageEmulated() -> {
                !isExternalStorageWritable() -> {
                    CmnUtil.showToast("외부 저장장치가 없습니다.")
                }
                else -> {
                    // 내부 저장소 파일 저장
//                    saveToInnerStorage(text, fileName)

                    // 외부 저장소 파일 저장
//                    saveToExternalStorage(text, fileName)

                    // 외부 저장소 "sdcard/data.txt" 데이터 저장
                    saveToExternalCustomDirectory(text)
                }
            }
        }
        findViewById<Button>(R.id.loadButton).setOnClickListener {
            try {
                // 내부저장소 파일 데이터 로드
//                findViewById<TextView>(R.id.textField).setText(loadFromExternalCustomDir() + " 로드 성공! (내부저장소)")

                // 외부저장소 앱 전용 디렉토리 파일 데이터 로드
//                findViewById<TextView>(R.id.textField).setText(loadFromExternalStorage(fileName) + " 로드 성공! (외부저장소)")

                // 외부저장소 "sdcard/data.txt" 데이터 로드
                findViewById<TextView>(R.id.textField).setText(loadFromExternalCustomDir() + " 로드 성공! (외부저장소 임의 경로)")
            } catch (e: FileNotFoundException) {
                CmnUtil.showToast("저장된 텍스트가 없습니다.")
            }
        }
    }

    /**
     * 내부 저장소 파일 텍스트 저장
     */
    fun saveToInnerStorage(text: String, fileName: String) {
        val fos =openFileOutput(fileName, Context.MODE_PRIVATE)
        fos.write(text.toByteArray())
        fos.close()
    }

    /**
     * 내부 저장소 파일 텍스트 로드
     */
    fun loadFromExternalCustomDir(): String {
        val fis = openFileInput(fileName)
        return fis.reader().readText()
    }

    /**
     * 외부 저장장치 사용 가능 여부 반환
     */
    fun isExternalStorageWritable(): Boolean {
        when {
            Environment.getExternalStorageState() === Environment.MEDIA_MOUNTED -> return true
            else -> return false
        }
    }

    /**
     * 외부 저장장치 앱 전용 데이터 사용 파일 객체 반환
     */
    fun getAppDataFileFromExternalStorage(fileName: String): File {
        // KITKAT 버전 부터 앱 전용 디렉토리 타입 상수 Environment.DIRECTORY_DOCUMENTS 지원
        val dir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        } else {
            // 하위 버전 직접 디렉토리명 입력
            File(Environment.getExternalStorageDirectory().absolutePath + "/Documents")
        }
        // 없는 디렉토리 생성
        dir?.mkdirs()
        return File("${dir?.absolutePath}${File.separator}${fileName}")
    }

    /**
     * 외부저장소 앱 전용 디렉토리에 파일 저장
     */
    fun saveToExternalStorage(text: String, fileName: String) {
        val fos = FileOutputStream(getAppDataFileFromExternalStorage(fileName))
        fos.write(text.toByteArray())
        fos.close()
    }

    /**
     * 외부저장소 앱 전용 디렉토리 파일 데이터 로드
     */
    fun loadFromExternalStorage(fileName: String): String {
        return FileInputStream(getAppDataFileFromExternalStorage(fileName)).reader().readText()
    }

    /**
     * 권한 체크 및 요청
     */
    fun checkPermission() {
        val permissonCheck = ContextCompat.checkSelfPermission(this@FileExActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        when {
            permissonCheck != PackageManager.PERMISSION_GRANTED -> {
                // 권한 요청
                ActivityCompat.requestPermissions(
                    this@FileExActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSION_REQUEST
                )
            }
        }
    }

    /**
     * 권한요청 결과 콜백
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST -> {
                when {
                    grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        granted = true
                    }
                    else -> {
                        granted = false
                    }
                }
            }
        }
    }

    /**
     * 임의 경로 파일 데이터 저장
     */
    fun saveToExternalCustomDirectory(text: String, filePath: String = "sdcard/data.txt") {
        when {
            // 권한 있는 경우
            granted -> {
                val fos = FileOutputStream(File(filePath))
                fos.write(text.toByteArray())
                fos.close()
            }
            else -> {
                CmnUtil.showToast("권한이 없습니다. (저장)", Toast.LENGTH_SHORT)
            }
        }
    }

    /**
     * 임의 경로 파일 데이터 로드
     */
    fun loadFromExternalCustomDir(filePath: String = "sdcard/data.txt"): String {
        when {
            // 권한 있는 경우
            granted -> {
                return FileInputStream(File(filePath)).reader().readText()
            }
            else -> {
                CmnUtil.showToast("권한이 없습니다. (로드)", Toast.LENGTH_SHORT)
                return ""
            }
        }
    }
}