package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnBegin;
    Button btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBegin = findViewById(R.id.btnBegin);
        btnLeaderboard = findViewById(R.id.btnLeaderboard); //finding the leaderboard button

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(quiz);
            }
        });

        //creating a function to start the activity leaderboard when the button is clicked
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent board = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(board);
            }
        });
    }
}