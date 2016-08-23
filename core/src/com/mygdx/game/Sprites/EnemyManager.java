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
        mEnemies.add(new Enemies(mWorld, mPlayer, new Vector2(3, 5), false, ID));
        for(int i = 0; i < 5; i++) {
            mEnemies.add(new Enemies(mWorld, mPlayer, new Vector2(20 + (i * 10), 6), false, ID+i));
        }
    }

    public void update(float deltaTime){
        for(Enemies eachEnemy : mEnemies){
            eachEnemy.update(deltaTime);
        }
    }

    public void render(Batch sb){
        for(Enemies eachEnemy : mEnemies){
            eachEnemy.render(sb);
        }
    }

    public Enemies getEnemy(String name) {
        Enemies someEnemy = mEnemies.get(0);
        for (int i = 0; i < mEnemies.size; i++) {
            if (mEnemies.get(i).getBody().getFixtureList().get(0).toString().contains(name)) {
                someEnemy = mEnemies.get(i);
            }
        }
        return someEnemy;
    }

}
