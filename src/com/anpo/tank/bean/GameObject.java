package com.anpo.tank.bean;

import java.awt.*;

public abstract class GameObject {
    public int x,y;
    public int width,height;

    public abstract void paint(Graphics g);
}
