package com.anpo.tank.bean;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    public int x,y;
    public int width,height;

    public abstract void paint(Graphics g);
}
