package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.Player;

/**
 * Created by Tony Howarth on 7/19/2016.
 */
public class GameContactListener implements ContactListener{

    private InputHandler mInputs;

    public GameContactListener(InputHandler input){
        mInputs = input;
    }


    // called when two fixtures start to collide
    @Override
    public void beginContact(Contact contact) {
        Fixture fb = contact.getFixtureB();
        if(fb.getUserData().equals("foot")){
            mInputs.setPlayerOnGround(true);
        }

    }

    // called when two fixtures no longer collide
    @Override
    public void endContact(Contact contact) {
        Fixture fb = contact.getFixtureB();
        if(fb.getUserData() != null && fb.getUserData().equals("foot")){
            mInputs.setPlayerOnGround(false);
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
