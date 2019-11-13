package com.example.scoping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private RecyclerView scoreRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Score> scoresArrayList;
    ScoreAdapter scoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        scoreRecyclerView = findViewById(R.id.scoreRecyclerView);

        scoreRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(Dashboard.this);
        scoreRecyclerView.setLayoutManager(layoutManager);

        scoresArrayList.clear();
        scoreAdapter = new ScoreAdapter(this,scoresArrayList);
        scoreRecyclerView.setAdapter(scoreAdapter);

    }
}
