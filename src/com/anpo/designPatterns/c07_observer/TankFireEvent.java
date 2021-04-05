package com.anpo.designPatterns.c07_observer;

import com.anpo.tank.bean.Tank;

public class TankFireEvent {

    Tank tank;

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }

    public Tank getSource(){
        return tank;
    }



}
