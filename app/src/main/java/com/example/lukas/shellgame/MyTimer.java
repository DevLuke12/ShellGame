package com.example.lukas.shellgame;

import java.util.Timer;

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
                view.SwapShells(2000);
            }
        };
    }



}
