package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.*;

public class TankWallCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2, GameModel gameModel) {

        if (o1 instanceof Tank && o2 instanceof Wall){
            Tank tank = (Tank)o1;
            Wall wall = (Wall)o2;
            collidedWith(tank,wall,gameModel);
        }else if (o1 instanceof Wall && o2 instanceof Tank){
            return collide(o2,o1,gameModel);
        }
        return false;
    }

    //碰撞检测   撞了返回 true  否则返回 false
    public boolean collidedWith(Tank tank, Wall wall, GameModel gameModel) {
        if(tank.getRectangle().intersects(wall.getRectangle())){
            tank.reverseDirection();
            return true;
        }
        return false;
    }
}
