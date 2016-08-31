package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GameTools.Platformer;

/**
 * Created by Tony Howarth on 8/25/2016.
 */
public class MenuScreen implements Screen {
    private Platformer mGame;
    private SpriteBatch mBatch;
    private Texture mStartImage;

    private Cursor mMouse;
    private Pixmap mPix;
    private BitmapFont mFontBasic;

    private Stage mStage;
    private TextureAtlas mAtlas;
    private Skin mSkin;
    private Table mTable;



    public MenuScreen(Game game, SpriteBatch batch){

    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
