package com.mygdx.game.Sprites;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tony Howarth on 8/3/2016.
 */
public class ShotManager {

    private Array<GunShot> mBullets;
    private Player mPlayer;


    public ShotManager(Player player){
        mBullets = new Array<GunShot>();
        mPlayer = player;
    }

    public void addBullets(){
        mBullets.add(new GunShot(mPlayer));
    }

    public void update(float deltaTime){
        Array<GunShot> removableShots = new Array<GunShot>();
        for(GunShot eachShot : mBullets){
            eachShot.update(deltaTime);
            if(eachShot.isDead()){
                eachShot.getBody().setActive(false);
                removableShots.add(eachShot);
            }
        }
        removeOldShots(removableShots);
    }

    public void removeOldShots(Array<GunShot> itemsToRemove){
            mBullets.removeAll(itemsToRemove, true);
    }

    public void render(Batch sb){
        for(GunShot eachShot : mBullets){
            eachShot.render(sb);
        }
    }

    public GunShot getShot(String name) {
        GunShot someShot = mBullets.get(0);
        for (int i = 0; i < mBullets.size; i++) {
            if (mBullets.get(i).getBody().getFixtureList().get(0).toString().contains(name)) {
                someShot = mBullets.get(i);
            }
        }
        return someShot;
    }
}
