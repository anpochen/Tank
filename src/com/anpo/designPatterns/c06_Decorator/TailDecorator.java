package com.anpo.designPatterns.c06_Decorator;

import com.anpo.designPatterns.c05_chainOfResponsibility.Collider;
import com.anpo.tank.bean.GameObject;
import com.anpo.tank.enums.Direction;

import java.awt.*;

public class TailDecorator extends GameObjectDecorator{

    public TailDecorator(GameObject gameObject) {
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
        graphics.setColor(Color.GREEN);
        graphics.drawLine(x,y,x+width,y+height);

        graphics.setColor(color);

    }
}
