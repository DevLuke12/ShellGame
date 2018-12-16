package com.example.lukas.shellgame;

public class TopScoreHelper implements Comparable<TopScoreHelper>
{
    private int score;
    private String playerName;

    public TopScoreHelper(String playerName, int score)
    {
        this.playerName = playerName;
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }


    @Override
    public int compareTo(TopScoreHelper topScoreHelperTo)
    {
        int compareScore=((TopScoreHelper)topScoreHelperTo).getScore();
        return compareScore - this.score;
    }

}
