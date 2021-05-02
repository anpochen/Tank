package com.anpo.tank.bean;

import com.anpo.config.PropertyManager;
import com.anpo.net.Client;
import com.anpo.net.msg.TankDirectionChangeMsg;
import com.anpo.net.msg.TankStartMovingMsg;
import com.anpo.net.msg.TankStopMovingMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    public static final int GAME_WIDTH = PropertyManager.getInt("gameWidth");
    public static final int GAME_HEIGHT = PropertyManager.getInt("gameHeight");

    public static final TankFrame INSTANCE = new TankFrame();
    //网络版不需要
    //public List<Tank> tanks = new ArrayList<>();

    public Map<UUID,Tank> tanks = new HashMap<>();
    public List<Bullet> bullets = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();

    Random random = new Random();

    public Tank getMyTank() {
        return myTank;
    }

    Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
    private Tank myTank = new Tank((random.nextInt(GAME_WIDTH)),random.nextInt(GAME_HEIGHT), direction, Group.GOOD,this);
//    Bullet bullet = new Bullet(200,200,Direction.DOWN,this);

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        //setVisible(true);

        addKeyListener(new MykeyListener());

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
        g.drawString("敌人坦克的数量："+ tanks.size(),10, 60);
        g.drawString("子弹的数量："+ bullets.size(),10, 80);
        g.drawString("爆炸的数量："+ explodes.size(),10, 100);
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
         * 显示敌方坦克  单机版
         */
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }

        //碰撞检测  单机版
       /* for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidedWith(tanks.get(j));
            }
        }*/

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        //显示所有坦克  网络版
//        tanks.values().stream().forEach((e)->paint(g));

        for (Map.Entry<UUID,Tank> entry: tanks.entrySet()) {
            Tank tank = entry.getValue();
            tank.paint(g);
        }

        //碰撞检测  网络版
        Collection<Tank> tankCollection = tanks.values();
        for (int i = 0; i < bullets.size(); i++) {
            for (Tank t: tankCollection) {
                bullets.get(i).collidedWith(t);
            }
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

    class MykeyListener extends KeyAdapter {

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
                Client.INSTANCE.send(new TankStopMovingMsg(getMyTank()));
            }else{
                Direction direction = myTank.getDirection();

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

                //只在开始移动时发送，减少发送的消息数量
                if(!myTank.isMoving()){
                    Client.INSTANCE.send(new TankStartMovingMsg(getMyTank()));
                }
                myTank.setMoving(true);

                if(direction != myTank.getDirection()){
                    Client.INSTANCE.send(new TankDirectionChangeMsg(getMyTank()));
                }
            }

//            new Thread(()->new Audio("com/anpo/resource/audio/tank_move.wav").play()).start();
        }
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    public void addTank(Tank t) {
        tanks.put(t.getUuid(), t);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
}
