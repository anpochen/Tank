package com.anpo.designPatterns.c03_factory.factory;

import com.anpo.designPatterns.c03_factory.bean.Bullet;
import com.anpo.designPatterns.c03_factory.bean.Explode;
import com.anpo.designPatterns.c03_factory.bean.Tank;
import com.anpo.tank.baseBean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

public abstract class GameFactory {

    public abstract Tank createTank(int x, int y, Direction direction, Group group, TankFrame tankFrame);

    public abstract Bullet createBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame);

    public abstract Explode createExplode(int x, int y, TankFrame tankFrame);

}
