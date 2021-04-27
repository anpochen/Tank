package com.anpo.tank.bean;

import com.anpo.resource.ResourceManager;

import java.awt.*;

public class Explode {
    public static final int WIDTH = ResourceManager.explodes[0].getWidth();
    public static final int HEIGHT = ResourceManager.explodes[0].getHeight();

    private int x,y;
    private TankFrame tankFrame;

    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(()->{
            new Audio("com/anpo/resource/audio/explode.wav").play();
        });

    }

    public void paint(Graphics g){
        g.drawImage(ResourceManager.explodes[step++],x,y,null);
        if(step >= ResourceManager.explodes.length){
            tankFrame.explodes.remove(this);
            step =0;
        }
    }





}
