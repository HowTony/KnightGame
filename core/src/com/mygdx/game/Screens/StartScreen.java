package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameTools.Platformer;

/**
 * Created by Tony Howarth on 8/25/2016.
 */
public class StartScreen implements Screen{
    private Platformer mGame;
    private SpriteBatch mBatch;
    private Texture mStartImage;

    private Cursor mMouse;
    private Pixmap mPix;
    private BitmapFont mFontBasic;


    public StartScreen(Platformer game, SpriteBatch sb){
        mGame = game;
        mBatch = sb;
        mStartImage = new Texture(Gdx.files.internal("Poster1.jpg"));
        mPix = new Pixmap(Gdx.files.internal("MouseCursor.png"));
        mFontBasic = new BitmapFont(Gdx.files.internal("perished.fnt"));
        mMouse = Gdx.graphics.newCursor(mPix, 20, 20);
    }

    @Override
    public void show() {

    }

    public void update(float deltaTime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            mGame.setScreen(new PlayScreen(mGame, mBatch));
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        mBatch.begin();
        Gdx.graphics.setCursor(mMouse);
        mBatch.draw(mStartImage, 0,0, 1400, 900);
        mFontBasic.draw(mBatch, "Any key to start", Platformer.V_WIDTH / 3, Platformer.V_HEIGHT / 3);
        mBatch.end();

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
