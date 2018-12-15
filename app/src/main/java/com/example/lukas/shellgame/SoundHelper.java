package com.example.lukas.shellgame;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundHelper
{
    private MediaPlayer moveSound;
    private Context context;
    public SoundHelper(Context context)
    {
        this.context = context;
    }

    public void InitMoveSound()
    {
        moveSound = MediaPlayer.create(context, R.raw.movesound);
    }
    public void PlayMoveSound()
    {

    }
}
