package com.example.lukas.shellgame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopScoreActivity extends Activity
{

    private TableLayout gridForEasyGame;
    private TableLayout gridForNormalGame;
    private TableLayout gridForHardGame;
    private FileHelper fileHelper;
    private Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(((ExtendMyApplication) getApplication()).getLightBackground())
            setTheme(R.style.LightTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_top_score);
        btnHome = findViewById(R.id.btnHome);
        gridForEasyGame = findViewById(R.id.gridForEasyGame);
        gridForEasyGame.setPadding(0, 0, 0, 5);
        gridForNormalGame = findViewById(R.id.gridForNormalGame);
        gridForNormalGame.setPadding(0, 0, 0, 5);
        gridForHardGame = findViewById(R.id.gridForHardGame);
        gridForHardGame.setPadding(0, 0, 0, 5);
        fileHelper = new FileHelper();
        SetHeader(gridForEasyGame);
        SetHeader(gridForNormalGame);
        SetHeader(gridForHardGame);
        btnHome.setOnClickListener(closeThisActivity);
        if(!GetData("easy").isEmpty())
            AddRowValues(gridForEasyGame, GetData("easy"));

        if(!GetData("normal").isEmpty())
            AddRowValues(gridForNormalGame, GetData("normal"));

        if(!GetData("hard").isEmpty())
            AddRowValues(gridForHardGame, GetData("hard"));

    }

    private void SetHeader(TableLayout layout)
    {
        TableRow row = new TableRow(this);

        row.setPadding(2, 0, 2, 0);
        row.setBackgroundColor(Color.parseColor("#FFFF00"));

        TextView Header = new TextView(this);
        Header.setText("Order    Name    Score");
        Header.setTextSize(16.0f);
        Header.setTextColor(Color.parseColor("#000000"));
        Header.setTypeface(null, Typeface.BOLD);

        row.addView(Header);
        layout.addView(row);
    }

    private ArrayList<Integer> GetNumbers(String data)
    {
        Pattern paternNumber = Pattern.compile("\\d+");
        Matcher matcherNumber = paternNumber.matcher(data);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while (matcherNumber.find())
        {
            numbers.add(Integer.parseInt(matcherNumber.group()));
        }
        return numbers;
    }

    private ArrayList<String> GetWords(String data)
    {
        Pattern paternWord = Pattern.compile("[a-zA-Z]+");
        Matcher matcherWord = paternWord.matcher(data);
        ArrayList<String> words = new ArrayList<String>();
        while (matcherWord.find())
        {
            words.add(matcherWord.group());
        }
        return words;
    }
    private void AddRowValues(TableLayout layout, String data)
    {
        ArrayList<Integer> numbers = GetNumbers(data);
        ArrayList<String> words = GetWords(data);

        List<TopScoreHelper> results = new ArrayList<>();
        for (int i = 0; i < numbers.size();i++)
        {
            results.add(new TopScoreHelper(words.get(i),numbers.get(i)));
        }

        Collections.sort(results);

        int maxCount = 0;
        if(results.size() > 3)
            maxCount = 3;
        else
            maxCount = results.size();
        for (int i = 0; i < maxCount; i++)
        {
            TableRow row = new TableRow(this);

            row.setPadding(2, 0, 2, 0);
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));

            TextView Header = new TextView(this);
            Header.setText(String.valueOf(i + 1) + "                " + results.get(i).getPlayerName()
                    +"         " +  String.valueOf(results.get(i).getScore()));
            Header.setTextSize(13.0f);
            Header.setTextColor(Color.parseColor("#000000"));
            Header.setTypeface(null, Typeface.BOLD);

            row.addView(Header);
            layout.addView(row);
        }
    }

    private String GetData(String difficulty)
    {
        if(difficulty.equals("easy"))
            return fileHelper.ReadFromFile("easy", getApplicationContext());
        else if(difficulty.equals("normal"))
            return fileHelper.ReadFromFile("normal", getApplicationContext());
        else
            return fileHelper.ReadFromFile("hard", getApplicationContext());

    }

    private View.OnClickListener closeThisActivity = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            finish();
        }
    };
}
