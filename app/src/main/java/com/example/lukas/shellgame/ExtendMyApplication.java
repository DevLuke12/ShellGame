package com.example.lukas.shellgame;

import android.app.Application;

public class ExtendMyApplication extends Application
{
    private String myPlayerName = "guest";
    private boolean lightBackground = true;

    public String getPlayerName()
    {
        return myPlayerName;
    }

    public void setPlayerName(String playerName) {
        this.myPlayerName = playerName;
    }

    public boolean getLightBackground()
    {
        return lightBackground;
    }

    public void setLightBackground(boolean value) {
        this.lightBackground = value;
    }
}
