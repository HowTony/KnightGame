package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Created by Tony Howarth on 8/2/2016.
 */
public class GunShot {

    private Body mBullet;
    private World mWorld;
    private Player mPlayer;
    private final short CATEGORY_PLAYER = 0x0001;
    private float mTimeAlive = 0;
    private boolean mIsDead = false;
    private float mBulletForce = 5f;
    private Assets mGameAssets;
    private Animation mRingBullet;
    private TextureRegion mCurrentPlayerFrame;
    private enum State {FLYING}
    private State mCurrentState;
    private State mPreviousState;
    private float mStateTimer;
    private float mShotAngle;

    public GunShot(Player player){
        mPlayer = player;
        mWorld = mPlayer.getWorld();
        mGameAssets = mPlayer.getGameAssets();
        mRingBullet = mGameAssets.getRingAnimation();
        mShotAngle = mPlayer.getAimDirection().angle();
        defineBullet();
        shoot();
    }

    public void defineBullet(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(mPlayer.getBulletOrigin().x, mPlayer.getBulletOrigin().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0f;
        mBullet = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.06f, .06f);
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = CATEGORY_PLAYER;
        fixtureDef.filter.categoryBits = CATEGORY_PLAYER;
        fixtureDef.density = 5f;

        mBullet.createFixture(fixtureDef).setUserData("shot");
        mBullet.isBullet();


        shape.dispose();
    }

    public Body getBody(){
        return mBullet;
    }


    public void update(float deltaTime){
        mTimeAlive += deltaTime;
        if(mTimeAlive > 3){
            setIsDead(true);
        }
        animateSprite(deltaTime);
    }

    public void render(Batch sb){
        sb.draw(mCurrentPlayerFrame, mBullet.getPosition().x - .54f , mBullet.getPosition().y - .46f, .5f, .5f, 1,1,1,1, mShotAngle);
        //sb.draw(mCurrentPlayerFrame, mBullet.getPosition().x - .54f , mBullet.getPosition().y - .46f, originX, originY, width, height, scaleX, ScaleY, roation);

    }

    public void shoot(){
        //mBullet.applyLinearImpulse(mPlayer.getAimDirection().x /500 , mPlayer.getAimDirection().y /400, Gdx.input.getX(), Gdx.input.getY(), false);
        Vector2 bulletForce = new Vector2(mPlayer.getAimDirection().x * mBulletForce, mPlayer.getAimDirection().y * mBulletForce);
        Vector2 mousePos = new Vector2((Gdx.input.getX()), Gdx.input.getY());
        mBullet.applyForce(bulletForce, mousePos, false);
    }

    public float getTimeAlive(){
        return mTimeAlive;
    }

    public void setIsDead(boolean b){
        mIsDead = b;
    }

    public boolean isDead(){
        return mIsDead;
    }


    public void animateSprite(float deltaTime){
        mCurrentPlayerFrame = getFrame(deltaTime);
    }

    public TextureRegion getFrame(float deltaTime){
        mCurrentState = getState();
        TextureRegion region;
        switch(mCurrentState){
            case FLYING:
                region = mRingBullet.getKeyFrame(mStateTimer, 0);
                break;
            default:
                region = mGameAssets.getEmptyAnimation().getKeyFrame(mStateTimer, 1);
                break;
        }
        mStateTimer = mCurrentState == mPreviousState ? mStateTimer + deltaTime : 0;
        mPreviousState = mCurrentState;
        return region;
    }
    public State getState(){
        return State.FLYING;
    }



}
