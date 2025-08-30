package com.example.mathgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardClass> {
    public LeaderboardAdapter(Context context, ArrayList<LeaderboardClass> leaderboard) {
        super(context,0,leaderboard);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LeaderboardClass l = getItem(position);
        if (convert == null) {
            convert = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_layout,
                    parent, false);
        }
        TextView username = (TextView) convert.findViewById(R.id.leaderboardUsername);
        TextView score = (TextView) convert.findViewById(R.id.leaderboardScore);
        TextView time = (TextView) convert.findViewById(R.id.leaderboardTime);
        username.setText(l.username);
        score.setText(String.valueOf(l.score));
        time.setText(String.valueOf(l.time));

        return convert;
    }
}
