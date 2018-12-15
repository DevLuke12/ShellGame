package com.example.lukas.shellgame;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorHelper
{
    //private Context context;
    private Vibrator myVibrator;
    public VibratorHelper(Context context)
    {
        //this.context = context;
        myVibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
    }
//    public void Init()
//    {
//
//    }
    public void Vibrate(int milisecondsDuration)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            myVibrator.vibrate(VibrationEffect.createOneShot(milisecondsDuration, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else
            {
            myVibrator.vibrate(500);
        }
    }
}
