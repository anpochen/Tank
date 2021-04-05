package com.anpo.designPatterns.c04_facade_mediator.model;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c05_chainOfResponsibility.ColliderChain;
import com.anpo.tank.bean.*;
import com.anpo.tank.frame.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {

    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    public List<GameObject> gameObjects = new ArrayList<>();

    ColliderChain colliderChain = new ColliderChain();

    Random random = new Random();
    int gameWidth = PropertyManager.getInt("gameWidth");
    int gameHeight = PropertyManager.getInt("gameHeight");

    int myTankX = gameWidth/2;
    int myTankY = gameHeight/2;
    public Tank myTank;

    private GameModel() {}

    void init(){
        //初始化主战坦克
        myTank = new Tank(myTankX,myTankY, Direction.DOWN, false, Group.GOOD);
        //初始化敌方坦克
        int initTankCount = PropertyManager.getInt("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            int x = (int) (Math.random()*(TankFrame.GAME_WIDTH*0.9));
            int y = (int) (Math.random()*(TankFrame.GAME_HEIGHT*0.9));
            new Tank(x,y, Direction.DOWN, true, Group.BAD);
        }

        //初始化墙
        String [] walls = PropertyManager.getString("wall").split(";");
        for (int i = 0; i < walls.length; i++) {
            String [] instance = walls[i].split(",");

            int x = (int)(Double.parseDouble(instance[0])*gameWidth);
            int y = (int)(Double.parseDouble(instance[1])*gameHeight);
            int w = (int)Double.parseDouble(instance[2]);
            int h = (int)Double.parseDouble(instance[3]);

            new Wall(x,y,w,h);
        }
    }

    public void add(GameObject gameObject){
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject){
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics g) {

        //myTank.paint(g);

        //统计坦克和炸弹的数量
        int tankNum = 0;
        int bulletNum = 0;
        int explodeNum = 0;
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            gameObject.paint(g);
            if(gameObject instanceof Tank && ((Tank) gameObject).getGroup() == Group.BAD){
                tankNum ++;
            }
            if(gameObject instanceof Bullet){
                bulletNum ++;
            }
            if(gameObject instanceof Explode){
                explodeNum ++;
            }

        }

        Color color = g.getColor();
        g.setColor(Color.GREEN);
        g.drawString("游戏物体的数量"+ gameObjects.size(),10,44);
        g.drawString("敌方坦克的数量"+ tankNum,10,60);
        g.drawString("子弹的数量"+ bulletNum,10,76);
        g.drawString("爆炸的数量"+ explodeNum,10,92);
        g.setColor(color);

        //碰撞检测
        a:for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i+1; j < gameObjects.size(); j++) {
                GameObject gameObject1 = gameObjects.get(i);
                GameObject gameObject2 = gameObjects.get(j);
                if(colliderChain.collide(gameObject1,gameObject2,this)){
                    continue a;
                }
            }
        }
    }

    public static GameModel getINSTANCE() {
        return INSTANCE;
    }
}
