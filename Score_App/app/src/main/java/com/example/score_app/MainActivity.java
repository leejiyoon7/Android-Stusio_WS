package com.example.score_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    Button btnAdd, btnSub, btnReset;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = (TextView)findViewById(R.id.txtScore);
        scoreView.setText(String.valueOf(score));
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreView.setText(String.valueOf(score));
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score--;
                scoreView.setText(String.valueOf(score));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                scoreView.setText(String.valueOf(score));
            }
        });
    }
}
