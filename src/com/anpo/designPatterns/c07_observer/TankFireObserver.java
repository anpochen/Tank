package com.anpo.designPatterns.c07_observer;

public interface TankFireObserver {

    public abstract void actionOnFire(TankFireEvent tankFireEvent);
}
