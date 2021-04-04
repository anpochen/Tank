package com.anpo.designPatterns.c02_strategy;

import com.anpo.tank.bean.Audio;
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
        int bulletX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bulletY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        Direction [] directions = Direction.values();
        for (Direction direction : directions) {
            new Bullet(bulletX, bulletY, direction, tank.getGroup(), tank.getTankFrame());
        }
        if (tank.getGroup() == Group.GOOD){
            new Thread(()->new Audio("com/anpo/resource/audio/tank_fire.wav").play()).start();
        }
    }
}
