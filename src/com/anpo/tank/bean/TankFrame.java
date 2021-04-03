package com.anpo.tank.bean;

import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 700;

    public List<Tank> tanks = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();

    Tank myTank = new Tank(200,200, Direction.DOWN, Group.GOOD,this);
//    Bullet bullet = new Bullet(200,200,Direction.DOWN,this);

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        addKeyListener(new MikeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.GREEN);
        g.drawString("敌方坦克的数量："+ tanks.size(),10, 60);
        g.drawString("子弹的数量："+ bullets.size(),10, 80);
        g.setColor(color);

        myTank.paint(g);
//        bullet.paint(g);
        /**
         * 第一种，这种普通的遍历，同时在子弹类的paint方法中删除元素不会报错
         */
        for (int i = 0; i<bullets.size();i++){
            Bullet bullet = bullets.get(i);
            bullet.paint(g);
        }

        /**
         * 这种写法在需要同时删除bullets中的元素时会报错
         */
        /*for (Bullet bullet: bullets) {
            bullet.paint(g);
        }*/

        /**
         * 第二种，使用Iterator，只能在这里删除无效的子弹
         */
        /*Iterator<Bullet> iterator = bullets.iterator();
        for (;iterator.hasNext();){
            Bullet bullet= iterator.next();
            if (!bullet.isAlive()){
                // 调用这个删除方法也会报错
                //bullets.remove(bullet);
                iterator.remove();
            }
            bullet.paint(g);
        }*/

        /**
         * 显示敌方坦克
         */
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        //碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidedWith(tanks.get(j));
            }
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
    }

    /**
     * 以下方法是为了解决游戏画面会闪烁的问题
     */
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    class MikeyListener extends KeyAdapter {

        boolean bl = false;
        boolean br = false;
        boolean bu = false;
        boolean bd = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            /**
             * 一次只能一个方向
             */
            /*switch (keyCode){
                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                default:
                    break;
            }*/
            /**
             * 尝试斜着走，未成功
             */
            if(keyCode == KeyEvent.VK_LEFT){
                bl = true;
            }
            if(keyCode == KeyEvent.VK_RIGHT){
                br = true;
            }
            if(keyCode == KeyEvent.VK_UP){
                bu = true;
            }
            if(keyCode == KeyEvent.VK_DOWN){
                bd = true;
            }
            setMainTankDirection();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            /*switch (keyCode){
                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                default:
                    break;
            }*/

            if(keyCode == KeyEvent.VK_LEFT){
                bl = false;
            }
            if(keyCode == KeyEvent.VK_RIGHT){
                br = false;
            }
            if(keyCode == KeyEvent.VK_UP){
                bu = false;
            }
            if(keyCode == KeyEvent.VK_DOWN){
                bd = false;
            }
            if(keyCode == KeyEvent.VK_SPACE){
                myTank.fire();
//                System.out.println("fire...");
            }
            setMainTankDirection();
        }

        private void setMainTankDirection() {

            if(!bl && !br && !bu && !bd){
                myTank.setMoving(false);
            }else{
                myTank.setMoving(true);
                if(bl){
                    myTank.setDirection(Direction.LEFT);
                }
                if(br){
                    myTank.setDirection(Direction.RIGHT);
                }
                if(bu){
                    myTank.setDirection(Direction.UP);
                }
                if(bd){
                    myTank.setDirection(Direction.DOWN);
                }
            }

//            new Thread(()->new Audio("com/anpo/resource/audio/tank_move.wav").play()).start();
        }
    }
}
