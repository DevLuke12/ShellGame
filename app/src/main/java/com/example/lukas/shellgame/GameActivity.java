package com.example.lukas.shellgame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    private ShellGameView shellGameView;
    private Point DisplaySize;
    private int score = 0;
    int counterSwapping = 0;
    private int numberOfSwapping = 0;
    private Timer timer;
    private static final int EASY = 3;
    private static final int NORMAL = 2;
    private static final int HARD = 1;
    private static final int MILISECONDS = 1000;
    private int periodRate = 0;
    private String difficultyGame;
    public PropertyChangeListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        shellGameView = findViewById(R.id.myGameView);
        //Handler handler = new Handler();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            difficultyGame = extras.getString("selDifficulty");

        SetPeriodRate();
        RunInit();

       listener = new PropertyChangeListener()
       {
            @Override
            public void propertyChange(PropertyChangeEvent event)
            {
                if (shellGameView.ballWasFound)
                {

                    score++;
                    Log.d("score", String.valueOf(score));
                    shellGameView.ballWasFound = false;
                    Run();
                }
            }
        };
        shellGameView.changes.addPropertyChangeListener(listener);
    }

    private void RunMixing(int period, int delay)
    {
        final int myPeriod = period;
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                GameActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(counterSwapping > 0)
                        {
                            shellGameView.SwapShells(myPeriod);
                            counterSwapping--;
                        }
                        else
                        {
                            timer.cancel();
                            shellGameView.isTouchable = true;
                        }
                    }
                });
            }
        }, delay, period);
    }

    private void RunInitMoveAndUp()
    {
        if(shellGameView.findingShell == 1)
            shellGameView.MoveUpAndDown(shellGameView.firstShell,true);
        else if(shellGameView.findingShell == 2)
            shellGameView.MoveUpAndDown(shellGameView.secondShell,true);
        else
            shellGameView.MoveUpAndDown(shellGameView.thirdShell,true);
    }

    private void RunMoveAndUp()
    {
        if(shellGameView.findingShell == 1)
            shellGameView.MoveUpAndDown(shellGameView.firstShell,false);
        else if(shellGameView.findingShell == 2)
            shellGameView.MoveUpAndDown(shellGameView.secondShell,false);
        else
            shellGameView.MoveUpAndDown(shellGameView.thirdShell,false);
    }

    private void InitRunEasy()
    {
        RunInitMoveAndUp();
        SetNumberOfSwapping(2);
        SetCounterSwapping();
        RunMixing(periodRate,5000);
        DecreasePeriodRate( 200);
    }

    private void InitRunNormal()
    {
        RunInitMoveAndUp();
        SetNumberOfSwapping(3);
        SetCounterSwapping();
        RunMixing(periodRate,5000);
        DecreasePeriodRate( 150);
    }

    private void InitRunHard()
    {
        RunInitMoveAndUp();
        SetNumberOfSwapping(4);
        SetCounterSwapping();
        RunMixing(periodRate,5000);
        DecreasePeriodRate(100);
    }

    private void RunEasy()
    {
        RunMoveAndUp();
        SetNumberOfSwapping(score + 1);
        SetCounterSwapping();
        RunMixing(periodRate,3000);
        DecreasePeriodRate(200);
    }
    private void RunNormal()
    {
        RunMoveAndUp();
        SetNumberOfSwapping(score + 2);
        SetCounterSwapping();
        RunMixing(periodRate,3000);
        DecreasePeriodRate( 150);
    }
    private void RunHard()
    {
        RunMoveAndUp();
        SetNumberOfSwapping(score + 3);
        SetCounterSwapping();
        RunMixing(periodRate,3000);
        DecreasePeriodRate(100);
    }



    private void RunInit()
    {
        if(difficultyGame.equals("easy"))
        {
            InitRunEasy();
        }
        else if(difficultyGame.equals("normal"))
        {
            InitRunNormal();
        }
        else
            InitRunHard();

    }
    private void Run()
    {

        if(difficultyGame.equals("easy"))
        {
            RunEasy();
        }
        else if(difficultyGame.equals("normal"))
        {
            RunNormal();
        }
        else
            RunHard();
    }

    private void SetPeriodRate()
    {
        if(difficultyGame.equals("easy"))
            periodRate = EASY * MILISECONDS + 500;
        else if(difficultyGame.equals("normal"))
            periodRate = NORMAL * MILISECONDS + 500;
        else
            periodRate = HARD * MILISECONDS + 500;
    }

    private void SetNumberOfSwapping(int addValue)
    {
        this.numberOfSwapping += addValue;
    }

    private void SetCounterSwapping()
    {
        this.counterSwapping = numberOfSwapping;
    }

    private void DecreasePeriodRate(int value)
    {
        this.periodRate -= value;
    }
}
