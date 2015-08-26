package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gdr on 15-7-16.
 */
public class Enemy {
    public int type;
    public static final int TYPE_FLY=1;

    public static final int TYPE_DUCKL=2;

    public static final int TYPE_DUCKR=3;

    public Bitmap bmpEnemy;

    public int x,y;

    public int frameW,frameH;
    private int frameIndex;
    private int speed;

    public boolean isDead;

    public Enemy(Bitmap bmpEnemy,int enemyType,int x,int y){
        this.bmpEnemy=bmpEnemy;
        frameW=this.bmpEnemy.getWidth()/10;
        frameH=this.bmpEnemy.getHeight();

        this.type=enemyType;

        this.x=x;
        this.y=y;

        switch (type){
            case TYPE_FLY:
                speed=25;
                break;
            case TYPE_DUCKL:
                speed=3;
                break;
            case TYPE_DUCKR:
                speed=3;
                break;
            default:
                break;
        }

    }
    public void draw(Canvas canvas,Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bmpEnemy,x-frameIndex*frameW,y,paint);
        canvas.restore();
    }

    public void logic(){
        this.frameIndex++;
        if(frameIndex>=10){
            frameIndex=0;
        }
        switch (type){
            case TYPE_FLY:
                if(!isDead){
                    speed-=1;
                    y+=speed;
                    if(y<-200){
                        isDead=true;
                    }
                }
                break;
            case TYPE_DUCKL:
                if(!isDead){
                    x+=speed/2;
                    y+=speed;
                    if(x>MySurfaceView.screenW){
                        isDead=true;
                    }
                }
                break;
            case TYPE_DUCKR:
                if(!isDead){
                    x-=speed/2;
                    y+=speed;
                    if(x<-50){
                        isDead=true;
                    }
                }
                break;
            default:
                break;
        }
    }
    public boolean isCollisionWith(Bullet bullet){
        boolean collision=false;
        int x2=bullet.bulletX;
        int y2=bullet.bulletY;
        int w2=bullet.getWidth();
        int h2=bullet.getHeight();
        if(x>=x2+w2||x<=x2-w2||y>=y2+h2||y<=y2-h2){
            collision=false;
        }else{
            collision=true;
            isDead=true;
        }
        return collision;
    }


}
