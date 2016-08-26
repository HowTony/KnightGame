package com.mygdx.game.GameTools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tony Howarth on 8/4/2016.
 */
public class BodyRemover {
    private World mWorld;

    public BodyRemover(World world){
        mWorld = world;
    }

    public void update(float deltaTime){
        removeBodies();
    }

    public void removeBodies(){
        Array<Body> bodyRemover = new Array<Body>();
        mWorld.getBodies(bodyRemover);
        for(int i = 0; i < bodyRemover.size; i++){
            if(!mWorld.isLocked() && !bodyRemover.get(i).isActive()){
                mWorld.destroyBody(bodyRemover.get(i));
            }
        }
    }
}
