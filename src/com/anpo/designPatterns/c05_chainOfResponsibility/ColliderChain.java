package com.anpo.designPatterns.c05_chainOfResponsibility;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider{

    List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        String[] colliders = PropertyManager.getString("colliders").split(";");
        for (String s:colliders) {
            try {
                add((Collider) Class.forName(s).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Collider collider){
        colliders.add(collider);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2, GameModel gameModel) {
        for (int i = 0; i < colliders.size(); i++) {
            if (colliders.get(i).collide(o1, o2, gameModel)){
                return true;
            }
        }
        return false;
    }
}
