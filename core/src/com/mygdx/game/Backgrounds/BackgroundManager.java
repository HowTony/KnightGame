package com.mygdx.game.Backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameTools.Platformer;
import com.mygdx.game.MoveableObjects.Player;
import com.mygdx.game.Screens.PlayScreen;


/**
 * Created by Tony Howarth on 8/26/2016.
 */
public class BackgroundManager {
    private Texture mTrees;
    private Texture mSky;
    private Texture mMountains;
    private Texture mGrass;

    private Player mPlayer;
    private ParallaxLayer[] mLayers;
    private ParallaxBackground mBackground;

    public BackgroundManager(Player player){
        mPlayer = player;
        mTrees = new Texture(Gdx.files.internal("Trees.png"));
        mMountains = new Texture(Gdx.files.internal("mountainBG.png"));
        mSky = new Texture(Gdx.files.internal("sky.png"));
        mGrass = new Texture(Gdx.files.internal("grass.png"));

        mLayers = new ParallaxLayer[4];
        mLayers[0] = new ParallaxLayer(mSky, 1);
        mLayers[1] = new ParallaxLayer(mGrass, 2);
        mLayers[2] = new ParallaxLayer(mMountains, 3);
        mLayers[3] = new ParallaxLayer(mTrees,20);
        mBackground = new ParallaxBackground(mLayers, Platformer.V_WIDTH, Platformer.V_HEIGHT - 200, mPlayer);
    }

    public void render(float deltaTime, SpriteBatch mBatch){
        mBackground.render(mBatch);
    }

    public void update(float deltaTime){
        if(!PlayScreen.mIsPaused){
        mBackground.update(deltaTime);
        }
    }
}
