package com.example.lukas.shellgame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    private ShellGameView shellGameView;
    private Point DisplaySize;
    int score = 0;
    int numberOfSwapping = 0;
    Timer timer;
    private static final int EASY = 4;
    private static final int NORMAL = 3;
    private static final int HARD = 5;
    private static final int MILISECONDS = 1000;
    private int easyPeriodRate = EASY * MILISECONDS;
    private String difficultyGame;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        shellGameView = findViewById(R.id.myGameView);
        Handler handler = new Handler();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            difficultyGame = extras.getString("selDifficulty");
        }

        if(difficultyGame.equals("easy"))
        {
            RunEasy();
        }


        //handler.postDelayed(r, 1000);

//        MyTimer timer = new MyTimer(easy * 1000*6,300,2000);
//        timer.StartTimer(shellGameView);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                // do something...
//                System.out.println("period = " + period);
//                period = 500;   // change the period time
//                timer.cancel(); // cancel time
//                startTimer();   // start the time again with a new period time
//            }
//        }, 0, period);
        //CounterDownInterval(normal,10);
//        final int duration = easy * 1000;
//        Timer timer = new Timer();
//        TimerTask t = new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                shellGameView.SwapFrame(duration);
//
//                //System.out.println("1");
//            }
//        };
//        timer.schedule(t,3000,duration);



    }

    private void RunMixing(final int period, int delay)
    {

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

                        if(numberOfSwapping > 0)
                        {
                            shellGameView.SwapFrame(period);
                            numberOfSwapping--;
                        }
                        else timer.cancel();
                    }
                });
            }
        }, delay, period);
    }

    private void RunInitMoveAndUp()
    {
        Log.d("value","asdas");
        shellGameView.MoveUpAndDown(shellGameView.secondShell,true);
    }

    private void RunEasy()
    {
        Log.d("value","dsadas");
        RunInitMoveAndUp();
        numberOfSwapping = score + 1;
        RunMixing(easyPeriodRate,6005);
        easyPeriodRate -= 150;
    }
    private void Run()
    {

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        shellGameView.SwapFrame(1500);
                    }
                }, 0, 2001);//put here time 1000 milliseconds=1 second
                //do something
                handler.postDelayed(this, delay);
            }
        }, delay);
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
