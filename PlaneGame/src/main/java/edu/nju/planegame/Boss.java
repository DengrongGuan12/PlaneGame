package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gdr on 15-7-20.
 */
public class Boss {
    private int hp=50;
    private Bitmap bmpBoss;
    private int x,y;
    private int frameW,frameH;
    private int frameIndex;
    private int speed=5;
    private boolean isCrazy;

    private int crazyTime=200;

    private int count;
    public Boss(Bitmap bmpBoss){
        this.bmpBoss=bmpBoss;
        this.frameW=bmpBoss.getWidth()/10;
        this.frameH=bmpBoss.getHeight();
        x=MySurfaceView.screenW/2-frameW/2;
        y=0;
    }
    public void draw(Canvas canvas,Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(this.bmpBoss,x-this.frameW*this.frameIndex,y,paint);
        canvas.restore();
    }
    public void logic(){
        this.frameIndex++;
        if(this.frameIndex >= 10){
            frameIndex = 0;
        }
        if(isCrazy == false){
            x += speed;
            if(x + frameW >= MySurfaceView.screenW){
                speed = -speed;
            }else if(x<=0){
                speed = -speed;
            }
            count ++;
            if(count % crazyTime == 0){
                isCrazy = true;
                speed = 24;
            }
        }else{
            speed -= 1;
            if(speed == 0){
                //add eight direction bullet

            }

        }
    }
}
