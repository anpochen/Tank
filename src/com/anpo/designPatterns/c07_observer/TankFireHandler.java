package com.anpo.designPatterns.c07_observer;

import com.anpo.tank.bean.Tank;

import java.io.Serializable;

public class TankFireHandler implements TankFireObserver, Serializable {
    @Override
    public void actionOnFire(TankFireEvent tankFireEvent) {
        Tank t = tankFireEvent.getSource();
        t.fire();
    }
}
