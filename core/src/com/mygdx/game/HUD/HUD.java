package com.mygdx.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameTools.Platformer;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.GameTools.Assets;
import com.mygdx.game.MoveableObjects.Player;

/**
 * Created by Tony Howarth on 6/23/2016.
 */
public class HUD implements Disposable{

    private Stage mStage;
    private Viewport mView;

    private Integer mWorldTimer = 500;
    private float mTimeCount;
    private Integer mScore;
    private Player mPlayer;
    private Assets mAssets;
    private TextureRegion mAlienHead;
    private TextureRegion mMouseCursor;
    private TextureRegion mHealthBar;
    private TextureRegion mHealth;
    private TextureRegion mPower;
    private Pixmap mPix;
    private Cursor mMouse;


    private BitmapFont mFontBasic;
    private BitmapFont mFontDeathMsg;


//    private Label mCountDownLabel;
//    private Label mScoreLabel;
//    private Label mTimeLabel;
//    private Label mLevelLable;
//    private Label mWorldLabel;
//    private Label mPlayerLabel;

    private int mFPS = 10;

    public HUD(SpriteBatch sb, Player player){
        mPlayer = player;
        mAssets = mPlayer.getGameAssets();
        mAlienHead = mAssets.getAlienHead();
        mWorldTimer = 300;
        mTimeCount = 0;
        mScore = 0;
        mMouseCursor = mAssets.getMousecursor();
        mHealthBar = mAssets.getHealthBar();
        mHealth = mAssets.getHealthBubble();
        mPower = mAssets.getPowerBubble();
        mPix = new Pixmap(Gdx.files.internal("MouseCursor.png"));
        mMouse = Gdx.graphics.newCursor(mPix, 20, 20);

        mFontBasic = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        mFontDeathMsg = new BitmapFont(Gdx.files.internal("perished.fnt"));
        mFontBasic.setColor(Color.WHITE);
        //how to scale the font
//        mFont.getData().scale(.7f);


        mView = new StretchViewport(Platformer.V_WIDTH, Platformer.V_HEIGHT, new OrthographicCamera());
        mStage = new Stage(mView, sb);
//
//        Table table = new Table();
//        table.top();
//        table.setFillParent(true);// makes table the size of the stage

//        mCountDownLabel = new Label(String.format("%03d", mWorldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        mScoreLabel = new Label(String.format("%06d", mScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        mTimeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//
//        mWorldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        mPlayerLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        table.add(mPlayerLabel).expandX().padTop(10);
//        table.add(mWorldLabel).expandX().padTop(10);
//        table.add(mTimeLabel).expandX().padTop(10);
//        table.row();
//        table.add(mScoreLabel).expandX();
//
//        table.add(mCountDownLabel).expandX();
//        mStage.addActor(table);
    }

    public void update(float deltaTime){
        mTimeCount += deltaTime;
        //mFPS = Gdx.graphics.getFramesPerSecond();
        if(mTimeCount >= 1) {
            mWorldTimer -= 1;
        }

    }

    public void render(float deltatime,Batch batch, OrthographicCamera cam){
        mStage.draw();
        update(deltatime);
        batch.begin();
        Gdx.graphics.setCursor(mMouse);

        batch.draw(mHealthBar,Platformer.V_WIDTH / 20, Platformer.V_HEIGHT / 30);
        //mFontBasic.draw(batch,"FPS: " + mFPS , 50,1150);
        for(int i = 0; i < mPlayer.getHitsTilDeath(); i++){
            batch.draw(mHealth,Platformer.V_WIDTH/8 +(i*12.3f), (Platformer.V_HEIGHT / 30) + 60 );
        }
        batch.draw(mAlienHead, Platformer.V_WIDTH/28, Platformer.V_HEIGHT/ 30);

        if(mPlayer.isDead()){
            mFontDeathMsg.draw(batch, "YOU DIED", Platformer.V_WIDTH / 3, Platformer.V_HEIGHT / 2);
        }
        if(PlayScreen.mIsPaused){
            mFontDeathMsg.draw(batch, "PAUSED", Platformer.V_WIDTH / 3, Platformer.V_HEIGHT / 2);
        }
        batch.end();

    }


    public Stage getmStage(){
        return this.mStage;
    }

    @Override
    public void dispose() {
        mStage.dispose();
    }
}
