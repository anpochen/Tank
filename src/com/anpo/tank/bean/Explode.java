package com.anpo.tank.bean;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.resource.Audio;
import com.anpo.resource.ResourceManager;

import java.awt.*;

public class Explode extends GameObject{
    public static final int WIDTH = ResourceManager.explodes[0].getWidth();
    public static final int HEIGHT = ResourceManager.explodes[0].getHeight();

    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

        new Thread(()->new Audio("com/anpo/resource/audio/explode.wav").play()).start();

        GameModel.getINSTANCE().add(this);
    }

    public void paint(Graphics g){
        g.drawImage(ResourceManager.explodes[step++],x,y,null);
        if(step >= ResourceManager.explodes.length){
            GameModel.getINSTANCE().remove(this);
            step =0;
        }
    }





}
