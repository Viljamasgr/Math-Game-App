package com.example.mathgame;

public class LevelSummaryClass {
    public String level;
    public int attempts;
    public int totalScore;


    public LevelSummaryClass(String levelIn, int attemptsIn, int totalScoreIn) {
        this.level = levelIn;
        this.attempts = attemptsIn;
        this.totalScore = totalScoreIn;
    }

}