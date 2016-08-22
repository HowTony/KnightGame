package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.Enemies;
import com.mygdx.game.Sprites.EnemyManager;
import com.mygdx.game.Sprites.GunShot;
import com.mygdx.game.Sprites.ShotManager;

/**
 * Created by Tony Howarth on 7/19/2016.
 */
public class GameContactListener implements ContactListener {

    private InputHandler mInputs;
    private EnemyManager mEnemies;
    private Enemies mEnemy;
    private boolean mOnSlope;
    private ShotManager mShotManager;
    private GunShot mShot;

    public GameContactListener(InputHandler input, EnemyManager enemies, ShotManager shotman) {
        mInputs = input;
        mEnemies = enemies;
        mShotManager = shotman;
    }

    // called when two fixtures start to collide
    @Override
    public void beginContact(Contact contact) {
        Fixture fb = contact.getFixtureA();
        Fixture fa = contact.getFixtureB();


        //if player and enemy aggro sensor collide then set the enemy to attacking, enemy will chase player
        if (fa.getUserData().toString().contains("aggro") || fb.getUserData().toString().contains("aggro")) {
            if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
                mEnemy = mEnemies.getEnemy(fa.getBody().getFixtureList().get(0).toString());
                mEnemy.setAttacking(true);
            }
        }


        //if the shot collides with a sensor then kill the shot
        if (fa.getUserData().toString().contains("shot") || fb.getUserData().toString().contains("shot")) {
            if (!fa.isSensor() && !fb.isSensor()) {
                mShot = mShotManager.getShot(fa.getBody().getFixtureList().get(0).toString());
                mShot.setIsDead(true);
                mShot = mShotManager.getShot(fb.getBody().getFixtureList().get(0).toString());
                mShot.setIsDead(true);
            }
        }

        /*
        check to see if foot sensors are colliding with ground
        if colliding with a slope we reduce the friction in the preSolve,
        then adjust it back in the postSolve. This makes slow traversal much
        more realistic.
         */
        if (fa.getUserData().equals("foot1") || fb.getUserData().equals("foot1")) {
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
        if (mOnSlope) {
            contact.setFriction(.05f);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if (!mOnSlope) {
            contact.setFriction(.8f);
        }
    }
}
