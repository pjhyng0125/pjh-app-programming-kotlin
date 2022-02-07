package com.pjh.anonymoussns


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.card_background.view.*

class WriteActivity : AppCompatActivity() {
    // 선택된 배경 이미지 position 저장 변수 선언
    var currentBgPosition = 0

    // 배경 리스트 데이터
    val bgList = mutableListOf(
        "android.resource://com.pjh.anonymoussns/drawable/default_bg"
        , "android.resource://com.pjh.anonymoussns/drawable/bg2"
        , "android.resource://com.pjh.anonymoussns/drawable/bg3"
        , "android.resource://com.pjh.anonymoussns/drawable/bg4"
        , "android.resource://com.pjh.anonymoussns/drawable/bg5"
        , "android.resource://com.pjh.anonymoussns/drawable/bg6"
        , "android.resource://com.pjh.anonymoussns/drawable/bg7"
        , "android.resource://com.pjh.anonymoussns/drawable/bg8"
        , "android.resource://com.pjh.anonymoussns/drawable/bg9"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        supportActionBar?.title = "글쓰기"

        // recycleView 사용 layout 설정
        val layoutManager = LinearLayoutManager(this@WriteActivity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MyAdapter()

        sendButton.setOnClickListener {
            if (TextUtils.isEmpty(input.text)) {
                Toast.makeText(applicationContext, "메세지를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val post = Post()
            val newRef = FirebaseDatabase.getInstance().getReference("Posts").push()
            post.writeTime = ServerValue.TIMESTAMP // 서버 기준 시간
            post.bgUri = bgList[currentBgPosition]
            post.message = input.text.toString()
            post.writerId = getMyId()
            post.postId = newRef?.key!!
            newRef.setValue(post)
            Toast.makeText(applicationContext, "공유되었습니다.", Toast.LENGTH_SHORT).show()

        }
    }
    
    // 디바이스 ID 반환 (회원 구분)
    fun getMyId(): String {
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // RecyclerView 사용 View 홀더 클래스
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageView2
    }

    // RecyclerView Adapter 클래스
    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            //
            return MyViewHolder(LayoutInflater.from(this@WriteActivity).inflate(R.layout.card_background, parent, false))
        }

        override fun getItemCount(): Int {
            return bgList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            Picasso.get()
                .load(Uri.parse(bgList[position]))
                .fit()
                .centerCrop()
                .into(holder.imageView)

            holder.itemView.setOnClickListener {
                // 선택된 배경의 포지션을 currentBgPosition 에 저장
                currentBgPosition = position
                // 이미지 로딩 라이브러리인 피카소 객체로 뷰홀더에 존재하는 글쓰기 배경 이미지뷰에 이미지 로딩
                Picasso.get()
                    .load(Uri.parse(bgList[position]))
                    .fit()
                    .centerCrop()
                    .into(writeBackground)
            }
        }
    }
}