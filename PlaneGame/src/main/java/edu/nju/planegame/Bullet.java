package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gdr on 15-7-17.
 */
public class Bullet {

    private int dir;

    public static final int DIR_UP=1;
    public static final int DIR_DOWN=2;
    public static final int DIR_LEFT=3;
    public static final int DIR_RIGHT=4;
    public static final int DIR_UP_LEFT=5;
    public static final int DIR_UP_RIGHT=6;
    public static final int DIR_DOWN_LEFT=7;
    public static final int DIR_DOWN_RIGHT=8;

    private Bitmap bmpBullet;
    public int bulletX,bulletY;
    public int speed;
    public int bulletType;

    public static final int BULLET_PLAYER=-1;
    public static final int BULLET_DUCK=1;
    public static final int BULLET_FLY=2;
    public static final int BULLET_BOSS=3;

    public boolean isDead;

    public Bullet(Bitmap bmpBullet,int bulletX,int bulletY,int bulletType){
        this.bmpBullet=bmpBullet;
        this.bulletX=bulletX;
        this.bulletY=bulletY;
        this.bulletType=bulletType;
        switch (this.bulletType){
            case BULLET_PLAYER:
                this.speed=4;
                break;
            case BULLET_DUCK:
                this.speed=3;
                break;
            case BULLET_FLY:
                this.speed=4;
                break;
            case BULLET_BOSS:
                this.speed=5;
                break;
            default:
                break;
        }
    }
    public Bullet(Bitmap bmpBullet,int bulletX,int bulletY,int bulletType,int dir){
        this.bmpBullet=bmpBullet;
        this.bulletX=bulletX;
        this.bulletY=bulletY;
        this.bulletType=bulletType;
        this.dir=dir;
        this.speed=5;
    }
    public void draw(Canvas canvas,Paint paint){
        canvas.drawBitmap(bmpBullet,bulletX,bulletY,paint);
    }
    public void logic(){
        switch (this.bulletType){
            case BULLET_PLAYER:
                this.bulletY-=speed;
                if(this.bulletY<-50){
                    isDead=true;
                }
                break;
            case BULLET_DUCK:
            case BULLET_FLY:
                bulletY+=speed;
                if(bulletY>MySurfaceView.screenH){
                    isDead=true;
                }
                break;
            case BULLET_BOSS:
                switch (this.dir){
                    case DIR_UP:
                        bulletY-=speed;
                        break;
                    case DIR_DOWN:
                        bulletY+=speed;
                        break;
                    case DIR_LEFT:
                        bulletX-=speed;
                        break;
                    case DIR_RIGHT:
                        bulletX+=speed;
                        break;
                    case DIR_DOWN_LEFT:
                        bulletX-=speed;
                        bulletY+=speed;
                        break;
                    case DIR_DOWN_RIGHT:
                        bulletX+=speed;
                        bulletY+=speed;
                        break;
                    case DIR_UP_LEFT:
                        bulletX-=speed;
                        bulletY-=speed;
                        break;
                    case DIR_UP_RIGHT:
                        bulletX+=speed;
                        bulletY-=speed;
                        break;
                }
                break;

        }
    }
    public int getWidth(){
        return this.bmpBullet.getWidth();
    }
    public int getHeight(){
        return this.bmpBullet.getHeight();
    }

}
