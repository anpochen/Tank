package com.anpo.designPatterns.c07_observer;

import com.anpo.tank.bean.Tank;

public class TankFireHandler implements TankFireObserver{
    @Override
    public void actionOnFire(TankFireEvent tankFireEvent) {
        Tank t = tankFireEvent.getSource();
        t.fire();
    }
}
