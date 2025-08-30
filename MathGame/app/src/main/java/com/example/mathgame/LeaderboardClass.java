package com.example.mathgame;

public class LeaderboardClass {
    public int id;
    public String level;
    public String username;
    public int score;
    public double time;


    public LeaderboardClass(int idIn, String levelIn, String usernameIn,
                            int scoreIn, double timeIn) {
        this.id = idIn;
        this.level = levelIn;
        this.username = usernameIn;
        this.score = scoreIn;
        this.time = timeIn;
    }
}
