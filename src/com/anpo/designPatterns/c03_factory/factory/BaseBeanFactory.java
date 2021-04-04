package com.anpo.designPatterns.c03_factory.factory;

import com.anpo.designPatterns.c03_factory.bean.Bullet;
import com.anpo.designPatterns.c03_factory.bean.Explode;
import com.anpo.designPatterns.c03_factory.bean.Tank;
import com.anpo.tank.baseBean.BaseBullet;
import com.anpo.tank.baseBean.BaseExplode;
import com.anpo.tank.baseBean.BaseTank;
import com.anpo.tank.baseBean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

public class BaseBeanFactory extends GameFactory {

    @Override
    public Tank createTank(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new BaseTank(x,y,direction,group,tankFrame);
    }

    @Override
    public Bullet createBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new BaseBullet(x,y,direction,group,tankFrame);
    }

    @Override
    public Explode createExplode(int x, int y, TankFrame tankFrame) {
        return new BaseExplode(x,y,tankFrame);
    }
}
