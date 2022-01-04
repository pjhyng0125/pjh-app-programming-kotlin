package com.pjh.kotlinsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ControlJavaActivity extends AppCompatActivity {

    EditText numberField;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 레이아웃 xml 설정
        setContentView(R.layout.layout_control);

        // 멤버변수 바인딩
        numberField = findViewById(R.id.numberField);
        button = findViewById(R.id.button6);

        // 클릭 이벤트 등록
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberField.getText().toString());
                // 조건 분기
                if (number % 2 == 0) {
                    Toast.makeText(getApplicationContext(), "" + number + "는 2의 배수입니다.", Toast.LENGTH_SHORT).show();
                } else if (number % 3 == 0) {
                    Toast.makeText(getApplicationContext(), "" + number + "는 3의 배수입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "" + number, Toast.LENGTH_SHORT).show();
                }

                // 텍스트 변경
                switch (number) {
                    case 4:
                        button.setText("실행 - 4");
                        break;
                    case 9:
                        button.setText("실행 - 9");
                        break;
                    default:
                        button.setText("실행");
                        break;
                }
            }
        });

    }
}