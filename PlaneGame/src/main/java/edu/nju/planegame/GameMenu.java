package edu.nju.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by gdr on 15-7-13.
 */
public class GameMenu {
    private Bitmap bmpMenu;
    private Bitmap bmpButton,bmpButtonPress;

    private int btnX,btnY;

    private boolean isPress;

    public GameMenu(Bitmap bmpMenu,Bitmap bmpButton,Bitmap bmpButtonPress){
        this.bmpMenu=bmpMenu;
        this.bmpButton=bmpButton;
        this.bmpButtonPress=bmpButtonPress;

        this.btnX=MySurfaceView.screenW/2-this.bmpButton.getWidth()/2;
        this.btnY=MySurfaceView.screenH-this.bmpButton.getHeight();

        isPress=false;
    }

    public void draw(Canvas canvas,Paint paint){
        canvas.drawBitmap(this.bmpMenu,0,0,paint);
        if(this.isPress){
            canvas.drawBitmap(this.bmpButtonPress,this.btnX,this.btnY,paint);

        }else{
            canvas.drawBitmap(this.bmpButton,this.btnX,this.btnY,paint);
        }
    }

    public void onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(x>btnX&&x<btnX+this.bmpButton.getWidth()){
                    if(y>btnY&&y<btnY+this.bmpButton.getHeight()){
                        this.isPress=true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(x>btnX&&x<btnX+this.bmpButton.getWidth()){
                    if(y>btnY&&y<btnY+this.bmpButton.getHeight()){
                        this.isPress=false;
                        MySurfaceView.gamestate=MySurfaceView.GAMEING;
                    }
                }
                break;
            default:
                break;
        }
    }
}
