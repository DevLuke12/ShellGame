package com.example.lukas.shellgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.beans.PropertyChangeSupport;
import java.util.Random;


public class ShellGameView extends View
{
    private Paint paint;
    protected AnimatableRectF firstShell;
    protected AnimatableRectF secondShell;
    protected AnimatableRectF thirdShell;
    protected AnimatableRectF ball;
    private int posFirstShell = 1;
    private int posSecondShell = 2;
    private int posThirdShell = 3;
    private TouchPoint touchPoint = new TouchPoint();
    private int displayWidth;
    private int displayHeight;
    public boolean isTouchable = false;
    private Random rngSwap = new Random();
    private int posBall = 0;
    protected int findingShell = 0;
    protected boolean ballWasFound = false;
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private void GetDisplaySize(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;
    }

    public ShellGameView(Context context)
    {
        super(context);
        Init(context);

    }

    public ShellGameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        Init(context);
    }

    public ShellGameView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        Init(context);

    }

    private void Init(Context context)
    {
        GetDisplaySize(context);
        paint = new Paint();
        firstShell = new AnimatableRectF(displayWidth / 6, displayHeight / (float) 4.4 + 30, displayWidth / 6 + 150, displayHeight / (float) 4.4 + 230);
        secondShell = new AnimatableRectF(displayWidth / 6 + 300, displayHeight / (float) 4.4 + 30, displayWidth / 6 + 450, displayHeight / (float) 4.4 + 230);
        thirdShell = new AnimatableRectF(displayWidth / 6 + 600, displayHeight / (float) 4.4 + 30, displayWidth / 6 + 750, displayHeight / (float) 4.4 + 230);
        firstShell.setName("firstShell");
        secondShell.setName("secondShell");
        thirdShell.setName("thirdShell");
        RngPositionOfBall();
        findingShell = posBall;
        InitializeBall(posBall);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        paint.setColor(Color.rgb(213, 145, 89));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);

        Paint paint1 = new Paint();
        paint1.setColor(Color.rgb(144, 55, 66));
        paint1.setStrokeWidth(2);
        paint1.setStyle(Paint.Style.FILL);

        canvas.drawOval(ball, paint1);

        canvas.drawOval(firstShell, paint);
        canvas.drawOval(secondShell, paint);
        canvas.drawOval(thirdShell, paint);
    }

    private AnimatableRectF GetSelectedShell(float touchX, float touchY)
    {
        if (firstShell.contains(touchX, touchY))
            return firstShell;
        else if (secondShell.contains(touchX, touchY))
            return secondShell;
        else if (thirdShell.contains(touchX, touchY))
            return thirdShell;
        else
            return null;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(!isTouchable)
            return false;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                touchPoint.touchFromX = event.getX();
                touchPoint.touchFromY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchPoint.touchToX = event.getX();
                touchPoint.touchToY = event.getY();

                if(touchPoint.IsSwiftLineUpCorrect(touchPoint) &&
                        GetSelectedShell(touchPoint.touchFromX, touchPoint.touchFromY) != null)
                {
                    MoveUpAndDown(GetSelectedShell(touchPoint.touchFromX, touchPoint.touchFromY),false);

                    if(IsBallInsideShell(GetSelectedShell(touchPoint.touchFromX, touchPoint.touchFromY).getName()))
                    {
                        //Log.d("score", String.valueOf(score).toString());
                        ballWasFound = true;
                        this.changes.firePropertyChange("ballWasFound",false,true);
                        isTouchable = false;
                    }
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private void MoveShellToP1(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.AnimateByPath(rectF,this, displayWidth / 6,displayWidth / 6 + 150, 360,
                milisecondDuration, swapOrientationUp);
    }
    private void MoveShellToP2(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.AnimateByPath(rectF,this, displayWidth / 6 + 300,displayWidth / 6 + 450, 360,
                milisecondDuration, swapOrientationUp);
    }
    private void MoveShellToP3(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.AnimateByPath(rectF,this, displayWidth / 6 + 600,displayWidth / 6 + 750, 360,
                milisecondDuration, swapOrientationUp);

    }

    private void MoveBallToP1(int milisecondDuration, boolean swapOrientationUp) {

        ball.AnimateByPath(ball,this, displayWidth / 6 + 50,displayWidth / 6 + 100, 360,
                milisecondDuration, swapOrientationUp);
    }
    private void MoveBallToP2(int milisecondDuration, boolean swapOrientationUp) {

        ball.AnimateByPath(ball,this, displayWidth / 6 + 350,displayWidth / 6 + 400, 360,
                milisecondDuration, swapOrientationUp);
    }
    private void MoveBallToP3(int milisecondDuration, boolean swapOrientationUp) {

        ball.AnimateByPath(ball,this, displayWidth / 6 + 650,displayWidth / 6 + 700, 360,
                milisecondDuration, swapOrientationUp);
    }


    private void MoveUp(AnimatableRectF rectF, int milisecondDuration, int delay)
    {
        rectF.AnimateByXY(rectF, this, 0, -175, milisecondDuration, delay);
    }

    private void MoveDown(AnimatableRectF rectF, int milisecondDuration, int delay)
    {
        rectF.AnimateByXY(rectF, this, 0, 175, milisecondDuration, delay);
    }

    protected void MoveUpAndDown(final AnimatableRectF rectF, boolean isIninial)
    {
        int delay = 0;
        if(isIninial)
            delay = 2000;
        else
            delay = 0;

        MoveUp(rectF, 1500, delay);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                MoveDown(rectF,1500, 0);
            }
        }, delay = isIninial == true ?  delay + 1500 : 1500);
    }

    private Boolean RngSwapOrientation()
    {
        int tmp = rngSwap.nextInt(2) + 1;

        if(tmp == 1)
            return true;
        else
            return false;
    }
    protected void SwapShells(int milisecondDuration)
    {
        //isTouchable = false;
        int rngSwap = this.rngSwap.nextInt(3) + 1;
        boolean swapOrientationUp = RngSwapOrientation();
        if(rngSwap == 1)
        {
            if(posFirstShell==1)
            {
                if(findingShell == 1)
                    MoveBallToP2(milisecondDuration, swapOrientationUp);

                MoveShellToP2(firstShell, milisecondDuration, swapOrientationUp);
                posFirstShell = 2;
                if(posSecondShell == 2)
                {
                    if(findingShell == 2)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(secondShell, milisecondDuration, !swapOrientationUp);
                    posSecondShell=1;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(thirdShell, milisecondDuration, !swapOrientationUp);
                    posThirdShell = 1;
                }
            }
            else if(posSecondShell==1)
            {
                if(findingShell == 2)
                    MoveBallToP2(milisecondDuration, !swapOrientationUp);

                MoveShellToP2(secondShell, milisecondDuration, !swapOrientationUp);
                posSecondShell = 2;
                if(posFirstShell == 2)
                {
                    if(findingShell == 1)
                        MoveBallToP1(milisecondDuration,swapOrientationUp);

                    MoveShellToP1(firstShell, milisecondDuration, swapOrientationUp);
                    posFirstShell = 1;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP1(milisecondDuration, swapOrientationUp);

                    MoveShellToP1(thirdShell, milisecondDuration, swapOrientationUp);
                    posThirdShell=1;
                }
            }
            else if(posThirdShell==1)
            {
                if(findingShell == 3)
                    MoveBallToP2(milisecondDuration, !swapOrientationUp);

                MoveShellToP2(thirdShell, milisecondDuration, !swapOrientationUp);
                posThirdShell = 2;
                if(posFirstShell == 2)
                {
                    if(findingShell == 1)
                        MoveBallToP1(milisecondDuration, swapOrientationUp);

                    MoveShellToP1(firstShell, milisecondDuration, swapOrientationUp);
                    posFirstShell=1;
                }
                else
                {
                    if(findingShell == 2)
                        MoveBallToP1(milisecondDuration, swapOrientationUp);

                    MoveShellToP1(secondShell, milisecondDuration, swapOrientationUp);
                    posSecondShell=1;
                }
            }
        }
        else if(rngSwap == 2)
        {
            if(posFirstShell == 1)
            {
                if(findingShell == 1)
                    MoveBallToP3(milisecondDuration, swapOrientationUp);

                MoveShellToP3(firstShell,milisecondDuration, swapOrientationUp);
                posFirstShell=3;

                if(posSecondShell==3)
                {
                    if(findingShell == 2)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(secondShell,milisecondDuration,!swapOrientationUp);
                    posSecondShell=1;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(thirdShell,milisecondDuration,!swapOrientationUp);
                    posThirdShell=1;
                }
            }
            else if(posSecondShell==1)
            {
                if(findingShell == 2)
                    MoveBallToP3(milisecondDuration,!swapOrientationUp);

                MoveShellToP3(secondShell,milisecondDuration,!swapOrientationUp);
                posSecondShell=3;
                if(posFirstShell==3)
                {
                    if(findingShell == 1)
                        MoveBallToP1(milisecondDuration,swapOrientationUp);

                    MoveShellToP1(firstShell,milisecondDuration,swapOrientationUp);
                    posFirstShell=1;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP1(milisecondDuration,swapOrientationUp);

                    MoveShellToP1(thirdShell,milisecondDuration,swapOrientationUp);
                    posThirdShell=1;
                }
            }
            else if(posThirdShell==1)
            {
                if(findingShell == 3)
                    MoveBallToP3(milisecondDuration,swapOrientationUp);

                MoveShellToP3(thirdShell,milisecondDuration,swapOrientationUp);
                posThirdShell=3;
                if(posFirstShell==3)
                {
                    if(findingShell == 1)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(firstShell,milisecondDuration,!swapOrientationUp);
                    posFirstShell=1;
                }
                else
                {
                    if(findingShell == 2)
                        MoveBallToP1(milisecondDuration,!swapOrientationUp);

                    MoveShellToP1(secondShell,milisecondDuration,!swapOrientationUp);
                    posSecondShell=1;
                }
            }
        }
        else if(rngSwap == 3)
        {
            if(posFirstShell==2)
            {
                if(findingShell == 1)
                    MoveBallToP3(milisecondDuration,!swapOrientationUp);

                MoveShellToP3(firstShell,milisecondDuration,!swapOrientationUp);
                posFirstShell=3;
                if(posSecondShell==3)
                {
                    if(findingShell == 2)
                        MoveBallToP2(milisecondDuration,swapOrientationUp);

                    MoveShellToP2(secondShell,milisecondDuration,swapOrientationUp);
                    posSecondShell=2;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP2(milisecondDuration,swapOrientationUp);

                    MoveShellToP2(thirdShell,milisecondDuration,swapOrientationUp);
                    posThirdShell=2;
                }
            }
            else if(posSecondShell==2)
            {
                if(findingShell == 2)
                    MoveBallToP3(milisecondDuration,swapOrientationUp);

                MoveShellToP3(secondShell,milisecondDuration,swapOrientationUp);
                posSecondShell=3;
                if(posFirstShell==3)
                {
                    if(findingShell == 1)
                        MoveBallToP2(milisecondDuration,!swapOrientationUp);

                    MoveShellToP2(firstShell,milisecondDuration,!swapOrientationUp);
                    posFirstShell=2;
                }
                else
                {
                    if(findingShell == 3)
                        MoveBallToP2(milisecondDuration,!swapOrientationUp);

                    MoveShellToP2(thirdShell,milisecondDuration,!swapOrientationUp);
                    posThirdShell=2;
                }
            }
            else if(posThirdShell==2)
            {
                if(findingShell == 3)
                    MoveBallToP3(milisecondDuration,!swapOrientationUp);

                MoveShellToP3(thirdShell,milisecondDuration,!swapOrientationUp);
                posThirdShell=3;
                if(posFirstShell==3)
                {
                    if(findingShell == 1)
                        MoveBallToP2(milisecondDuration,swapOrientationUp);

                    MoveShellToP2(firstShell,milisecondDuration,swapOrientationUp);
                    posFirstShell=2;
                }
                else
                {
                    if(findingShell == 2)
                        MoveBallToP2(milisecondDuration,swapOrientationUp);

                    MoveShellToP2(secondShell,milisecondDuration,swapOrientationUp);
                    posSecondShell=2;
                }
            }
        }
        SetActualBallPosition();
    }

    protected void RngPositionOfBall()
    {
        posBall = rngSwap.nextInt(3) + 1;
    }

    private void InitializeBall(int position)
    {
        if(position == 1)
            ball = new AnimatableRectF(displayWidth / 6 + 50, displayHeight / (float) 4.4 + 180,
                    displayWidth / 6 + 100, displayHeight / (float) 4.4 + 230);
        else if(position == 2)
            ball = new AnimatableRectF(displayWidth / 6 + 350, displayHeight / (float) 4.4 + 180,
                    displayWidth / 6 + 400, displayHeight / (float) 4.4 + 230);
        else
            ball = new AnimatableRectF(displayWidth / 6 + 650, displayHeight / (float) 4.4 + 180,
                    displayWidth / 6 + 700, displayHeight / (float) 4.4 + 230);

    }

    private void SetActualBallPosition()
    {
        if(findingShell == 1)
            posBall = posFirstShell;
        else if(findingShell == 2)
            posBall = posSecondShell;
        else
            posBall = posThirdShell;
    }

    protected boolean IsBallInsideShell(String selectedName)
    {
        boolean isInside = false;
        if(selectedName.equals("firstShell"))
        {
            if(findingShell == 1)
                isInside = true;
        }
        else if(selectedName.equals("secondShell"))
        {
            if(findingShell == 2)
                isInside = true;
        }
        else if(selectedName.equals("thirdShell"))
        {
            if(findingShell == 3)
                isInside = true;
        }

        return isInside;
    }

}
