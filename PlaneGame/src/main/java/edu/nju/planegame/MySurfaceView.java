package edu.nju.planegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Vector;

/**
 * Created by gdr on 15-7-13.
 */
public class MySurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    public static int screenW, screenH;

    public static final int GAME_MENU=0;
    public static  final int GAMEING=1;
    public static final int GAME_WIN=2;
    public static final int GAME_LOST=3;
    public static final int GAME_PAUSE=-1;

    public static int gamestate=GAME_MENU;

    private Resources res=this.getResources();

    private Bitmap bmpBackGround;
    private Bitmap bmpBoom;
    private Bitmap bmpBoosBoom;
    private Bitmap bmpButton;
    private Bitmap bmpButtonPress;
    private Bitmap bmpEnemyDuck;
    private Bitmap bmpEnemyFly;
//    private Bitmap bmpEnemyBullet;
    private Bitmap bmpEnemyBoos;
    private Bitmap bmpGameWin;
    private Bitmap bmpGameLost;
    private Bitmap bmpPlayer;
    private Bitmap bmpPlayerHp;
    private Bitmap bmpMenu;

    private Bitmap rightBtn;
    private Bitmap leftBtn;
    private Bitmap upBtn;
    private Bitmap downBtn;


    public static Bitmap bmpBullet;
    public static Bitmap bmpEnemyBullet;
    public static Bitmap bmpBossBullet;

    private GameMenu gameMenu;

    private GameBg gameBg;

    private Player player;

    private Vector<Enemy> vcEnemy;
    private int createEnemyTime=50;
    private int count;
    private int enemyArray[][]={{ 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
            { 1, 3 }, { 2, 1 }, { -1 }};
    private int enemyIndex;
    private boolean isBoss;
    private Random random;

    private Vector<Bullet> vcBullet=new Vector<Bullet>();
    private int countEnemyBullet;

    private Vector<Bullet> vcBulletPlayer=new Vector<Bullet>();
    private int countPlayerBullet;

    private Boss boss ;
    private static Vector<Bullet> vcBulletBoss;

    private Vector<Boom> vcBoom=new Vector<Boom>();



    public MySurfaceView(Context context){
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setKeepScreenOn(true);

    }

    private void initGame(){
        if(gamestate==GAME_MENU){
            bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
            bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
            bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
            bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
            bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
            bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
            bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
            bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
            bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
            bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
            bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
            bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
            bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
            bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
            bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
            bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
            rightBtn=BitmapFactory.decodeResource(res,R.drawable.right);
            leftBtn=BitmapFactory.decodeResource(res,R.drawable.left);
            upBtn=BitmapFactory.decodeResource(res,R.drawable.up);
            downBtn=BitmapFactory.decodeResource(res,R.drawable.down);

            gameMenu=new GameMenu(bmpMenu,bmpButton,bmpButtonPress);
            this.gameBg=new GameBg(bmpBackGround);
            this.player=new Player(bmpPlayer,bmpPlayerHp,rightBtn,leftBtn,downBtn,upBtn);
            this.vcEnemy=new Vector<Enemy>();
            this.random=new Random();
            this.isBoss=false;
            this.enemyIndex=0;
            this.boss = new Boss(bmpEnemyBoos);




        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        flag = true;
        th=new Thread(this);
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        flag=false;

    }

    public void myDraw(){
        try {
            canvas=sfh.lockCanvas();
            if(canvas!=null){
                canvas.drawColor(Color.WHITE);
                switch (gamestate){
                    case GAME_MENU:
                        this.gameMenu.draw(canvas,paint);
                        break;
                    case GAMEING:
                        this.gameBg.draw(canvas,paint);
                        this.player.draw(canvas,paint);
                        if(!isBoss){
                            for(int i=0;i<this.vcEnemy.size();i++){
                                vcEnemy.elementAt(i).draw(canvas,paint);
                            }
                            for(int i=0;i<vcBullet.size();i++){
                                vcBullet.elementAt(i).draw(canvas,paint);
                            }
                        }else{
                            boss.draw(canvas,paint);
                            for(int i = 0; i<vcBulletBoss.size();i++){
                                vcBulletBoss.get(i).draw(canvas,paint);
                            }

                        }
                        for(int i=0;i<vcBulletPlayer.size();i++){
                            vcBulletPlayer.elementAt(i).draw(canvas,paint);
                        }
                        for (int i = 0; i < vcBoom.size(); i++) {
                            vcBoom.elementAt(i).draw(canvas, paint);
                        }
                        break;
                    case GAME_LOST:
                        canvas.drawBitmap(this.bmpGameLost,0,0,paint);
                        break;
                    case GAME_PAUSE:
                        break;
                    case GAME_WIN:
                        canvas.drawBitmap(this.bmpGameWin,0,0,paint);
                        break;
                }
            }
        }catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }

    }

    public boolean onTouchEvent(MotionEvent event){
        switch (gamestate) {
            case GAME_MENU:
                this.gameMenu.onTouchEvent(event);
                break;
            case GAMEING:
                this.player.onTouchEvent(event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:

                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(gamestate==GAMEING||gamestate==GAME_WIN||gamestate==GAME_LOST){
                gamestate=GAME_MENU;
                initGame();
            }else if(gamestate==GAME_MENU){
                MainActivity.instance.finish();
                System.exit(0);
            }
        }
        return true;
    }
    public boolean onKeyUp(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (gamestate == GAMEING || gamestate == GAME_WIN || gamestate == GAME_LOST) {
                gamestate = GAME_MENU;
            }
        }
        return true;
    }

    private void logic(){
        switch (gamestate){
            case GAMEING:
                this.gameBg.logic();
                this.player.logic();
                if(!isBoss){
                    for(int i=0;i<this.vcEnemy.size();i++){
                        Enemy en=this.vcEnemy.elementAt(i);
                        if(en.isDead){
                            vcEnemy.removeElementAt(i);
                        }else{
                            en.logic();
                        }
                    }
                    count++;
                    if(count%this.createEnemyTime==0){
                        for(int i=0;i<this.enemyArray[enemyIndex].length;i++){
                            if(this.enemyArray[enemyIndex][i]==1){
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
                            }
                            if(this.enemyArray[enemyIndex][i]==2){
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));

                            }
                            if(this.enemyArray[enemyIndex][i]==3){
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));

                            }
                        }
                        enemyIndex++;
                        if(enemyIndex==enemyArray.length-1){
                            isBoss=true;
                        }
                    }
                    for(int i=0;i<vcEnemy.size();i++){
                        if(player.isCollisionWith(vcEnemy.elementAt(i))){
                            player.setPlayerHp(player.getPlayerHp()-1);
                            if(player.getPlayerHp()<=0){
                                gamestate=GAME_LOST;
                            }
                        }
                    }
                    countEnemyBullet++;
                    if(countEnemyBullet%40==0){
                        for(int i=0;i<vcEnemy.size();i++){
                            Enemy en=vcEnemy.elementAt(i);
                            int bulletType=0;
                            switch (en.type){
                                case Enemy.TYPE_FLY:
                                    bulletType=Bullet.BULLET_FLY;
                                    break;
                                case Enemy.TYPE_DUCKR:
                                case Enemy.TYPE_DUCKL:
                                    bulletType=Bullet.BULLET_DUCK;
                                    break;

                            }
                            vcBullet.add(new Bullet(bmpEnemyBullet,en.x+10,en.y+20,bulletType));
                        }
                    }
                    for(int i=0;i<vcBullet.size();i++){
                        Bullet b=vcBullet.elementAt(i);
                        if(b.isDead){
                            vcBullet.removeElementAt(i);
                        }else{
                            b.logic();
                        }
                    }
                    for(int i=0;i<vcBullet.size();i++){
                        if(player.isCollisionWith(vcBullet.elementAt(i))){
                            player.setPlayerHp(player.getPlayerHp()-1);
                            if(player.getPlayerHp()<=0){
                                gamestate=GAME_LOST;
                            }
                        }
                    }

                    for(int i=0;i<vcBulletPlayer.size();i++){
                        Bullet blPlayer=vcBulletPlayer.elementAt(i);
                        for(int j=0;j<vcEnemy.size();j++){
                            if(vcEnemy.elementAt(j).isCollisionWith(blPlayer)){
                                blPlayer.isDead=true;
                                vcBoom.add(new Boom(bmpBoom,vcEnemy.elementAt(j).x,vcEnemy.elementAt(j).y,7));
                            }
                        }
                    }

                }else{

                }
                countPlayerBullet++;
                if(countPlayerBullet%20==0){
                    vcBulletPlayer.add(new Bullet(bmpBullet,player.getX()+15,player.getY()-20,Bullet.BULLET_PLAYER));
                }
                for(int i=0;i<vcBulletPlayer.size();i++){
                    Bullet b=vcBulletPlayer.elementAt(i);
                    if(b.isDead){
                        vcBulletPlayer.removeElementAt(i);
                    }else{
                        b.logic();
                    }
                }
                for(int i=0;i<vcBoom.size();i++){
                    Boom boom=vcBoom.elementAt(i);
                    if(boom.isEnd()){
                        vcBoom.removeElementAt(i);
                    }else{
                        boom.logic();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
