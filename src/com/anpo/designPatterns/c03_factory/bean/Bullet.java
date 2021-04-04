package com.anpo.designPatterns.c03_factory.bean;

import java.awt.*;

public abstract class Bullet {

    public abstract void paint(Graphics g);

    public abstract void collidedWith(Tank tank);
}
