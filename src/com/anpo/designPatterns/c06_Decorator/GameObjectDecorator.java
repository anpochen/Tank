package com.anpo.designPatterns.c06_Decorator;

import com.anpo.tank.bean.GameObject;

import java.awt.*;

public abstract class GameObjectDecorator extends GameObject {

    GameObject gameObject;

    public GameObjectDecorator(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public abstract void paint(Graphics graphics);
}
