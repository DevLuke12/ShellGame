package com.example.lukas.shellgame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.PathMotion;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import static android.animation.ObjectAnimator.ofFloat;

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
    private float selectedX = 0;
    private float selectedY = 0;
    public String difficulty = null;


    public ShellGameView(Context context) {
        super(context);
        Init(context);
    }

    public ShellGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public ShellGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);
    }

    private void Init(Context context) {
        paint = new Paint();
        firstShell = new AnimatableRectF(400, 200, 500, 350);
        secondShell = new AnimatableRectF(600, 200, 700, 350);
        thirdShell = new AnimatableRectF(800, 200, 900, 350);
        ball = new AnimatableRectF(625, 300, 675, 350);
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

    private Path DrawCurvedArrow(int x1, int y1, int x2, int y2, int curveRadius, boolean orientationUp)
    {
        Path path = new Path();
        int midX = x1 + ((x2 - x1) / 2);
        int midY = y1 + ((y2 - y1) / 2);
        float xDiff = midX - x1;
        float yDiff = midY - y1;
        double angle;

        if(orientationUp)
            angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        else
            angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) + 90;
        double angleRadians = Math.toRadians(angle);
        float pointX = (float) (midX + curveRadius * Math.cos(angleRadians));
        float pointY = (float) (midY + curveRadius * Math.sin(angleRadians));

        path.moveTo(x1, y1);
        path.cubicTo(x1,y1,pointX, pointY, x2, y2);

        return path;
    }

//    private Paint SetLineColor(int lineWidth)
//    {
//        Paint paint  = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(lineWidth);
//        paint.setColor(Color.rgb(144, 55, 66));
//        return  paint;
//    }

    private String GetSelectedShell(AnimatableRectF firstShell, AnimatableRectF secondShell, AnimatableRectF thirdShell,
                                    float touchX, float touchY) {
        if (firstShell.contains(touchX, touchY))
            return "firstShell";
        else if (secondShell.contains(touchX, touchY))
            return "secondShell";
        else if (thirdShell.contains(touchX, touchY))
            return "thirdShell";
        else
            return "You do not select shell";
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectedX = event.getX();
                selectedY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                String shellString = GetSelectedShell(firstShell, secondShell, thirdShell, selectedX, selectedY);
                Log.d("shell", shellString);
                if (shellString == "secondShell") {
                    MoveShellP1ToP2(firstShell, 4000, false);
                    MoveShellP2ToP1(secondShell, 4000, true);

                }
            default:
                return false;
        }
        return true;
    }



    private void MoveShellP1ToP2(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {


        Path pathLeft = DrawCurvedArrow(400,400,600,600,360, swapOrientationUp);
        Path pathTop = DrawCurvedArrow(200,200,200,200,360, swapOrientationUp);
        Path pathRight = DrawCurvedArrow(500,500,700,700,360, swapOrientationUp);
        Path pathBottom = DrawCurvedArrow(350,350,350,350,360, swapOrientationUp);

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", "left", pathLeft);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "top", "top", pathTop);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "right", "right", pathRight);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", "bottom", pathBottom);

        animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
        rectAnimation.setDuration(milisecondDuration).start();
    }

    private void MoveShellP1ToP3(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        Path pathLeft = DrawCurvedArrow(400,400,600,600,360, swapOrientationUp);
        Path pathTop = DrawCurvedArrow(200,200,200,200,360, swapOrientationUp);
        Path pathRight = DrawCurvedArrow(500,500,700,700,360, swapOrientationUp);
        Path pathBottom = DrawCurvedArrow(350,350,350,350,360, swapOrientationUp);

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", "left", pathLeft);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "top", "top", pathTop);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "right", "right", pathRight);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", "bottom", pathBottom);

        animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
        rectAnimation.setDuration(milisecondDuration).start();
    }

    private void MoveShellP2ToP1(AnimatableRectF rectF, int milisecondDuration, boolean swapOrientationUp) {

        Path pathLeft = DrawCurvedArrow(600,600,400,400,360, swapOrientationUp);
        Path pathTop = DrawCurvedArrow(200,200,200,200,360, swapOrientationUp);
        Path pathRight = DrawCurvedArrow(700,700,500,500,360, swapOrientationUp);
        Path pathBottom = DrawCurvedArrow(350,350,350,350,360, swapOrientationUp);

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", "left", pathLeft);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "top", "top", pathTop);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "right", "right", pathRight);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", "bottom", pathBottom);

        animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
        rectAnimation.setDuration(milisecondDuration).start();
    }


}