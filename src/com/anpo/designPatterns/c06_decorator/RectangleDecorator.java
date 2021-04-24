package com.anpo.designPatterns.c06_decorator;

import com.anpo.tank.bean.GameObject;

import java.awt.*;

public class RectangleDecorator extends GameObjectDecorator{

    public RectangleDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics graphics) {
        this.x = gameObject.x;
        this.y = gameObject.y;
        this.width = gameObject.width;
        this.height = gameObject.height;
        gameObject.paint(graphics);

        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.drawRect(x,y,width,height);

        graphics.setColor(color);

    }
}
