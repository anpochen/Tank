package com.anpo.tank.bean;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;

import java.awt.*;

public class Wall extends GameObject{

    Rectangle rectangle = new Rectangle();

    public Wall(int x,int y,int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        rectangle.setRect(x,y,width,height);

        GameModel.getINSTANCE().add(this);
    }

    @Override
    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x,y,width,height);
        g.setColor(color);

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
