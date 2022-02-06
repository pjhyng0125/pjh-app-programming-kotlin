package com.pjh.anonymoussns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    // set firebase 'test' key
    val ref = FirebaseDatabase.getInstance().getReference("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref.addValueEventListener(object:ValueEventListener{
            // 데이터 변경 감지된 경우
            override fun onDataChange(snapshot: DataSnapshot) {
                val msg = snapshot.value.toString()
                Log.d(TAG, msg)
                // set title from firebase
                supportActionBar?.title = msg
            }

            // 데이터 읽기 취소된 경우
            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}