package com.mygdx.game.GameTools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MoveableObjects.Enemies;
import com.mygdx.game.MoveableObjects.Managers.EnemyManager;
import com.mygdx.game.MoveableObjects.GunShot;
import com.mygdx.game.MoveableObjects.Managers.ShotManager;

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

        //if enemy gets hit by gun, add +1 to hits taken on enemy counter
        if (fa.getUserData().toString().contains("shot") || fb.getUserData().toString().contains("shot")) {
            //if the shot collides with a sensor then kill the shot
            if (!fa.isSensor() && !fb.isSensor()) {
                mShot = mShotManager.getShot(fa.getBody().getFixtureList().get(0).toString());
                mShot.setIsDead(true);
                mShot = mShotManager.getShot(fb.getBody().getFixtureList().get(0).toString());
                mShot.setIsDead(true);
            }


            if (fa.getUserData().toString().contains("enemy")) {
                mEnemy = mEnemies.getEnemy(fa.getBody().getFixtureList().get(0).getUserData().toString());
                //System.out.println("fa shot " + mEnemy.getBody().getFixtureList().get(0).getUserData().toString());
                mEnemy.increaseHitsTaken();
                mEnemy.setAttacking(true);
            } else if (fb.getUserData().toString().contains("enemy")) {
                mEnemy = mEnemies.getEnemy(fb.getBody().getFixtureList().get(0).getUserData().toString());
                //System.out.println("fb shot " + mEnemy.getBody().getFixtureList().get(0).getUserData().toString());
                mEnemy.increaseHitsTaken();
                mEnemy.setAttacking(true);
            }
        }

        if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
            if (fa.getUserData().toString().contains("aggro") || fb.getUserData().toString().contains("aggro")) {
                //if player and enemy aggro sensor collide then set the enemy to attacking, enemy will chase player
                mEnemy = mEnemies.getEnemy(fa.getBody().getFixtureList().get(0).getUserData().toString());
                mEnemy.setAttacking(true);
            }else if(fa.getUserData().toString().contains("enemy") || fb.getUserData().toString().contains("enemy")){
                //player is hit by enemy
                mInputs.getPlayer().takeDamage();
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
