package com.mygdx.game.MoveableObjects.Controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoveableObjects.Enemies;
import com.mygdx.game.MoveableObjects.Player;

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
    private float mEnemyScountRange = 5;


    public EnemyController(World world, Enemies enemies, Player player, boolean groundType) {
        mWorld = world;
        mEnemy = enemies;
        mPlayer = player;
        mIsGroundType = groundType;
    }


    public void update(float deltaTime) {
        mCurrentTime += deltaTime;
        if (mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x > 0) {
            mIsPlayerOnRightSide = true;
        } else {
            mIsPlayerOnRightSide = false;
        }


        if (!mEnemy.isAttacking() && !mEnemy.isDead()) {
            if (mCurrentTime <= 6) {
                moveLeft(1.5f);
            } else if (mCurrentTime > 6) {
                moveRight(1.5f);
                if(mCurrentTime >= 12){
                    mCurrentTime = 0;
                }
            }

            if(mEnemy.getBody().getPosition().y > mEnemy.getSpawnLocation().y){
                moveDown(.05f);
            }

        } else if (mEnemy.isAttacking() && !mEnemy.isDead()) {

            if (mEnemy.getBody().getPosition().y - mPlayer.getBody().getPosition().y > 3) {
                moveDown(2);
            } else if (mEnemy.getBody().getPosition().y - mPlayer.getBody().getPosition().y < 1) {
                moveUp(2);
            }
            if (mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x > 2) {
                moveLeft(4);
            } else if (mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x < -2) {
                moveRight(4);
            }
            //System.out.println(mEnemy.getBody().getPosition().x - mPlayer.getBody().getPosition().x);
        }
    }

    public void moveRight(float maxSpeed) {
        if (mEnemy.getBody().getLinearVelocity().x < maxSpeed) {
            mEnemy.getBody().applyLinearImpulse(new Vector2(.8f, 0), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveLeft(float maxSpeed) {
        if (mEnemy.getBody().getLinearVelocity().x > -maxSpeed) {
            mEnemy.getBody().applyLinearImpulse(new Vector2(-.8f, 0), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveUp(float maxSpeed) {
        if (mEnemy.getBody().getLinearVelocity().y < maxSpeed) {
            mEnemy.getBody().applyLinearImpulse(new Vector2(0, .5f), mEnemy.getBody().getWorldCenter(), true);
        }
    }

    public void moveDown(float maxSpeed) {
        if (mEnemy.getBody().getLinearVelocity().y > -maxSpeed) {
            mEnemy.getBody().applyLinearImpulse(new Vector2(0, -.5f), mEnemy.getBody().getWorldCenter(), true);
        }
    }


}



