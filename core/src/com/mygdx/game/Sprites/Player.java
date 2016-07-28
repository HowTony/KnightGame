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

    private Assets mGameAssets;
    private enum State {FALLING, JUMPING, STANDING, RUNNING, WALKING, DEATH, CROUCH, SHOOT, LANDING}
    private State mCurrentState;
    private State mPreviousState;
    private float mStateTimer;

    private float mPlayerWidth = 1;
    private float mPlayerHeight = 1;
    private float mSpriteXCenter = .48f;
    private float mSpriteYCenter = .48f;

    private Animation mJumpAnime;
    private Animation mFallingAnime;
    private Animation mLandingAnime;
    private Animation mRunAnime;
    private Animation mStandAnime;
    private Animation mDeathAnime;
    private Animation mCrouchAnime;
    private Animation mShootAnime;

    private boolean mPlayerOnGround;
    private boolean mFoot1OnGround = false;
    private boolean mFoot2OnGround = false;

    public Player(World world){
        this.mWorld = world;
        mGameAssets = new Assets();
        mCurrentState = State.STANDING;
        mPreviousState = State.STANDING;
        mStateTimer = 0;
        definePlayer();
        mGameAssets.load();
        mJumpAnime = mGameAssets.getAlienJumpAnimation();
        mFallingAnime = mGameAssets.getAlienFallingAnimation();
        mLandingAnime = mGameAssets.getAlienLandingAnimation();
        mRunAnime = mGameAssets.getAlienRunningAnimation();
        mStandAnime = mGameAssets.getAlienStandingAnimation();
        mDeathAnime = mGameAssets.getAlienDeathAnimation();
        mCrouchAnime = mGameAssets.getAlienCrouchAnimation();
        mShootAnime = mGameAssets.getAlienShootAnimation();
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

        //create foot sensor 1
        shape.setAsBox(.01f, .03f, new Vector2(-.15f, -.32f), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        mBody.createFixture(fixtureDef).setUserData("foot1");

        //create foot sensor 2
        shape.setAsBox(.01f, .03f, new Vector2(.15f, -.32f), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        mBody.createFixture(fixtureDef).setUserData("foot2");

        shape.dispose();
    }

    public void update(float deltaTime){

        if(mFoot1OnGround || mFoot2OnGround){
            setPlayerOnGround(true);
        }else{
            setPlayerOnGround(false);
        }
        if(mBody.getPosition().y < 0){
            mBody.setTransform(5,5,0);
        }
        AnimateSprite(deltaTime);
        setPosition(mBody.getPosition().x - getWidth() / 2, .1f + mBody.getPosition().y - getHeight() / 2);
    }

    public void AnimateSprite(float deltaTime){
        mCurrentPlayerFrame = getFrame(deltaTime);
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

    public TextureRegion getFrame(float deltaTime){
        mCurrentState = getState();
        TextureRegion region;
        switch(mCurrentState){
            case JUMPING:
                region = mJumpAnime.getKeyFrame(mStateTimer, 1);
                break;
            case FALLING:
                region = mFallingAnime.getKeyFrame(mStateTimer, 0);
                break;
            case LANDING:
                region = mLandingAnime.getKeyFrame(mStateTimer, 1);
                break;
            case RUNNING:
                region = mRunAnime.getKeyFrame(mStateTimer, 0);
                break;
            case STANDING:
                region = mStandAnime.getKeyFrame(mStateTimer, 0);
                break;
            default:
                region = mStandAnime.getKeyFrame(mStateTimer, 1);
                break;
        }
        mStateTimer = mCurrentState == mPreviousState ? mStateTimer + deltaTime : 0;
        mPreviousState = mCurrentState;
        return region;
    }

    public State getState(){
        if(mBody.getLinearVelocity().y > 0 && !mPlayerOnGround){
            return State.JUMPING;
        }else if(mBody.getLinearVelocity().y < 0 && !mPlayerOnGround){
            return State.FALLING;
        }else if(mBody.getLinearVelocity().y == 0 && mPlayerOnGround && mPreviousState == State.FALLING ){
            return State.LANDING;
        }else if(mBody.getLinearVelocity().x < 0 || mBody.getLinearVelocity().x > 0 && mPlayerOnGround){
            return State.RUNNING;
        }else{
            return State.STANDING;
        }
    }

    public void setPlayerOnGround(boolean b) {
        this.mPlayerOnGround = b;
    }
    public boolean isPlayerOnGround(){
        return mPlayerOnGround;
    }

    public void setFoot1OnGround(boolean b){
        mFoot1OnGround = b;
    }

    public void setFoot2OnGround(boolean b){
        mFoot2OnGround = b;
    }

    public boolean isFoot1OnGround(){
        return mFoot1OnGround;
    }
    public boolean isFoot2OnGround(){
        return mFoot2OnGround;
    }

}
