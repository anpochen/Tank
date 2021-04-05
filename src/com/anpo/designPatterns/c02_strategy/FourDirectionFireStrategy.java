package com.anpo.designPatterns.c02_strategy;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.designPatterns.c06_Decorator.RectangleDecorator;
import com.anpo.designPatterns.c06_Decorator.TailDecorator;
import com.anpo.resource.Audio;
import com.anpo.tank.bean.Bullet;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

public class FourDirectionFireStrategy implements FireStrategy{

    private static final FourDirectionFireStrategy fourDirectionFireStrategy = new FourDirectionFireStrategy();

    private FourDirectionFireStrategy() {
    }

    public static FourDirectionFireStrategy getInstance(){
        return fourDirectionFireStrategy;
    }

    @Override
    public void fire(Tank tank) {
        //已经死了的坦克不会发出子弹
        if(!tank.isAlive()){
            return;
        }
        int bulletX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bulletY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        Direction [] directions = Direction.values();
        for (Direction direction : directions) {
            GameModel.getINSTANCE().add(new TailDecorator(new RectangleDecorator(new Bullet(bulletX, bulletY, direction, tank.getGroup()))));
        }
        if (tank.getGroup() == Group.GOOD){
            new Thread(()->new Audio("com/anpo/resource/audio/tank_fire.wav").play()).start();
        }
    }
}
