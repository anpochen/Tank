package com.anpo.designPatterns.c03_factory.superBean;

import com.anpo.designPatterns.c03_factory.bean.Explode;
import com.anpo.resource.ResourceManager;
import com.anpo.tank.baseBean.Audio;
import com.anpo.tank.baseBean.TankFrame;

import java.awt.*;

public class SuperExplode extends Explode {

    public static int WIDTH = ResourceManager.explodes[0].getWidth();
    public static int HEIGHT = ResourceManager.explodes[0].getHeight();

    private int x, y;

    TankFrame tankFrame = null;

    private int step = 0;

    public SuperExplode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    @Override
    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawOval(x,y,WIDTH,HEIGHT);

        g.setColor(color);

        if(step >= ResourceManager.explodes.length){
            tankFrame.explodes.remove(this);
            step =0;
        }
    }
}
