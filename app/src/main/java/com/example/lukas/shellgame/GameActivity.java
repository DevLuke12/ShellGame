package com.example.lukas.shellgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private ShellGameView shellGameView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        shellGameView = findViewById(R.id.myGameView);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
            shellGameView.difficulty = extras.getString("selDifficulty");

    }
}
