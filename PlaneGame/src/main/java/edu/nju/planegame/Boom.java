package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gdr on 15-7-18.
 */
public class Boom {
    private Bitmap bmpBoom;
    private int boomX,boomY;
    private int currentFrameIndex;

    private int totalFrame;
    private int frameW,frameH;

    private boolean playEnd;

    public Boom(Bitmap bmpBoom,int x,int y,int totalFrame){
        this.bmpBoom=bmpBoom;
        this.boomX=x;
        this.boomY=y;
        this.totalFrame=totalFrame;
        this.frameW=bmpBoom.getWidth()/totalFrame;
        this.frameH=bmpBoom.getHeight();
    }
    public void draw(Canvas canvas,Paint paint){
        canvas.save();
        canvas.clipRect(boomX,boomY,boomX+frameW,boomY+frameH);
        canvas.drawBitmap(bmpBoom,boomX-currentFrameIndex*frameW,boomY,paint);
        canvas.restore();
    }
    public void logic(){
        if(currentFrameIndex<totalFrame){
            currentFrameIndex++;
        }else{
            playEnd=true;
        }
    }
    public boolean isEnd(){
        return this.playEnd;
    }

}
