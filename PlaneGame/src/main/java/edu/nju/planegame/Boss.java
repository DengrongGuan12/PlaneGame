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
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getHp(){
        return this.hp;
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
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_UP_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet,x+30,y,Bullet.BULLET_BOSS,Bullet.DIR_DOWN_RIGHT));

            }
            y += speed;
            if(y<=0){
                isCrazy = false;
                speed = 5;
            }

        }
    }
    public boolean isCollisionWith(Bullet bullet){
        int x2 = bullet.bulletX;
        int y2 = bullet.bulletY;
        int w2 = bullet.getWidth();
        int h2 = bullet.getHeight();
        if(x >= x2+w2){
            return false;
        }else if(x <= x2-frameW){
            return false;
        }else if(y >= y2+h2){
            return false;
        }else if(y <= y2 -frameH){
            return false;
        }
        return true;
    }
    public void setHp(int hp){
        this.hp = hp;
    }
}
