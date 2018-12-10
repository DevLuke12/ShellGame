package com.example.lukas.shellgame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class AnimatableRectF extends RectF
{
    private View View;
    public AnimatableRectF() {
        super();
    }

    public AnimatableRectF(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    public AnimatableRectF(RectF r) {
        super(r);
    }

//    public AnimatableRectF(Rect r) {
//        super(r);
//    }

    public void setTop(float top){
        this.top = top;
    }
    public void setBottom(float bottom){
        this.bottom = bottom;
    }
    public void setRight(float right){
        this.right = right;
    }
    public void setLeft(float left){
        this.left = left;
    }

    public void Animate(AnimatableRectF rectF, View view, float newLeft,float newRight,
                        int curveRadius, int milisecondDuration, boolean swapOrientationUp)
    {
        this.View = view;

        Path pathLeft = DrawCurvedArrow(rectF.left, rectF.left, newLeft, newLeft, curveRadius, swapOrientationUp);
        Path pathTop = DrawCurvedArrow(rectF.top, rectF.top, rectF.top, rectF.top, curveRadius, swapOrientationUp);
        Path pathRight = DrawCurvedArrow(rectF.right, rectF.right, newRight, newRight, curveRadius, swapOrientationUp);
        Path pathBottom = DrawCurvedArrow(rectF.bottom, rectF.bottom, rectF.bottom, rectF.bottom, curveRadius, swapOrientationUp);

        //Path pathLeft = DrawCurvedArrow(400,400,600,600,360, swapOrientationUp);
//        Path pathTop = DrawCurvedArrow(200,200,200,200,360, swapOrientationUp);
//        Path pathRight = DrawCurvedArrow(500,500,700,700,360, swapOrientationUp);
//        Path pathBottom = DrawCurvedArrow(350,350,350,350,360, swapOrientationUp);

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", "left", pathLeft);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "top", "top", pathTop);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "right", "right", pathRight);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", "bottom", pathBottom);

        Update(animateBottom);

        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
        rectAnimation.setDuration(milisecondDuration).start();
    }


    public void AnimateUpOrDown(AnimatableRectF rectF, View view, float newX,float newY, int milisecondDuration, int delay)
    {
        this.View = view;

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(rectF, "left", rectF.left, rectF.left + newX);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(rectF, "right", rectF.right, rectF.right + newX);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(rectF, "top", rectF.top, rectF.top + newY);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(rectF, "bottom", rectF.bottom, rectF.bottom + newY);

        Update(animateBottom);

        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
        rectAnimation.setDuration(milisecondDuration).setStartDelay(delay);
        rectAnimation.start();

        if(!rectAnimation.isRunning())
        {
            rectF.setBottom( rectF.bottom + newY);
            rectF.setLeft( rectF.left + newX);
            rectF.setRight(rectF.right + newX);
            rectF.setTop( rectF.top + newY);
        }
    }
    private void Update(ObjectAnimator animate)
    {
        animate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                View.invalidate();
            }
        });
    }

    private Path DrawCurvedArrow(float x1, float y1, float x2, float y2, int curveRadius, boolean orientationUp)
    {
        Path path = new Path();
        float midX = x1 + ((x2 - x1) / 2);
        float midY = y1 + ((y2 - y1) / 2);
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
}
