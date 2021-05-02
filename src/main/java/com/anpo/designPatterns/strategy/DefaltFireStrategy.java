package com.anpo.designPatterns.strategy;

import com.anpo.net.Client;
import com.anpo.net.msg.TankNewBulletMsg;
import com.anpo.tank.bean.Audio;
import com.anpo.tank.bean.Bullet;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.bean.TankFrame;
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
        Bullet bullet = new Bullet(bulletX,bulletY,tank.getDirection(),tank.getGroup(), tank.getUuid(),tank.getTankFrame());

//        TankFrame.INSTANCE.bullets.add(bullet);
        Client.INSTANCE.send(new TankNewBulletMsg(bullet));

        if (tank.getGroup() == Group.GOOD){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
