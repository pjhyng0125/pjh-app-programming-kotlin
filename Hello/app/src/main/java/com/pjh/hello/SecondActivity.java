package com.pjh.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    Car c1 = new Car(3,100,4);
    SportCar c2 = new SportCar(10,50,8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 버튼1 클릭 이벤트 추가
        findViewById(R.id.tBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.acceleration();
                c2.acceleration();
                String info = "car1: " + c1.getCurSpeedStr() + ", car2: " + c2.getCurSpeedStr();
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                c2.openSunRoof();
                Toast.makeText(getApplicationContext(), c2.getSunRoofInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        // 버튼2 클릭 이벤트 추가
        findViewById(R.id.tBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.breakPedal();
                c2.breakPedal();
                String info = "car1: " + c1.getCurSpeedStr() + ", car2: " + c2.getCurSpeedStr();
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                c2.closeSunRoof();
                Toast.makeText(getApplicationContext(), c2.getSunRoofInfo(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}