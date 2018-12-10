package com.example.lukas.shellgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends Activity
{
    private Button btnEasy;
    private Button btnNormal;
    private Button btnHard;
    private Button btnHome;
    private String easyGame = "easy";
    private String normalGame = "normal";
    private String hardGame = "hard";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        btnEasy = findViewById(R.id.btnEasy);
        btnNormal = findViewById(R.id.btnNormal);
        btnHard = findViewById(R.id.btnHard);

        btnHome = findViewById(R.id.btnHome);

        btnEasy.setOnClickListener(openEasyGame);
        btnNormal.setOnClickListener(openNormalGame);
        btnHard.setOnClickListener(openHardGame);

        btnHome.setOnClickListener(closeGame);
    }

    private View.OnClickListener openEasyGame = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(DifficultyActivity.this,
                    GameActivity.class);

            intent.putExtra("selDifficulty", easyGame);
            startActivity(intent);
        }
    };

    private View.OnClickListener openNormalGame = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(DifficultyActivity.this,
                    GameActivity.class);

            intent.putExtra("selDifficulty", normalGame);
            startActivity(intent);
        }
    };

    private View.OnClickListener openHardGame = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(DifficultyActivity.this,
                    GameActivity.class);

            intent.putExtra("selDifficulty", hardGame);
            startActivity(intent);
        }
    };

    private View.OnClickListener closeGame = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            finish();
        }
    };
}
