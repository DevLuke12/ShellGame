package com.example.lukas.shellgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity
{
    private Button btnNewGame;
    private Button btnTopScores;
    private Button btnCloseGame;
    private Button btnSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(((ExtendMyApplication) getApplication()).getLightBackground())
            setTheme(R.style.LightTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnNewGame = findViewById(R.id.btnNewGame);
        btnTopScores = findViewById(R.id.btnTopScores);
        btnSettings = findViewById(R.id.btnSettings);
        btnCloseGame = findViewById(R.id.btnCloseGame);
        btnNewGame.setOnClickListener(openNewGame);
        btnSettings.setOnClickListener(openSettings);
    }

    private View.OnClickListener openNewGame = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this,
                    DifficultyActivity.class);
            //intent.putExtra(SELECTED_DIFFICULTY, myText2.getText().toString());
            startActivity(intent);
        }
    };

    private View.OnClickListener openSettings = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            //intent.putExtra(SELECTED_DIFFICULTY, myText2.getText().toString());
            startActivity(intent);
        }
    };
}
