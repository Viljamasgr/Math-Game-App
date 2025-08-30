package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity {

    Button btnEasy;
    Button btnMed;
    Button btnHard;
    Button btnVHard;
    Button btnGoBak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnEasy = findViewById(R.id.btnEasy);
        btnMed = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        btnVHard = findViewById(R.id.btnVeryHard);
        btnGoBak = findViewById(R.id.btnQGoBack);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(QuizActivity.this, Questions.class);
                quiz.putExtra("level",1);
                startActivity(quiz);
            };
        });

        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(QuizActivity.this, Questions.class);
                quiz.putExtra("level",2);
                startActivity(quiz);
            };
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(QuizActivity.this, Questions.class);
                quiz.putExtra("level",3);
                startActivity(quiz);
            };
        });

        btnVHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(QuizActivity.this, Questions.class);
                quiz.putExtra("level",4);
                startActivity(quiz);
            };
        });
        //Go back button function on the quiz activity
        btnGoBak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
    }
}