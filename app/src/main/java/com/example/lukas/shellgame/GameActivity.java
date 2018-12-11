package com.example.lukas.shellgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity
{

    private ShellGameView shellGameView;
    private Point DisplaySize;
    public int bla = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        shellGameView = findViewById(R.id.myGameView);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            shellGameView.difficulty = extras.getString("selDifficulty");
        }


    }


    private void SetDisplaySize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        DisplaySize.x = size.x;
        DisplaySize.y = size.y;
        //shellGameView.displayHeight = size.y;
        //Log.d("width", String.valueOf(shellGameView.displayWidth).toString() );
    }
}
