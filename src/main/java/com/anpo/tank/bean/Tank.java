package com.anpo.tank.bean;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.strategy.DefaltFireStrategy;
import com.anpo.designPatterns.strategy.FourDirectionFireStrategy;
import com.anpo.net.msg.TankJoinMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.resource.ResourceManager;
import com.anpo.tank.enums.Group;

import java.awt.*;

import java.util.Random;
import java.util.UUID;

public class Tank {
    public static final int WIDTH = ResourceManager.goodTankD.getWidth();
    public static final int HEIGHT = ResourceManager.goodTankD.getHeight();

    private UUID uuid = UUID.randomUUID();
    private int x,y;
    private static final int SPEED = PropertyManager.getInt("tankSpeed");
    private Direction direction;
    private Group group;
    private TankFrame tankFrame;

    Rectangle rectangle = new Rectangle();

    private final Random random = new Random();

    private boolean moving = true;
    private boolean alive = true;

    public Tank(int x, int y, Direction direction,Group group,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.setRect(x,y,WIDTH,HEIGHT);
    }

    public Tank(TankJoinMsg msg) {
        this.x = msg.x;
        this.y = msg.y;
        this.direction = msg.direction;
        this.moving = msg.isMoving();
        this.group = msg.group;
        this.uuid = msg.uuid;

        rectangle.setRect(x,y,WIDTH,HEIGHT);
    }

    public void paint(Graphics g) {
        if (!alive){
            tankFrame.tanks.remove(this);
        }

        /*Color color = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(x,y,50,50);
        g.drawString("子弹的数量："+ bullets.size(),10,60);
        g.setColor(color);*/

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
