package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.GameObject;

public interface Collider {

    boolean collide(GameObject o1, GameObject o2, GameModel gameModel);

}
