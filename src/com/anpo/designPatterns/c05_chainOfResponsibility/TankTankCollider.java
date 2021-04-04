package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.GameObject;
import com.anpo.tank.bean.Tank;

public class TankTankCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2, GameModel gameModel) {
        if (o1 instanceof Tank && o2 instanceof Tank){
            Tank tank1 = (Tank)o1;
            Tank tank2 = (Tank)o2;
            collidedWith(tank1,tank2);
        }
        return false;
    }

    //碰撞检测
    public boolean collidedWith(Tank tank1, Tank tank2) {
        if(tank1.getRectangle().intersects(tank2.getRectangle())){
            tank1.reverseDirection();
            tank2.reverseDirection();
        }
        return false;
    }
}
