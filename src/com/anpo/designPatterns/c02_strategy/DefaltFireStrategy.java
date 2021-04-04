package com.anpo.designPatterns.c02_strategy;

import com.anpo.designPatterns.c03_factory.bean.Tank;
import com.anpo.tank.baseBean.Audio;
import com.anpo.tank.baseBean.BaseBullet;
import com.anpo.tank.baseBean.BaseTank;
import com.anpo.tank.enums.Group;

public class DefaltFireStrategy implements FireStrategy {

    private static final DefaltFireStrategy defaltFireStrategy = new DefaltFireStrategy();

    private DefaltFireStrategy() {
    }

    public static DefaltFireStrategy getInstance(){
        return defaltFireStrategy;
    }

    @Override
    public void fire(Tank tank) {
        int bulletX = tank.getX() + BaseTank.WIDTH/2 - BaseBullet.WIDTH/2;
        int bulletY = tank.getY() + BaseTank.HEIGHT/2 - BaseBullet.HEIGHT/2;
//        new Bullet(bulletX,bulletY,baseTank.getDirection(),baseTank.getGroup(),baseTank.getTankFrame());
        tank.getTankFrame().gameFactory.createBullet(bulletX,bulletY, tank.getDirection(), tank.getGroup(), tank.getTankFrame());
        if (tank.getGroup() == Group.GOOD){
            new Thread(()->new Audio("com/anpo/resource/audio/tank_fire.wav").play()).start();
        }
    }
}
