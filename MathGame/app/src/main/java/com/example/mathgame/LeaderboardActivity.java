package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class LeaderboardActivity extends AppCompatActivity {
    private String BASE_URL = "https://mathgameapi.azurewebsites.net/leaderboard/";
    private ArrayList<LeaderboardClass> leaderboard;

    private ArrayList<LevelSummaryClass> summaries;

    //defining the btngoback as a button
    Button btnGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //adding a value to btngoback
        btnGoBack = findViewById(R.id.btnGoBack);

        //creating a function so when the back button is clicked the user is returned to the homepage
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(back);
            }
        });

        leaderboard = new ArrayList<>();
        summaries = new ArrayList<>();
        getLeaderboard();
        getSummaries();
    }

    private void getLeaderboard() {

        // Code to call leaderboard from api
        String leaderboardURL = BASE_URL + "all/";

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(leaderboardURL,"Getting Leaderboard");

            try {
                StringRequest request = new StringRequest(Request.Method.GET, leaderboardURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                leaderboard = new Gson().fromJson(response, new TypeToken<List<LeaderboardClass>>(){}.getType());
                                ArrayList<LeaderboardClass> easy = new ArrayList<>();
                                ArrayList<LeaderboardClass> medium = new ArrayList<>();
                                ArrayList<LeaderboardClass> hard = new ArrayList<>();
                                ArrayList<LeaderboardClass> veryHard = new ArrayList<>();

                                for (int i = 0; i < leaderboard.size(); i++) {
                                    if (leaderboard.get(i).level.toLowerCase().equals("easy")) {
                                        easy.add(leaderboard.get(i));
                                    }
                                    else if (leaderboard.get(i).level.toLowerCase().equals("medium")) {
                                        medium.add(leaderboard.get(i));
                                    }
                                    else if (leaderboard.get(i).level.toLowerCase().equals("hard")) {
                                        hard.add(leaderboard.get(i));
                                    }
                                    else {
                                        veryHard.add(leaderboard.get(i));
                                    }
                                }

                                easy.sort(new Comparator<LeaderboardClass>() {
                                    @Override
                                    public int compare(LeaderboardClass l1, LeaderboardClass l2) {
                                        if (l1.score == l2.score) {
                                            return (int)l1.time - (int)l2.time;
                                        }
                                        return l2.score - l1.score;
                                    }
                                });

                                medium.sort(new Comparator<LeaderboardClass>() {
                                    @Override
                                    public int compare(LeaderboardClass l1, LeaderboardClass l2) {
                                        if (l1.score == l2.score) {
                                            return (int)l1.time - (int)l2.time;
                                        }
                                        return l2.score - l1.score;
                                    }
                                });

                                hard.sort(new Comparator<LeaderboardClass>() {
                                    @Override
                                    public int compare(LeaderboardClass l1, LeaderboardClass l2) {
                                        if (l1.score == l2.score) {
                                            return (int)l1.time - (int)l2.time;
                                        }
                                        return l2.score - l1.score;
                                    }
                                });

                                veryHard.sort(new Comparator<LeaderboardClass>() {
                                    @Override
                                    public int compare(LeaderboardClass l1, LeaderboardClass l2) {
                                        if (l1.score == l2.score) {
                                            return (int)l1.time - (int)l2.time;
                                        }
                                        return l2.score - l1.score;
                                    }
                                });

                                LeaderboardAdapter easyAdapter = new LeaderboardAdapter(
                                        LeaderboardActivity.this, easy );
                                LeaderboardAdapter mediumAdapter = new LeaderboardAdapter(
                                        LeaderboardActivity.this, medium );
                                LeaderboardAdapter hardAdapter = new LeaderboardAdapter(
                                        LeaderboardActivity.this, hard );
                                LeaderboardAdapter veryHardAdapter = new LeaderboardAdapter(
                                        LeaderboardActivity.this, veryHard );

                                ListView easyLeaderboard = (ListView) findViewById(R.id.tv_leaderboard_1);
                                easyLeaderboard.setAdapter(easyAdapter);


                                ListView mediumLeaderboard = (ListView) findViewById(R.id.tv_leaderboard_2);
                                mediumLeaderboard.setAdapter(mediumAdapter);

                                ListView hardLeaderboard = (ListView) findViewById(R.id.tv_leaderboard_3);
                                hardLeaderboard.setAdapter(hardAdapter);

                                ListView veryHardLeaderboard = (ListView) findViewById(R.id.tv_leaderboard_4);
                                veryHardLeaderboard.setAdapter(veryHardAdapter);

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

    private void getSummaries() {

        // Code to call summaries from api
        String summaryURL = "https://mathgameapi.azurewebsites.net/summary/all";

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(summaryURL,"Getting Summaries");

            try {
                StringRequest request = new StringRequest(Request.Method.GET, summaryURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                summaries = new Gson().fromJson(response, new TypeToken<List<LevelSummaryClass>>(){}.getType());
                                System.out.println(summaries);
                                double averageScore;
                                for (int i = 0; i < summaries.size(); i++) {
                                    try {
                                        averageScore = summaries.get(i).totalScore / summaries.get(i).attempts;
                                    }
                                    catch (ArithmeticException e) {
                                        averageScore = summaries.get(i).totalScore;
                                    }
                                    if (summaries.get(i).level.toLowerCase().equals("easy")) {
                                        TextView easySummary = findViewById(R.id.easySummary);
                                        easySummary.setText(getResources().getString(R.string.average_score) + " " + averageScore);
                                    }
                                    else if (summaries.get(i).level.toLowerCase().equals("medium")) {
                                        TextView medSummary = findViewById(R.id.mediumSummary);
                                        medSummary.setText(getResources().getString(R.string.average_score) + " " + averageScore);
                                    }
                                    else if (summaries.get(i).level.toLowerCase().equals("hard")) {
                                        TextView hardSummary = findViewById(R.id.hardSummary);
                                        hardSummary.setText(getResources().getString(R.string.average_score) + " " + averageScore);
                                    }
                                    else {
                                        TextView vHardSummary = findViewById(R.id.veryHardSummary);
                                        vHardSummary.setText(getResources().getString(R.string.average_score) + " " + averageScore);
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //errorOutput.setText(error.toString());
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
    }
}
