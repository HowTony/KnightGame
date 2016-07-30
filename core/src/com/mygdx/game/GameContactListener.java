package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tony Howarth on 7/19/2016.
 */
public class GameContactListener implements ContactListener{

    private InputHandler mInputs;
    private boolean mOnSlope;

    public GameContactListener(InputHandler input){
        mInputs = input;
    }

    // called when two fixtures start to collide
    @Override
    public void beginContact(Contact contact) {
        Fixture fb = contact.getFixtureA();
        Fixture fa = contact.getFixtureB();




        if(fa.getUserData().equals("foot1") || fb.getUserData().equals("foot1")) {
            if (fa.getUserData().equals("ground") || fb.getUserData().equals("ground")) {
                mInputs.getPlayer().setFoot1OnGround(true);
                mOnSlope = false;
            }
            if (fa.getUserData().equals("slopes") || fb.getUserData().equals("slopes")) {
                mInputs.getPlayer().setFoot1OnGround(true);
                mOnSlope = true;
            }
        }
        if (fa.getUserData().equals("foot2") || fb.getUserData().equals("foot2")) {
            if (fa.getUserData().equals("ground") || fb.getUserData().equals("ground")) {
                mInputs.getPlayer().setFoot2OnGround(true);
                mOnSlope = false;
            }
            if (fa.getUserData().equals("slopes") || fb.getUserData().equals("slopes")) {
                mInputs.getPlayer().setFoot1OnGround(true);
                mOnSlope = true;
            }
        }
    }

    // called when two fixtures no longer collide
    @Override
    public void endContact(Contact contact) {
//        Fixture fa = contact.getFixtureA();
//        Fixture fb = contact.getFixtureB();
//        if(fa.getUserData().equals("foot1") || fb.getUserData().equals("foot1")) {
//            if (fa.getUserData().equals("ground") || fb.getUserData().equals("ground")) {
//            }
//            if (fa.getUserData().equals("slopes") || fb.getUserData().equals("slopes")) {
//            }
//        }
//        if (fa.getUserData().equals("foot2") || fb.getUserData().equals("foot2")) {
//            if (fa.getUserData().equals("ground") || fb.getUserData().equals("ground")) {
//            }
//            if (fa.getUserData().equals("slopes") || fb.getUserData().equals("slopes")) {
//            }
//        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if(mOnSlope) {
            contact.setFriction(.05f);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if(!mOnSlope) {
            contact.setFriction(.8f);
        }
    }
}
