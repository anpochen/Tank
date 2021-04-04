package com.anpo.designPatterns.c03_factory.factory;

import com.anpo.designPatterns.c03_factory.bean.Bullet;
import com.anpo.designPatterns.c03_factory.bean.Explode;
import com.anpo.designPatterns.c03_factory.bean.Tank;
import com.anpo.designPatterns.c03_factory.superBean.SuperBullet;
import com.anpo.designPatterns.c03_factory.superBean.SuperExplode;
import com.anpo.designPatterns.c03_factory.superBean.SuperTank;
import com.anpo.tank.baseBean.BaseBullet;
import com.anpo.tank.baseBean.BaseExplode;
import com.anpo.tank.baseBean.BaseTank;
import com.anpo.tank.baseBean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

public class SuperBeanFactory extends GameFactory {

    @Override
    public Tank createTank(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new SuperTank(x,y,direction,group,tankFrame);
    }

    @Override
    public Bullet createBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new SuperBullet(x,y,direction,group,tankFrame);
    }

    @Override
    public Explode createExplode(int x, int y, TankFrame tankFrame) {
        return new SuperExplode(x,y,tankFrame);
    }
}
