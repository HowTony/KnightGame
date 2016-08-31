package com.mygdx.game.GameTools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StartScreen;

/**
 * Created by Tony Howarth on 6/22/2016.
 */
public class Platformer extends Game {

    public static final int V_WIDTH = 1200;
    public static final int V_HEIGHT = 1200;
    public static final float PPM = 100;

    private SpriteBatch mBatch;

    @Override
    public void create(){
        mBatch = new SpriteBatch();
        setScreen(new StartScreen(this, mBatch));
    }

    @Override
    public void render(){
        super.render();
    }

    public static float getPpm(){
        return PPM;
    }
}
