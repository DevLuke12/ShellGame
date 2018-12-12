package com.example.lukas.shellgame;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer
{
    public int delay = 0;
    public int periodRate = 0;
    private Timer timer;
    private Runnable runnable;

    public MyTimer(int delay, int periodRate)
    {
        this.delay = delay;
        this.periodRate = periodRate;
    }

//    public void StartTimer(final ShellGameView view, GameActivity activity)
//    {
//        activity.getCon
//        timer = new Timer();
//        timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                new Handler().postDelayed(runnable, delay );
//            }
//        }, delay, periodRate);
//    }



    private void InitRunnable(final ShellGameView view, int periodRate)
    {
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                view.SwapFrame(2000);
            }
        };
    }



}
