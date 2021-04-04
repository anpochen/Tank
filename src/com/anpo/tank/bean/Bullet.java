package com.anpo.tank.bean;

import com.anpo.config.PropertyManager;
import com.anpo.tank.enums.Direction;
import com.anpo.resource.ResourceManager;
import com.anpo.tank.enums.Group;

import java.awt.*;

public class Bullet {
    private static final int SPEED = PropertyManager.getInt("bulletSpeed");
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();

    private int x,y;
    private Direction direction;
    private Group group;
    private TankFrame tankFrame;

    Rectangle rectangle = new Rectangle();

    private boolean alive = true;

    public Bullet(int x, int y, Direction direction,Group group,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.setRect(x,y,WIDTH,HEIGHT);

        tankFrame.bullets.add(this);
    }
    
    public void paint(Graphics g){
        /*
          如果子弹超出范围，将其从列表中删除
         */
        if (!alive){
            tankFrame.bullets.remove(this);
        }

//        Color color = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x,y,WEIGHT,HEIGHT);
//        g.setColor(color);
        switch (direction){
            case LEFT:
                g.drawImage(ResourceManager.bulletL,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceManager.bulletR,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceManager.bulletU,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceManager.bulletD,x,y,null);
                break;
        }
        move();

        //update rectangle
        rectangle.setLocation(x,y);
        /*
          设置子弹超出范围就会设置为false，避免对象未释放造成内存泄漏
         */
        if (x<0 || y <0 || x> TankFrame.GAME_WIDTH || y> TankFrame.GAME_HEIGHT){
            alive = false;
        }
    }

    private void move() {
        switch (direction){
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
    }

    public void collidedWith(Tank tank) {
        //如果都是同一组的，那么直接返回
        if(this.group.equals(tank.getGroup())){
            return;
        }

        /*//这里每次碰撞检测时都要新建一个矩形对象，垃圾太多，放到对象中
        Rectangle rectangle1 = new Rectangle(x,y,WIDTH,HEIGHT);
        Rectangle rectangle2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);*/
        if(this.rectangle.intersects(tank.rectangle)){
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            tankFrame.explodes.add(new Explode(eX, eY,tankFrame));

        }

    }

    private void die() {
        this.alive = false;
    }

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
