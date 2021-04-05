package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.Bullet;
import com.anpo.tank.bean.Explode;
import com.anpo.tank.bean.GameObject;
import com.anpo.tank.bean.Tank;

public class BulletTankCollider implements Collider{
    /*
    继续碰撞返回false  中断循环碰撞返回 true
     */
    @Override
    public boolean collide(GameObject o1, GameObject o2, GameModel gameModel) {
        if (o1 instanceof Bullet && o2 instanceof Tank){
            Bullet bullet = (Bullet)o1;
            Tank tank = (Tank)o2;
            return collidedWith(bullet,tank,gameModel);
        }else if (o1 instanceof Tank && o2 instanceof Bullet){
            return collide(o2,o1,gameModel);
        }else {
            return false;
        }
    }

    //碰撞检测   撞死了返回 true  否则返回 false
    public boolean collidedWith(Bullet bullet, Tank tank, GameModel gameModel) {
        //如果都是同一组的，那么直接返回
        if(bullet.getGroup().equals(tank.getGroup())){
            return false;
        }

        if(bullet.getRectangle().intersects(tank.getRectangle())){
            tank.die();
            bullet.die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            new Explode(eX, eY);
            return true;
        }
        return false;
    }
}
