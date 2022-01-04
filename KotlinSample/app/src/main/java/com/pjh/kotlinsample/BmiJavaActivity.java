package com.pjh.kotlinsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiJavaActivity extends AppCompatActivity {
    EditText tallField;
    EditText weightField;
    TextView resultLabel;
    Button bmiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_view_binding);

        tallField = findViewById(R.id.tall);
        bmiButton = findViewById(R.id.bmiBtn);
        weightField = findViewById(R.id.weight);
        resultLabel = findViewById(R.id.resultLabel);

        bmiButton.setOnClickListener(v -> {
                String tall = tallField.getText().toString();
                String weight = weightField.getText().toString();
                double bmi = Double.parseDouble(weight) / Math.pow(Double.parseDouble(tall) /100.0,2);
                resultLabel.setText("키: " + tall + ", 체중: " + weight + ", BMI: " + bmi);
            });
    }
}