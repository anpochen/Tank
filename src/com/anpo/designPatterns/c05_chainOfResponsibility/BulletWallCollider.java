package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.*;

public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2, GameModel gameModel) {
        if (o1 instanceof Bullet && o2 instanceof Wall){
            Bullet bullet = (Bullet)o1;
            Wall wall = (Wall)o2;
            collidedWith(bullet,wall,gameModel);
        }else if (o1 instanceof Wall && o2 instanceof Bullet){
            return collide(o2,o1,gameModel);
        }
        return false;
    }

    //碰撞检测   撞了返回 true  否则返回 false
    public boolean collidedWith(Bullet bullet, Wall wall, GameModel gameModel) {
        if(bullet.getRectangle().intersects(wall.getRectangle())){
            bullet.die();
            /*
            打开子弹撞墙会爆炸
             */
            /*int eX = bullet.getX() - Explode.WIDTH/2;
            int eY = bullet.getY() - Explode.HEIGHT/2;
            gameModel.gameObjects.add(new Explode(eX, eY, gameModel));*/
            return true;
        }
        return false;
    }
}
