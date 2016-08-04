package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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
        for(GunShot eachShot : mBullets){
            eachShot.update(deltaTime);
        }
    }






}
