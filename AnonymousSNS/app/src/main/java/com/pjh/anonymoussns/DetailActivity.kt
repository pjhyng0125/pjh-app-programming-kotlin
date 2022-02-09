package com.pjh.anonymoussns

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.card_post.*

class DetailActivity : AppCompatActivity() {
    val commentList = mutableListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val postId = intent.getStringExtra("postId")

        val layoutManager = LinearLayoutManager(this@DetailActivity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MyAdapter()

        FirebaseDatabase.getInstance().getReference("/Posts/$postId")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot?.let {
                        val post = it.getValue(Post::class.java)
                        post?.let {
                            Picasso.get().load(it.bgUri)
                            contentsText2.text = post.message
                        }
                    }
                }
            })

        FirebaseDatabase.getInstance().getReference("/Comments/$postId")
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {
                    error?.toException()?.printStackTrace()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot != null) {
                        val comment = snapshot.getValue(Comment::class.java)
                        comment?.let {
                            val existIndex = commentList.map { it.commentId }.indexOf(it.commentId)
                            commentList.removeAt(existIndex)

                            val prevIndex =
                                commentList.map { it.commentId }.indexOf(previousChildName)
                            commentList.add(prevIndex + 1, it)
                            recyclerView.adapter?.notifyItemInserted(prevIndex + 1)
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot?.let { snapshot ->
                        val comment = snapshot.getValue(Comment::class.java)
                        comment?.let { comment ->
                            val prevIndex =
                                commentList.map { it.commentId }.indexOf(previousChildName)
                            commentList[prevIndex + 1] = comment
                            recyclerView.adapter?.notifyItemChanged(prevIndex + 1)
                        }
                    }
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot?.let { snapshot ->
                        // snapshop 의 데이터를 Post 객체로 가져옴
                        val comment = snapshot.getValue(Comment::class.java)
                        comment?.let {
                            // 새 글이 마지막 부분에 추가된 경우
                            // 글이 중간에 삽입된 경우 prevChildKey 로 한단계 앞의 데이터의 위치를 찾은 뒤 데이터를 추가
                            val prevIndex =
                                commentList.map { it.commentId }.indexOf(previousChildName)
                            commentList.add(prevIndex + 1, comment)
                            // RecyclerView 의 adapter 에 글이 추가된 것을 알림
                            recyclerView.adapter?.notifyItemInserted(prevIndex + 1)
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    snapshot?.let {
                        // snapshot 의 데이터를 Post 객체로 가져옴
                        val comment = snapshot.getValue(Comment::class.java)
                        comment?.let { comment ->
                            // 기존에 저장된 인덱스를 찾아서 해당 인덱스의 데이터를 삭제한다.
                            val existIndex =
                                commentList.map { it.commentId }.indexOf(comment.commentId)
                            commentList.removeAt(existIndex)
                            recyclerView.adapter?.notifyItemRemoved(existIndex)
                        }
                    }
                }
            })

        // 하단 댓글쓰기 버튼에 클릭 이벤트 리스너 설정
        floatingActionButton2.setOnClickListener {
            // 글쓰기 화면으로 이동할 Intent 생성
            val intent = Intent(this@DetailActivity, WriteActivity::class.java)
            // 글쓰기 화면에서 댓글쓰기 인것을 인식할수 있도록 글쓰기 모드를 comment 로 전달
            intent.putExtra("mode", "comment")
            // 글의 ID 를 전달
            intent.putExtra("postId", postId)
            // 글쓰기 화면 시작
            startActivity(intent)
        }
    }
        // RecyclerView 에서 사용하는 View 홀더 클래스
        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView = itemView.findViewById<ImageView>(R.id.background)
            val commentText = itemView.findViewById<TextView>(R.id.commentText)
        }

        // RecyclerView 의 어댑터 클래스
        inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
            // RecyclerView 에서 각 Row(행)에서 그릴 ViewHolder 를 생성할때 불리는 메소드
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                // RecyclerView 에서 사용하는 ViewHolder 클래스를 card_background 레이아웃 리소스 파일을 사용하도록 생성한다.
                return MyViewHolder(LayoutInflater.from(this@DetailActivity).inflate(R.layout.card_comment, parent, false))
            }

            // RecyclerView 에서 몇개의 행을 그릴지 기준이 되는 메소드
            override fun getItemCount(): Int {
                return commentList.size
            }

            // 각 행의 포지션에서 그려야할 ViewHolder UI 에 데이터를 적용하는 메소드
            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                val comment = commentList[position]
                comment?.let {
                    // 이미지 로딩 라이브러리인 피카소 객체로 뷰홀더에 존재하는 imageView 에 이미지 로딩
                    Picasso.get()
                        .load(Uri.parse(comment.bgUri))
                        .fit()
                        .centerCrop()
                        .into(holder.imageView)
                    holder.commentText.text = comment.message
                }
            }
        }
}