package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Questions extends AppCompatActivity {

    TextView question;
    int level = 1;
    TextView questionNum;
    Button btn;
    private List<QuestionClass> questions;
    int totalQuestions;
    int count = 0;
    private QuestionClass current;

    boolean answered;

    EditText guess;

    EditText username;
    TextView score;
    int scoreAmount = 0;

    TextView result;

    long startTime;
    long endTime;

    private Chronometer chronometer;

    ArrayList<LeaderboardClass> leaderboard;

    String updateURL = "https://mathgameapi.azurewebsites.net/leaderboard/update/";
    String summaryURL = "https://mathgameapi.azurewebsites.net/summary/update/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("level");
        }
        questions = new ArrayList<>();
        generateQuestions(level);
        getLeaderboard();

        chronometer = findViewById(R.id.chronometer);
        btn = findViewById(R.id.btnSubmit);
        question = findViewById(R.id.Q);
        questionNum = findViewById(R.id.Question);
        guess = findViewById(R.id.Input);
        score = findViewById(R.id.Score);
        totalQuestions = questions.size();
        result = findViewById(R.id.Result);

        displayNextQuestion();
        startTime = System.currentTimeMillis();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (guess.getText().toString().trim().length() > 0) {
                        validateGuess();
                    }
                    else {
                        result.setText(R.string.Please_enter_in_a_guess);
                        result.setTextColor(Color.BLACK);
                    }
                }
                else {
                    displayNextQuestion();
                }
            }
        });

    }

    private void generateQuestions(int level) {
        Random r = new Random();
        String symbols[];
        int min;
        int max;

        if (level == 1) {
            //making it so level 1 is only addition
            symbols = new String[]{"+"};
            min = 0;
            max = 50;
        }
        else if (level == 2) {
            //making it so level 2 is addition and subtraction
            symbols = new String[]{"+", "-"};
            min = 0;
            max = 50;
        }
        else if (level == 3) {
            //making it so level 3 is addition, subtraction and multiplication
            symbols = new String[]{"+", "-", "*"};
            min = 0;
            max = 50;
        }
        else if (level == 4) {
            //making is so level 4 is addition, subtraction, multiplication and division
            symbols = new String[]{"+", "-", "*", "/"};
            min = 0;
            max = 50;
        }
        else {
            symbols = new String[]{};
            max =0;
            min = 0;
        }

        for (int i = 0; i < 10; i++) {
            int ans;
            int num1;
            int num2;
            String symbol = symbols[r.nextInt(symbols.length)];

            if (symbol == "/") {
                num2 = r.nextInt(9) + 1; // single-digit number
                num1 = num2 * (r.nextInt(10) + 2); // big number
                ans = num1 / num2;
            }
            else if (symbol == "*") {
                num1 = r.nextInt(12) + 1; // numbers up to 12
                num2 = r.nextInt(12) + 1;
                ans = num1 * num2;
            }
            else if (symbol == "+") {
                num1 =  r.nextInt(max - min + 1) + min;
                num2 =  r.nextInt(max - min + 1) + min;
                ans = num1 + num2;
            }
            else {
                num1 =  r.nextInt(max - min + 1) + min;
                num2 =  r.nextInt(num1 - min + 1) + min;
                ans = num1 - num2;
            }
            String q = num1 + " " + symbol + " " + num2;
            QuestionClass question = new QuestionClass(q, ans);
            questions.add(question);
        }
    }

    private void displayNextQuestion() {
        if (count < totalQuestions) {
            current = questions.get(count);
            question.setText(current.getQuestion());
            count++;
            questionNum.setText(getResources().getString(R.string.question) + " " + count);
            answered = false;
            btn.setText(R.string.submit);
            guess.setText(null);
            result.setText(null);
        }
    }

    private void getLeaderboard() {
        String leaderboardLevel;
        if (level == 1) {
            leaderboardLevel = "easy";
        }
        else if (level == 2) {
            leaderboardLevel = "medium";
        }
        else if (level == 3) {
            leaderboardLevel = "hard";
        }
        else {
            leaderboardLevel = "veryhard";
        }
        updateURL = updateURL + leaderboardLevel + "/";
        summaryURL = summaryURL + leaderboardLevel + "/";
        String leaderboardURL = "https://mathgameapi.azurewebsites.net/leaderboard/" + leaderboardLevel;

        // Call api for the leaderboard level
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(leaderboardURL,"Getting Leaderboard");

            try {
                StringRequest request = new StringRequest(Request.Method.GET, leaderboardURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                leaderboard = new Gson().fromJson(response, new TypeToken<List<LeaderboardClass>>(){}.getType());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(leaderboardURL, "Error "+ error.toString());
                            }
                        });
                queue.add(request);
            }
            catch (Exception e) {
                Log.d(leaderboardURL, e.toString());
            }
        }
        catch (Exception e1) {
            Log.d(leaderboardURL, e1.toString());
        }
    }

    private boolean hasHigherScore(double totalTime) {
        LeaderboardClass minValue = null;
        int scoreMin = 30;
        double timeMin = 0;
        for (int i = 0; i < leaderboard.size(); i++) {
            if (leaderboard.get(i).score <= scoreMin) {
                if (leaderboard.get(i).time >= timeMin) {
                    minValue = leaderboard.get(i);
                    System.out.println(minValue);
                }
            }
        }

        if (minValue != null) {
            if (scoreAmount >= minValue.score) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    private void validateGuess() {
        answered = true;
        int answer = Integer.parseInt(guess.getText().toString());
        if (answer == current.getAnswer()) {
            result.setText(R.string.correct);
            result.setTextColor(Color.GREEN);
            scoreAmount ++;
            score.setText(getResources().getString(R.string.score) + " " + scoreAmount);
        }
        else {
            result.setText(R.string.incorrect);
            result.setTextColor(Color.RED);
        }

        if (count < totalQuestions) {
            btn.setText(R.string.next);
        }
        else {
            endTime = System.currentTimeMillis();
            double totalTime = (endTime - startTime) / 1000.0;
            btn.setText(R.string.finish);
            question.setVisibility(View.INVISIBLE);
            chronometer.setVisibility(View.INVISIBLE);
            if (hasHigherScore(totalTime)) {
                result.setText(getResources().getString(R.string.you_scored)  + " " + scoreAmount +
                        " " + getResources().getString(R.string.out_of) + " " + totalQuestions +
                        " " + getResources().getString(R.string.in) + " " + totalTime + " " +
                        getResources().getString(R.string.seconds) + " " +
                        getResources().getString(R.string.please_enter));
                result.setTextColor(Color.BLACK);
                guess.setVisibility(View.INVISIBLE);
                username = findViewById(R.id.username);
                username.setVisibility(View.VISIBLE);
            }
            else {
                result.setText(getResources().getString(R.string.you_scored) + " " + scoreAmount +
                        " " + getResources().getString(R.string.out_of) + " " + totalQuestions +
                        " " + getResources().getString(R.string.in)  + " " + totalTime + " " +
                        getResources().getString(R.string.seconds) + " " +
                        getResources().getString(R.string.try_again));
                result.setTextColor(Color.BLACK);
            }
            //Making it so when the user clicks finish they are brought back to the MainActivity(home page)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasHigherScore(totalTime)) {
                        //checking to see if the field is not empty if so display a message
                        String usernameText = username.getText().toString().trim();
                        if (usernameText.isEmpty()) {
                            Toast.makeText(Questions.this, "Please Enter Username!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LeaderboardClass newEntry = new LeaderboardClass(1,"easy",
                                username.getText().toString(), scoreAmount, totalTime);

                        // api PUT call to update database
                        try {
                            RequestQueue queue = Volley.newRequestQueue(Questions.this);
                            Log.d(updateURL,"Updating Leaderboard");
                            JSONObject putData = new JSONObject();
                            try {
                                putData.put("id", newEntry.id);
                                putData.put("level", newEntry.level);
                                putData.put("username", newEntry.username);
                                putData.put("score", newEntry.score);
                                putData.put("time", newEntry.time);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                                        updateURL, putData,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.d(updateURL, response.toString());
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d(updateURL, "Error "+ error.toString());
                                            }
                                        });
                                queue.add(request);
                            }
                            catch (Exception e) {
                                Log.d(updateURL, e.toString());
                            }
                        }
                        catch (Exception e1) {
                            Log.d(updateURL, e1.toString());
                        }
                    }

                    // api PUT call to update summary in database
                    try {
                        RequestQueue queue = Volley.newRequestQueue(Questions.this);
                        Log.d(summaryURL,"Updating Summary");
                        JSONObject putData = new JSONObject();
                        try {
                            putData.put("level", "level");
                            putData.put("attempts", 1);
                            putData.put("totalScore", scoreAmount);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                                    summaryURL, putData,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d(summaryURL, response.toString());
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(summaryURL, "Error "+ error.toString());
                                        }
                                    });
                            queue.add(request);
                        }
                        catch (Exception e) {
                            Log.d(summaryURL, e.toString());
                        }
                    }
                    catch (Exception e1) {
                        Log.d(summaryURL, e1.toString());
                    }
                    Intent intent = new Intent(Questions.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}