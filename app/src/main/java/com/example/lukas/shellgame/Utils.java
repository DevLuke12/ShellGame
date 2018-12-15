package com.example.lukas.shellgame;

import android.os.Handler;

public class Utils
{
    public interface DelayCallback
    {
        void afterDelay();
    }

    public static void delay(int miliseconds, final DelayCallback delayCallback)
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, miliseconds); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}
