package com.anpo.tank.bean;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.enums.Direction;
import com.anpo.resource.ResourceManager;
import com.anpo.tank.enums.Group;
import com.anpo.tank.frame.TankFrame;

import java.awt.*;

public class Bullet extends GameObject{
    private static final int SPEED = PropertyManager.getInt("bulletSpeed");
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();

    private Direction direction;
    private Group group;

    Rectangle rectangle = new Rectangle();

    private boolean alive = true;

    public Bullet(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.direction = direction;
        this.group = group;

        rectangle.setRect(x,y,WIDTH,HEIGHT);

        GameModel.getINSTANCE().add(this);
    }
    
    public void paint(Graphics g){
        /*
          如果子弹超出范围，将其从列表中删除
         */
        if (!alive){
            GameModel.getINSTANCE().remove(this);
        }

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

    public void die() {
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
