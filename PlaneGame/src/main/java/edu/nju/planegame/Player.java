package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by gdr on 15-7-15.
 */
public class Player {
    private int playerHp=3;
    public int getPlayerHp() {
        return playerHp;
    }

    public void setPlayerHp(int playerHp) {
        this.playerHp = playerHp;
    }



    private Bitmap bmpPlayerHp;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x,y;
    private Bitmap bmpPlayer;

    private int speed=5;

    private boolean isUp, isDown, isLeft, isRight;

    private int noCollisionCount=0;

    private int noCollisionTime=60;

    private boolean isCollision=false;

    private Bitmap rightBtn,leftBtn,downBtn,upBtn;
    public Player(Bitmap bmpPlayer,Bitmap bmpPlayerHp,Bitmap rightBtn,Bitmap leftBtn,Bitmap downBtn,Bitmap upBtn){
        this.bmpPlayer=bmpPlayer;
        this.bmpPlayerHp=bmpPlayerHp;
        this.rightBtn=rightBtn;
        this.leftBtn=leftBtn;
        this.downBtn=downBtn;
        this.upBtn=upBtn;
        x=MySurfaceView.screenW/2-bmpPlayer.getWidth()/2;
        y=MySurfaceView.screenH-bmpPlayer.getHeight();
    }

    public void draw(Canvas canvas,Paint paint){
        if(isCollision){
            if(noCollisionCount%2==0){
                canvas.drawBitmap(this.bmpPlayer,x,y,paint);
            }
        }else{
            canvas.drawBitmap(this.bmpPlayer,x,y,paint);
        }


        for(int i=0;i<this.playerHp;i++){
            canvas.drawBitmap(bmpPlayerHp,i*bmpPlayerHp.getWidth(),MySurfaceView.screenH-bmpPlayerHp.getHeight(),paint);
        }
        canvas.drawBitmap(this.rightBtn,MySurfaceView.screenW-this.rightBtn.getWidth(),MySurfaceView.screenH-this.rightBtn.getHeight()-this.downBtn.getHeight(),paint);
        canvas.drawBitmap(this.leftBtn,MySurfaceView.screenW-this.leftBtn.getWidth()*2,MySurfaceView.screenH-this.leftBtn.getHeight()-this.downBtn.getHeight(),paint);
        canvas.drawBitmap(this.downBtn,MySurfaceView.screenW-this.rightBtn.getWidth()-this.downBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight(),paint);
        canvas.drawBitmap(this.upBtn,MySurfaceView.screenW-this.rightBtn.getWidth()-this.upBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight()-this.rightBtn.getHeight()-this.upBtn.getHeight(),paint);

    }
    public void onTouchEvent(MotionEvent event){
        int action=event.getAction();
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth(),MySurfaceView.screenH-this.rightBtn.getHeight()-this.downBtn.getHeight(),MySurfaceView.screenW,MySurfaceView.screenH-this.downBtn.getHeight())){
                    isRight=true;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.leftBtn.getWidth()*2,MySurfaceView.screenH-this.leftBtn.getHeight()-this.downBtn.getHeight(),MySurfaceView.screenW-this.leftBtn.getWidth(),MySurfaceView.screenH-this.downBtn.getHeight())){
                    isLeft=true;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth()-this.downBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight(),MySurfaceView.screenW-this.rightBtn.getWidth()+this.downBtn.getWidth()/2,MySurfaceView.screenH)){
                    isDown=true;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth()-this.upBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight()-this.rightBtn.getHeight()-this.upBtn.getHeight(),MySurfaceView.screenW-this.rightBtn.getWidth()+this.upBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight()-this.rightBtn.getHeight())){
                    isUp=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth(),MySurfaceView.screenH-this.rightBtn.getHeight()-this.downBtn.getHeight(),MySurfaceView.screenW,MySurfaceView.screenH-this.downBtn.getHeight())){
                    isRight=false;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.leftBtn.getWidth()*2,MySurfaceView.screenH-this.leftBtn.getHeight()-this.downBtn.getHeight(),MySurfaceView.screenW-this.leftBtn.getWidth(),MySurfaceView.screenH-this.downBtn.getHeight())){
                    isLeft=false;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth()-this.downBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight(),MySurfaceView.screenW-this.rightBtn.getWidth()+this.downBtn.getWidth()/2,MySurfaceView.screenH)){
                    isDown=false;
                }
                if(this.contains(x,y,MySurfaceView.screenW-this.rightBtn.getWidth()-this.upBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight()-this.rightBtn.getHeight()-this.upBtn.getHeight(),MySurfaceView.screenW-this.rightBtn.getWidth()+this.upBtn.getWidth()/2,MySurfaceView.screenH-this.downBtn.getHeight()-this.rightBtn.getHeight())){
                    isUp=false;
                }
                break;
            default:
                break;

        }

    }
    private boolean contains(int x,int y,float left,float top,float right,float bottom){
        boolean contain=false;
        if(x>=left&&x<=right&&y>=top&&y<=bottom){
            contain=true;
        }
        return contain;

    }
    public boolean isCollisionWith(Enemy en){
        boolean collision=false;
        if(!this.isCollision){
            int x2 = en.x;
            int y2 = en.y;
            int w2 = en.frameW;
            int h2 = en.frameH;
            if (x >= x2 && x >= x2 + w2) {
//                return false;
            } else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
//                return false;
            } else if (y >= y2 && y >= y2 + h2) {
//                return false;
            } else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
//                return false;
            }else{
                this.isCollision=true;
                collision=true;
            }

        }
        return collision;

    }
    public boolean isCollisionWith(Bullet bullet){
        boolean collision=false;
        if(!isCollision){
            int x2=bullet.bulletX;
            int y2=bullet.bulletY;
            int w2=bullet.getWidth();
            int h2=bullet.getHeight();
            if(x>=x2+w2||x<=x2-w2||y>=y2+h2||y<=y2-h2){
                collision=false;
            }else{
                collision=true;
                isCollision=true;
            }


        }

        return collision;
    }

    public void logic(){
        if(isLeft){
            x-=speed;
        }
        if(isRight){
            x+=speed;
        }
        if(isUp){
            y-=speed;
        }
        if(isDown){
            y+=speed;
        }
        if(x+this.bmpPlayer.getWidth()>=MySurfaceView.screenW){
            x=MySurfaceView.screenW-this.bmpPlayer.getWidth();
        }else if(x<=0){
            x=0;
        }
        if(y+this.bmpPlayer.getHeight()>=MySurfaceView.screenH){
            y=MySurfaceView.screenH-this.bmpPlayer.getHeight();
        }else if(y<=0){
            y=0;
        }
        if(this.isCollision){
            noCollisionCount++;
            if(noCollisionCount>=noCollisionTime){
                isCollision=false;
                noCollisionCount=0;
            }
        }
    }



}
