package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tony Howarth on 8/9/2016.
 */
public class EnemyController {

    private boolean mIsGroundType;
    private Enemies mEnemy;
    private World mWorld;
    private Player mPlayer;
    private boolean mIsPlayerOnRightSide;

    private float mCurrentTime = 0;
    private float mEnemyScountRange = 4;


    public EnemyController(World world, Enemies enemies, Player player, boolean groundType) {
        mWorld = world;
        mEnemy = enemies;
        mPlayer = player;
        mIsGroundType = groundType;
    }


    public void update(float deltaTime) {
        mCurrentTime += deltaTime;
        if(mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x > 0) {
            mIsPlayerOnRightSide = true;
        }else{
            mIsPlayerOnRightSide = false;
        }


        if (!mEnemy.isAttacking()) {

            if (mEnemy.getBody().getLinearVelocity().x > -2f && mEnemy.getBody().getPosition().x > mEnemy.getSpawnLocation().x - mEnemyScountRange) {
                mEnemy.getBody().applyLinearImpulse(new Vector2(-.18f, 0), mEnemy.getBody().getWorldCenter(), true);


            } else if (mEnemy.getBody().getLinearVelocity().x < 2f && mEnemy.getBody().getPosition().x < mEnemy.getSpawnLocation().x + mEnemyScountRange) {
                mEnemy.getBody().applyLinearImpulse(new Vector2(.18f, 0), mEnemy.getBody().getWorldCenter(), true);

            }

        } else if(mEnemy.isAttacking()) {
            if (mEnemy.getBody().getPosition().y - mPlayer.getBody().getPosition().y > 3) {
              moveDown();
            } else if ( mEnemy.getBody().getPosition().y - mPlayer.getBody().getPosition().y < 1) {
               moveUp();
            } if (mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x > 2) {
                moveLeft();
            } else if ( mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x < -2 ) {
                moveRight();
            }

            System.out.println(mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x);


        }
    }


    public void moveRight(){
        if (mEnemy.getBody().getLinearVelocity().x < 4f) {
            mEnemy.getBody().applyLinearImpulse(new Vector2( .8f, 0), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveLeft(){
        if (mEnemy.getBody().getLinearVelocity().x > -4f) {
            mEnemy.getBody().applyLinearImpulse(new Vector2( -.8f, 0), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveUp(){
        if (mEnemy.getBody().getLinearVelocity().y < 2) {
            mEnemy.getBody().applyLinearImpulse(new Vector2( 0, .5f), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveDown(){
        if (mEnemy.getBody().getLinearVelocity().y > -2) {
            mEnemy.getBody().applyLinearImpulse(new Vector2( 0,-.5f), mEnemy.getBody().getWorldCenter(), true);
        }
    }



}



