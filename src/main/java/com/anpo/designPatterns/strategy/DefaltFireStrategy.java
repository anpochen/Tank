package com.anpo.designPatterns.strategy;

import com.anpo.tank.bean.Audio;
import com.anpo.tank.bean.Bullet;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.enums.Group;

public class DefaltFireStrategy implements FireStrategy{

    private static final DefaltFireStrategy defaltFireStrategy = new DefaltFireStrategy();

    private DefaltFireStrategy() {
    }

    public static DefaltFireStrategy getInstance(){
        return defaltFireStrategy;
    }

    @Override
    public void fire(Tank tank) {
        int bulletX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bulletY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        new Bullet(bulletX,bulletY,tank.getDirection(),tank.getGroup(),tank.getTankFrame());
        if (tank.getGroup() == Group.GOOD){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
