package com.example.lukas.shellgame;

public class TouchPoint
{
    float touchFromX = 0;
    float touchFromY = 0;

    float touchToX = 0;
    float touchToY = 0;

    public boolean IsSwiftLineUpCorrect(TouchPoint point)
    {
       if(point.touchToY < point.touchFromY - 100)
           return true;
       else
           return false;
    }

}
