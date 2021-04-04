package com.anpo.designPatterns.c04_facade_mediator.model;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c05_chainOfResponsibility.BulletTankCollider;
import com.anpo.designPatterns.c05_chainOfResponsibility.Collider;
import com.anpo.designPatterns.c05_chainOfResponsibility.TankTankCollider;
import com.anpo.tank.bean.GameObject;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.frame.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public List<GameObject> gameObjects = new ArrayList<>();

    Collider bulletTankCollide = new BulletTankCollider();
    Collider tankTankCollide = new TankTankCollider();

    int myTankX = PropertyManager.getInt("gameWidth")/2;
    int myTankY = PropertyManager.getInt("gameHeight")/2;
    public Tank myTank = new Tank(myTankX,myTankY, Direction.DOWN, false, Group.GOOD,this);

    public GameModel() {

        //初始化敌方坦克
        int initTankCount = PropertyManager.getInt("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            int x = (int) (Math.random()*(TankFrame.GAME_WIDTH*0.9));
            int y = (int) (Math.random()*(TankFrame.GAME_HEIGHT*0.9));
            gameObjects.add(new Tank(x,y, Direction.DOWN, true, Group.BAD,this));
        }
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.GREEN);
//        g.drawString("敌方坦克的数量："+ tanks.size(),10, 60);
//        g.drawString("子弹的数量："+ bullets.size(),10, 80);
        g.setColor(color);

        myTank.paint(g);

        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }

        //碰撞检测
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i+1; j < gameObjects.size(); j++) {
                bulletTankCollide.collide(gameObjects.get(i),gameObjects.get(j),this);
                tankTankCollide.collide(gameObjects.get(i),gameObjects.get(j),this);
            }
        }
    }
}
