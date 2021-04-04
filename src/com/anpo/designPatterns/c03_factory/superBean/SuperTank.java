package com.anpo.designPatterns.c03_factory.superBean;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c02_strategy.DefaltFireStrategy;
import com.anpo.designPatterns.c02_strategy.FourDirectionFireStrategy;
import com.anpo.designPatterns.c03_factory.bean.Tank;
import com.anpo.tank.baseBean.BaseTank;
import com.anpo.tank.baseBean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.awt.*;
import java.util.Random;

public class SuperTank extends Tank {

    public static final int WIDTH = PropertyManager.getInt("superTankWidth");
    public static final int HEIGHT = PropertyManager.getInt("superTankheight");

    private int x,y;
    private static final int SPEED = PropertyManager.getInt("superTankSpeed");
    private Direction direction;
    private Group group;
    private TankFrame tankFrame;

    private Rectangle rectangle = new Rectangle();

    private final Random random = new Random();

    private boolean moving = true;
    private boolean alive = true;

    public SuperTank(int x, int y, Direction direction,Group group,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.setRect(x,y,WIDTH,HEIGHT);
    }

    public void paint(Graphics g) {
        if (!alive){
            tankFrame.tanks.remove(this);
        }

        Color color = g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(x,y,getWIDTH(),getHEIGHT());

        g.setColor(color);

        move();
    }

    private void move() {
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
        if(x> TankFrame.GAME_WIDTH * 0.99 - BaseTank.WIDTH){
            x = (int) (TankFrame.GAME_WIDTH * 0.99 - BaseTank.WIDTH);
            reverseDirection();
        }
        if(y > TankFrame.GAME_HEIGHT * 0.99 - BaseTank.HEIGHT){
            y = (int) (TankFrame.GAME_HEIGHT * 0.99 - BaseTank.HEIGHT);
            reverseDirection();
        }
    }

    //边界时调转方向
    private void reverseDirection() {
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

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
