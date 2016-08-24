package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tony Howarth on 8/5/2016.
 */
public class EnemyManager {

    private Array<Enemies> mEnemies;
    private World mWorld;
    private Player mPlayer;
    private Vector2 mSpawnLocation;
    private int ID = 0;

    public EnemyManager(World world, Player player){
        mWorld = world;
        mPlayer = player;
        mEnemies = new Array<Enemies>();
        addEnemies();
    }

    private void addEnemies() {
        for(int i = 0; i < 10; i++) {
            mEnemies.add(new Enemies(mWorld, mPlayer, new Vector2(5 + (i * 10), 6), false, ID+i));
        }
    }

    public void update(float deltaTime){
        for(Enemies eachEnemy : mEnemies){
            eachEnemy.update(deltaTime);
        }
        Array<Enemies>removableShots = new Array<Enemies>();
        for(Enemies eachEnemy : mEnemies){
            eachEnemy.update(deltaTime);
            if(eachEnemy.isRemovable()){
                eachEnemy.getBody().setActive(false);
                removableShots.add(eachEnemy);
            }
        }
            removeOldShots(removableShots);


    }

    public void render(Batch sb){
        for(Enemies eachEnemy : mEnemies){
            eachEnemy.render(sb);
        }
    }

    public Enemies getEnemy(String name) {
        Enemies someEnemy = mEnemies.first();
        for (int i = 0; i < mEnemies.size; i++) {
            if (mEnemies.get(i).getBody().getFixtureList().get(0).getUserData().toString().equals(name)) {
                someEnemy = mEnemies.get(i);
                return someEnemy;
            }
        }
        return someEnemy;
    }


    public void removeOldShots(Array<Enemies> itemsToRemove){
        mEnemies.removeAll(itemsToRemove, true);
    }


}
