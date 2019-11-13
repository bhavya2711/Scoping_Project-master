package com.example.scoping_project;

public class Score {
    Double score;
    String teamName;

    public Score(Double score, String teamName) {
        this.score = score;
        this.teamName = teamName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
