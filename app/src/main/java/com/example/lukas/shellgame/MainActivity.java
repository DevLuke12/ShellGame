package com.example.lukas.shellgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
    private Button btnNewGame;
    private Button btnTopScores;
    private Button btnCloseGame;
    public String SELECTED_DIFFICULTY;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewGame = findViewById(R.id.btnNewGame);
        btnTopScores = findViewById(R.id.btnTopScores);
        btnCloseGame = findViewById(R.id.btnCloseGame);
        btnNewGame.setOnClickListener(openNewGame);
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
}
