package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Platformer;
import javafx.scene.text.Font;

/**
 * Created by Tony Howarth on 6/23/2016.
 */
public class HUD implements Disposable{

    private Stage mStage;
    private Viewport mView;

    private Integer mWorldTimer = 500;
    private float mTimeCount;
    private Integer mScore;


    private BitmapFont mFont;


    private Label mCountDownLabel;
    private Label mScoreLabel;
    private Label mTimeLabel;
    private Label mLevelLable;
    private Label mWorldLabel;
    private Label mPlayerLabel;

    private int mFPS = 10;

    public HUD(SpriteBatch sb){
        mWorldTimer = 300;
        mTimeCount = 0;
        mScore = 0;

        mFont = new BitmapFont();


        mView = new StretchViewport(Platformer.V_WIDTH, Platformer.V_HEIGHT, new OrthographicCamera());
        mStage = new Stage(mView, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);// makes table the size of the stage

        mCountDownLabel = new Label(String.format("%03d", mWorldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mScoreLabel = new Label(String.format("%06d", mScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mTimeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        mWorldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mPlayerLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(mPlayerLabel).expandX().padTop(10);
        table.add(mWorldLabel).expandX().padTop(10);
        table.add(mTimeLabel).expandX().padTop(10);
        table.row();
        table.add(mScoreLabel).expandX();

        table.add(mCountDownLabel).expandX();
        mStage.addActor(table);
    }

    public void update(float deltaTime){
        mTimeCount += deltaTime;
        mFPS = Gdx.graphics.getFramesPerSecond();
        if(mTimeCount >= 1) {
            mWorldTimer -= 1;
        }
        //mLevelLable = new Label("FPS "  + mFPS, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    public void render(float deltatime,Batch sb, OrthographicCamera cam){
        update(deltatime);


    }


    public Stage getmStage(){
        return this.mStage;
    }

    @Override
    public void dispose() {
        mStage.dispose();
    }
}
