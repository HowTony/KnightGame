package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameContactListener;
import com.mygdx.game.InputHandler;
import com.mygdx.game.MapBodyMaker;
import com.mygdx.game.Platformer;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Sprites.Assets;
import com.mygdx.game.Sprites.Player;

/**
 * Created by Tony Howarth on 6/23/2016.
 */
public class PlayScreen implements Screen {
    private Game mGame;
    private SpriteBatch mBatch;
    private OrthographicCamera mGameCam;
    private Viewport mGamePort;
    private HUD mHud;
    private TmxMapLoader mMapLoader;
    private TiledMap mMap;
    private OrthogonalTiledMapRenderer mRenderer;
    private MapBodyMaker mMapMaker;
    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;
    private Player mPlayer;
    private GameContactListener mContactListener;
    private InputHandler mInputs;


    private Vector3 mMousePos = new Vector3();

    public PlayScreen(Platformer game, SpriteBatch batch){
        this.mGame = game;
        mBatch = batch;
        mGameCam = new OrthographicCamera();
        mGamePort = new StretchViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, mGameCam);
        mHud = new HUD(mBatch);
        mMapLoader = new TmxMapLoader();
        mMap = mMapLoader.load("test3.tmx");
        mRenderer = new OrthogonalTiledMapRenderer(mMap, 1/ Platformer.PPM);
        mGameCam.position.set(mGamePort.getWorldWidth() / 2, mGamePort.getWorldHeight() / 2, 0);

        mWorld = new World(new Vector2( 0, -9f), true);
        mPlayer = new Player(mWorld, mMousePos);
        mInputs = new InputHandler(mPlayer);
        mContactListener = new GameContactListener(mInputs);
        mWorld.setContactListener(mContactListener);
        mDebugRenderer = new Box2DDebugRenderer();
        mMapMaker = new MapBodyMaker();
        mMapMaker.buildShapes(mMap, Platformer.PPM, mWorld, "ground", "ground");
        mMapMaker.buildShapes(mMap, Platformer.PPM, mWorld, "slopes", "slopes");

    }

    public void update(float deltaTime){
        mWorld.step(1/60f, 6, 1);

        mGameCam.position.x = mPlayer.getBody().getPosition().x;
        //mGameCam.position.y = mPlayer.getmBody().getPosition().y;
        mPlayer.update(deltaTime);

        if(mPlayer.getBody().getPosition().y > 8){
            mGameCam.position.y = mPlayer.getBody().getPosition().y;
        }else if(mPlayer.getBody().getPosition().y < 8){
            //TODO
            //fix camera snap speed
            mGameCam.position.set(mGameCam.position.x, mGamePort.getWorldHeight() / 2, 0);
        }
        mGameCam.update();
        mMousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        mGameCam.unproject(mMousePos);
        mRenderer.setView(mGameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(.3f,.3f,.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(deltaTime);

        mRenderer.render();

        //mHud.getmStage().draw();
        mBatch.setProjectionMatrix(mGameCam.combined);
        mBatch.begin();
        mPlayer.draw(mBatch);
        mInputs.update(deltaTime, mBatch);
        mBatch.end();
//        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        mGamePort.update(width, height);
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
        mMap.dispose();
        mRenderer.dispose();
        mDebugRenderer.dispose();
        mHud.dispose();
        mBatch.dispose();
        mWorld.dispose();
    }
}
