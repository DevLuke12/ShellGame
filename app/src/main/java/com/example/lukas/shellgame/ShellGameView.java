package com.example.lukas.shellgame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


public class ShellGameView extends View
{
    private Paint paint;
    private AnimatableRectF firstShell;
    private AnimatableRectF secondShell;
    private AnimatableRectF thirdShell;
    private AnimatableRectF ball;
    private int locationFirstShell = 1;
    private int locationSecondShell = 2;
    private int locationThirdShell = 3;
    public String difficulty;
    private TouchPoint touchPoint = new TouchPoint();
    public int displayWidth;
    public int displayHeight;
    private int Score;
    private boolean isTouchable = false;


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
        ball = new AnimatableRectF(displayWidth / 6 + 350, displayHeight / (float) 4.4 + 180, displayWidth / 6 + 400, displayHeight / (float) 4.4 + 230);

        MoveUpAndDown(secondShell,true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
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
        switch (event.getAction()) {
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
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private void MoveShellP1ToP2(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6 + 300,displayWidth / 6 + 450, 360,
                milisecondDuration, swapOrientationUp);

//        Path pathLeft = DrawCurvedArrow(400,400,600,600,360, swapOrientationUp);
//        Path pathTop = DrawCurvedArrow(200,200,200,200,360, swapOrientationUp);
//        Path pathRight = DrawCurvedArrow(500,500,700,700,360, swapOrientationUp);
//        Path pathBottom = DrawCurvedArrow(350,350,350,350,360, swapOrientationUp);
//
//        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", "left", pathLeft);
//        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "top", "top", pathTop);
//        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "right", "right", pathRight);
//        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", "bottom", pathBottom);
//
//        animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                invalidate();
//            }
//        });
//
//        AnimatorSet rectAnimation = new AnimatorSet();
//        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
//        rectAnimation.setDuration(milisecondDuration).start();
    }

    private void MoveShellP1ToP3(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6 + 600,displayWidth / 6 + 750, 360,
                milisecondDuration * 2, swapOrientationUp);

    }

    private void MoveShellP2ToP1(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6,displayWidth / 6 + 150, 360,
                milisecondDuration, swapOrientationUp);
    }

    private void MoveShellP2ToP3(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6 + 600,displayWidth / 6 + 750, 360,
                milisecondDuration, swapOrientationUp);
    }

    private void MoveShellP3ToP1(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6,displayWidth / 6 + 150, 360,
                milisecondDuration * 2, swapOrientationUp);
    }

    private void MoveShellP3ToP2(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        rectF.Animate(rectF,this, displayWidth / 6 + 300,displayWidth / 6 + 450, 360,
                milisecondDuration, swapOrientationUp);
    }

    private void MoveUp(AnimatableRectF rectF, int milisecondDuration, int delay)
    {
        rectF.AnimateUpOrDown(rectF, this, 0, -175, milisecondDuration, delay);
    }

    private void MoveDown(AnimatableRectF rectF, int milisecondDuration, int delay)
    {
        rectF.AnimateUpOrDown(rectF, this, 0, 175, milisecondDuration, delay);
    }

    private void MoveUpAndDown(final AnimatableRectF rectF, boolean isIninial)
    {
        int delay = 0;
        if(isIninial)
            delay = 2000;
        else
            delay = 0;

        MoveUp(rectF, 2000, delay);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                MoveDown(rectF,2000, 0);
            }
        }, delay = isIninial == true ?  delay + 2001 : 2001);
    }
}
