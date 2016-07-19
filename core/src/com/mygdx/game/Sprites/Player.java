package com.mygdx.game.Sprites;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tony Howarth on 6/27/2016.
 */
public class Player extends Sprite {

    private World mWorld;
    private Body mBody;

    private TextureRegion mCurrentPlayerFrame;

    private Assets mGameAssets = new Assets();
    private float mGameTime = 0;

    private enum mState {FALLING, JUMPING, STANDING, RUNNING, WALKING, DEATH, CROUCH};
    private float mStateTimer;

    private float mPlayerWidth = 1;
    private float mPlayerHeight = 1;
    private float mSpriteXCenter = .48f;
    private float mSpriteYCenter = .48f;

    public Player(World world){

//        mCurrentState = mState.STANDING;
//        mPreviousState = mState.STANDING;
        mStateTimer = 0;
        this.mWorld = world;
        definePlayer();
        mGameAssets.load();
    }

    public void definePlayer(){
        // create player rect
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(5,5);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mBody = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.15f,.33f);
        fixtureDef.shape = shape;
        mBody.createFixture(fixtureDef).setUserData("player");

        //create foot sensor
        shape.setAsBox(.16f, .02f, new Vector2(0f, -.36f), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        mBody.createFixture(fixtureDef).setUserData("foot");

        shape.dispose();
    }

    public void update(float deltaTime){
        AnimateSprite(deltaTime);
        setPosition(mBody.getPosition().x - getWidth() / 2, .1f + mBody.getPosition().y - getHeight() / 2);
    }

    public void AnimateSprite(float deltaTime){
        mGameTime += deltaTime;
        mCurrentPlayerFrame = mGameAssets.getAlienRunningAnimation().getKeyFrame(mGameTime, 0);
    }

    public Body getmBody(){
        return this.mBody;
    }

    public void draw(SpriteBatch batch){
        batch.draw(mCurrentPlayerFrame, getX() - mSpriteXCenter, getY() - mSpriteYCenter, mPlayerWidth , mPlayerHeight);
    }

    public void reverseSpriteDirection(boolean b){
        if(b){
            mPlayerWidth = -1;
            mSpriteXCenter = -.48f;
        }else{
            mPlayerWidth = 1;
            mSpriteXCenter = .48f;
        }
    }
}
