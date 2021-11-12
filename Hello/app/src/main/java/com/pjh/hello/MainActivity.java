package com.pjh.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 클릭 카운트
    int clickCnt = 0;

    /**
     * MainActivity 가 최초 실행될 때 실행.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 토스트 메세지 출력
        Toast.makeText(getApplicationContext(), "안드로이드! 부순다!", Toast.LENGTH_LONG).show();

        // 버튼 클릭 이벤트 추가
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCnt = clickCnt + 1;

                if (clickCnt % 2 == 0) {
                    Toast.makeText(getApplicationContext(), "clickCnt" + clickCnt, Toast.LENGTH_SHORT).show();
                } else if (clickCnt % 3 == 0) {
                    Toast.makeText(getApplicationContext(), "hello world!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}