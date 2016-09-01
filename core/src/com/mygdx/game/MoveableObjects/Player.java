package com.mygdx.game.MoveableObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameTools.Animation;
import com.mygdx.game.GameTools.Assets;

/**
 * Created by Tony Howarth on 6/27/2016.
 */
public class Player extends Sprite {

    private World mWorld;
    private Body mBody;

    private TextureRegion mCurrentPlayerFrame;

    private Assets mGameAssets;
    private enum State {FALLING, JUMPING, STANDING, RUNNING, WALKING, DEATH, CROUCH, SHOOT, LANDING, EMPTY}
    private State mCurrentState;
    private State mPreviousState;
    private float mStateTimer;

    private float mPlayerWidth = 1.28f;
    private float mPlayerHeight = 1.28f;
    private float mSpriteXCenter = mPlayerWidth /2;
    private float mSpriteYCenter = mPlayerHeight /2;
    private float mCorrectAngle;

    private float mArmWidth = 1.23f;
    private float mArmHeight = 1.23f;
    private float mArmOriginX = mArmHeight/2;
    private float mArmOriginY = mArmWidth/2;
    private float mSpriteScaleWidth = 1;
    private float mSpriteScaleHeight = 1;
    private float mXDisplacement = .21f;
    private float mYDisplacement = .21f;

    private Animation mJumpAnime;
    private Animation mFallingAnime;
    private Animation mLandingAnime;
    private Animation mRunAnime;
    private Animation mWalkAnime;
    private Animation mStandAnime;
    private Animation mDeathAnime;
    private Animation mCrouchAnime;
    private Animation mShootAnime;

    private boolean mPlayerOnGround;
    private boolean mFoot1OnGround = false;
    private boolean mFoot2OnGround = false;
    private boolean mFacingRight = true;
    private boolean mIsDead = false;

    private float mCurrentTime;

    final short CATEGORY_PLAYER = 0x0001;
    private int mHitsTilDeath = 7;

    private Vector2 mAimDirection;
    private Vector3 mMousePos;

    public Player(World world, Vector3 mouseLoc){
        this.mWorld = world;
        mMousePos = mouseLoc;
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
        mWalkAnime = mGameAssets.getWalkingAnimation();
        mStandAnime = mGameAssets.getAlienStandingAnimation();
        mDeathAnime = mGameAssets.getAlienDeathAnimation();
        mCrouchAnime = mGameAssets.getAlienCrouchAnimation();
        mShootAnime = mGameAssets.getAlienShootAnimation();
    }

    public void definePlayer(){
        // create player rect
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(2,1 );
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mBody = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.15f,.33f);
        fixtureDef.shape = shape;
        //fixtureDef.density = 0;
        fixtureDef.filter.categoryBits = CATEGORY_PLAYER;
        fixtureDef.filter.groupIndex = CATEGORY_PLAYER;
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
        mCurrentTime += deltaTime;
        if(mHitsTilDeath <= 0){
            mIsDead = true;
        }
        armUpdate();
        if(mFoot1OnGround || mFoot2OnGround){
            setPlayerOnGround(true);
        }else{
            setPlayerOnGround(false);
        }
        if(mBody.getPosition().y < 0){
            if(mBody.getPosition().x <= 2){
                mBody.setTransform(2, 1.2f, 0);
            }else {
                mBody.setTransform(mBody.getPosition().x - 2, mBody.getPosition().y + 5, 0);
            }
            takeDamage();
        }
        AnimateSprite(deltaTime);
        setPosition(mBody.getPosition().x - getWidth() / 2, .1f + mBody.getPosition().y - getHeight() / 2);
    }

    public void render(SpriteBatch batch){
        batch.draw(mCurrentPlayerFrame, getBody().getPosition().x - mPlayerWidth/2, getBody().getPosition().y - mPlayerHeight/3, mPlayerWidth , mPlayerHeight);
        if(mPlayerOnGround && !isDead()) {
            if(!mFacingRight) {
                batch.draw(mGameAssets.getArmGunLEFT(), getBody().getPosition().x + mPlayerWidth/2.3f,
                        getBody().getPosition().y - mPlayerHeight/2.9f, mArmOriginX, mArmOriginY, mArmWidth, mArmHeight,
                        mSpriteScaleWidth, mSpriteScaleHeight, mCorrectAngle);
            }else{
                batch.draw(mGameAssets.getArmGunRIGHT(), getBody().getPosition().x - mPlayerWidth/1.8f,
                        getBody().getPosition().y - mPlayerHeight/2.9f, mArmOriginX, mArmOriginY, mArmWidth, mArmHeight,
                        mSpriteScaleWidth, mSpriteScaleHeight, mCorrectAngle);
            }
        }
    }

    public void armUpdate(){
        mAimDirection = new Vector2((mMousePos.x) - mBody.getPosition().x, (mMousePos.y) - mBody.getPosition().y);
        mAimDirection.nor();
        Vector2 xAxis = new Vector2(1, 0);
        float angle = MathUtils.atan2(mAimDirection.y, mAimDirection.x) - MathUtils.atan2(xAxis.y, xAxis.x);
        mCorrectAngle = angle * MathUtils.radiansToDegrees;

//        if(mFacingRight) {
//            if (mCorrectAngle > 50) {
//                mCorrectAngle = 50;
//            } else if (mCorrectAngle < 0) {
//                mCorrectAngle = 0;
//            }
//        }else{
//            if (mCorrectAngle > -180 && mCorrectAngle < 50) {
//                mCorrectAngle = -180;
//            } else if (mCorrectAngle < 135 && mCorrectAngle > 0) {
//                mCorrectAngle = 135;
//            }
//        }

    }

    public void reverseSpriteDirection(boolean b){
        if(b){
            mFacingRight = false;
            mSpriteXCenter = -.48f;
            mPlayerWidth = -1.28f;
        }else{
            mFacingRight = true;
            mPlayerWidth = 1.28f;
            mSpriteXCenter = .48f;
        }
    }

    public Vector2 getBulletOrigin(){
        Vector2 bulletOrigin;
        Vector2 normalAimDir = new Vector2(mAimDirection);
        normalAimDir = normalAimDir.nor();
        float gunLength = .6f;
        if(!mFacingRight) {
            bulletOrigin = new Vector2((mBody.getPosition().x + mXDisplacement) + normalAimDir.x * gunLength, ((mBody.getPosition().y + mYDisplacement )) + normalAimDir.y * gunLength );
            return bulletOrigin;
        }else{
            bulletOrigin = new Vector2((mBody.getPosition().x - mXDisplacement) + normalAimDir.x * gunLength, ((mBody.getPosition().y + mYDisplacement)) + normalAimDir.y * gunLength );
            return bulletOrigin;
        }
    }

    public void AnimateSprite(float deltaTime){
        mCurrentPlayerFrame = getFrame(deltaTime);
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
            case WALKING:
                region = mWalkAnime.getKeyFrame(mStateTimer, 0);
                break;
            case STANDING:
                region = mStandAnime.getKeyFrame(mStateTimer, 0);
                break;
            case DEATH:
                region = mDeathAnime.getKeyFrame(mStateTimer, 1);
                break;
            default:
                region = mStandAnime.getKeyFrame(mStateTimer, 0);
                break;
        }
        mStateTimer = mCurrentState == mPreviousState ? mStateTimer + deltaTime : 0;
        mPreviousState = mCurrentState;
        return region;
    }

    public State getState() {
        if (!mIsDead) {
            if (mBody.getLinearVelocity().y > 0 && !mPlayerOnGround) {
                return State.JUMPING;
            } else if (mBody.getLinearVelocity().y < 0 && !mPlayerOnGround) {
                return State.FALLING;
            } else if (mBody.getLinearVelocity().y == 0 && mPlayerOnGround && mPreviousState == State.FALLING) {
                return State.LANDING;
            } else if (mBody.getLinearVelocity().x < -3 || mBody.getLinearVelocity().x > 3 && mPlayerOnGround) {
                return State.RUNNING;
            } else if (mBody.getLinearVelocity().x < 0 || mBody.getLinearVelocity().x > 0 && mPlayerOnGround) {
                return State.WALKING;
            } else {
                return State.STANDING;
            }
        }else{
            return State.DEATH;
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

    public Assets getGameAssets(){
        return mGameAssets;
    }

    public void setIsDead(boolean b){
        mIsDead = b;
    }

    public boolean isDead(){
        return mIsDead;
    }

    public void takeDamage(){
        mHitsTilDeath -= 1;
    }

    public int getHitsTilDeath(){
        return mHitsTilDeath;
    }

    public boolean isFacingRight(){
        return mFacingRight;
    }

    public Body getBody(){
        return this.mBody;
    }

    public Vector2 getAimDirection(){
        return mAimDirection;
    }

    public World getWorld(){
        return mWorld;
    }


}
