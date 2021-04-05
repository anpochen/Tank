package com.anpo.tank.bean;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c02_strategy.DefaltFireStrategy;
import com.anpo.designPatterns.c02_strategy.FourDirectionFireStrategy;
import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.enums.Direction;
import com.anpo.resource.ResourceManager;
import com.anpo.tank.enums.Group;
import com.anpo.tank.frame.TankFrame;

import java.awt.*;

import java.util.Random;

public class Tank extends GameObject{
    public static final int WIDTH = ResourceManager.goodTankD.getWidth();
    public static final int HEIGHT = ResourceManager.goodTankD.getHeight();

    private int oldX,oldY;
    private static final int SPEED = PropertyManager.getInt("tankSpeed");
    private Direction direction;
    private Group group;

    Rectangle rectangle = new Rectangle();

    private final Random random = new Random();

    private boolean moving = true;
    private boolean alive = true;

    public Tank(int x, int y, Direction direction, boolean moving, Group group) {
        this.x = x;
        this.y = y;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.direction = direction;
        this.moving = moving;
        this.group = group;

        rectangle.setRect(x,y,WIDTH,HEIGHT);

        GameModel.getINSTANCE().add(this);
    }

    public void paint(Graphics g) {
        if (!alive){
            GameModel.getINSTANCE().remove(this);
        }

        switch (direction){
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL,x,y,null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR,x,y,null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU,x,y,null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD,x,y,null);
                break;
        }
        move();
    }

    private void move() {
        oldX = x;
        oldY = y;
        if (!moving){
            return;
        }else {
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
        if(this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }
        if(this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDirection();
        }
        boundsCheck();

        //update rectangle
        rectangle.setLocation(x,y);
    }

    //边界检测
    private void boundsCheck() {
        if (x<5){
            x = 5;
            reverseDirection();
        }
        if(y<25){
            y = 25;
            reverseDirection();
        }
        if(x> TankFrame.GAME_WIDTH * 0.99 - Tank.WIDTH){
            x = (int) (TankFrame.GAME_WIDTH * 0.99 - Tank.WIDTH);
            reverseDirection();
        }
        if(y > TankFrame.GAME_HEIGHT * 0.99 - Tank.HEIGHT){
            y = (int) (TankFrame.GAME_HEIGHT * 0.99 - Tank.HEIGHT);
            reverseDirection();
        }
    }

    //边界时调转方向
    public void reverseDirection() {
        //影响两个坦克分开，回到原位与调转方向不能一起使用
        x = oldX;
        y = oldY;
        if (this.group == Group.GOOD){
            return;
        }
        if(direction == Direction.LEFT){
            direction = Direction.RIGHT;
            return;
        }
        if(direction == Direction.RIGHT){
            direction = Direction.LEFT;
            return;
        }
        if (direction == Direction.UP){
            direction = Direction.DOWN;
            return;
        }
        if (direction == Direction.DOWN){
            direction = Direction.UP;
        }
    }

    private void randomDirection() {
        this.direction = Direction.values()[random.nextInt(4)];
    }

    public void fire() {
        if (this.group == Group.BAD){
            DefaltFireStrategy.getInstance().fire(this);
        }else {
            FourDirectionFireStrategy.getInstance().fire(this);
        }
    }

    public void die() {
        this.alive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
