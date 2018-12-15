package com.example.lukas.shellgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameActivity extends Activity
{

    private TextView txtActualScore;
    private TextView txtDifficulty;
    private TextView txtPlayerName;
    private TextView txtBestScore;
    private ShellGameView shellGameView;
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
    private MediaPlayer moveSound;
    private VibratorHelper myVibrator;
    private FileHelper fileHelper = new FileHelper();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(((ExtendMyApplication) getApplication()).getLightBackground())
            setTheme(R.style.LightTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        txtActualScore = findViewById(R.id.txtActualScore);
        shellGameView = findViewById(R.id.myGameView);
        txtBestScore = findViewById(R.id.txtBestScore);
        txtDifficulty = findViewById(R.id.txtDifficult);
        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtPlayerName.setText(((ExtendMyApplication) getApplication()).getPlayerName());
        moveSound = MediaPlayer.create(this, R.raw.movesound);
        myVibrator = new VibratorHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            difficultyGame = extras.getString("selDifficulty");

        txtDifficulty.setText(difficultyGame);
        if(!ReadFromFile().isEmpty())
            txtBestScore.setText(GetBestScore(ReadFromFile()));

        builder = new AlertDialog.Builder(this);
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
                    txtActualScore.setText(String.valueOf(score));
                    shellGameView.ballWasFound = false;
                    Run();
                }
                else if(shellGameView.endGame)
                {
                    myVibrator.Vibrate(500);
                    WriteToFile(txtPlayerName.getText().toString() + " " + String.valueOf(score) + System.lineSeparator());
                    shellGameView.endGame = false;

                    Utils.delay(3000, new Utils.DelayCallback() {
                        @Override
                        public void afterDelay()
                        {
                            builder.setMessage("Do you want to play new game").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        }
                    });

                }
            }
        };
        shellGameView.changes.addPropertyChangeListener(listener);

    }


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    Intent intent = new Intent(GameActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
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
                            PlayMoveSound();
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

    private void PlayMoveSound()
    {
        if (moveSound.isPlaying())
            moveSound.stop();
        else
            moveSound.start();
    }

    private void WriteToFile(String data)
    {
        if(difficultyGame.equals("easy"))
            fileHelper.WriteToFile("easy",data,getApplicationContext());
        else if(difficultyGame.equals("normal"))
            fileHelper.WriteToFile("normal", data,getApplicationContext());
        else
            fileHelper.WriteToFile("hard", data,getApplicationContext());
    }

    private String ReadFromFile()
    {
        if(difficultyGame.equals("easy"))
            return fileHelper.ReadFromFile("easy",getApplicationContext());
        else if(difficultyGame.equals("normal"))
            return fileHelper.ReadFromFile("normal",getApplicationContext());
        else
            return fileHelper.ReadFromFile("hard",getApplicationContext());
    }

    private String GetBestScore(String data)
    {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(data);
        int max  = 0;
        while (m.find())
        {
            int number = Integer.parseInt(m.group());
            if(number > max)
                max = number;
        }

        return String.valueOf(max);
    }
}
