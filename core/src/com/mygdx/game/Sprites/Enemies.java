package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tony Howarth on 8/5/2016.
 */
public class Enemies{


    private World mWorld;
    private Player mPlayer;
    private Body mBody;
    private Assets mAssets;
    private EnemyController mController;


    private Animation mFlyAnimation;
    private Animation mAttackAnimation;
    private Animation mDieAnimation;

    private int mHitsTaken = 0;
    private int mHitsToKill = 3;
    boolean mIsDead;


    private TextureRegion mCurrentPlayerFrame;
    private enum State {FLYING, ATTACK, DIE}
    private State mCurrentState;
    private State mPreviousState;
    private int ID;

    private float mStateTimer;
    private float mCorrectAngle;
    private float mPlayerWidth = 1.5f;
    private float mPlayerHeight = 1;
    private float mSpriteXCenter = .5f;
    private float mSpriteYCenter = .66f;
    private float mCurrentTime = 0;

    private boolean mFacingRight = true;
    private final short CATEGORY_ENEMY = 0x0011;

    private Vector2 mSpawnLocation;

    private boolean mIsGroundTypeEnemy;
    private boolean mAttacking = false;
    private boolean mRemovable = false;

    public Enemies(World world, Player player, Vector2 spawnLoc, boolean enemyIsGroundType, int id){
        mWorld = world;
        mPlayer = player;
        mSpawnLocation = spawnLoc;
        mIsGroundTypeEnemy = enemyIsGroundType;
        mController = new EnemyController(mWorld, this, mPlayer, mIsGroundTypeEnemy);
        mAssets = mPlayer.getGameAssets();
        ID = id;
        defineEnemy();
        mFlyAnimation = mAssets.getEnemy_FLYING_Fly_Anim();
        mAttackAnimation = mAssets.getEnemy_FLYING_ATK_Anim();
        mDieAnimation = mAssets.getEnemy_FLYING_DIE_Anim();

    }

    public void defineEnemy(){
        // create player rect
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(mSpawnLocation.x, mSpawnLocation.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mBody = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(.45f);
        fixtureDef.shape = shape;
        fixtureDef.density = .5f;
        fixtureDef.restitution = .2f;
        fixtureDef.filter.groupIndex = CATEGORY_ENEMY;
        mBody.createFixture(fixtureDef).setUserData("enemy" + ID);

        //create sensor
        CircleShape cshape = new CircleShape();
        cshape.setRadius(3);
        fixtureDef.shape = cshape;
        fixtureDef.isSensor = true;
        mBody.createFixture(fixtureDef).setUserData("aggro");

        mBody.setGravityScale(0);
        shape.dispose();
    }

    public void update(float deltaTime){
        animateSprite(deltaTime);
        getBodyAngle();
        mController.update(deltaTime);

        if(mHitsTaken >= mHitsToKill){
            setAttacking(false);
            setIsDead(true);
        }

        if(isDead()){
            mCurrentTime += deltaTime;
            if(mCurrentTime >= 5){
                setRemovable(true);
            }
        }

        if(mBody.getLinearVelocity().x > 0){
            reverseSpriteDirection(false);
        }else{
            reverseSpriteDirection(true);
        }
    }

    public void render(Batch batch){
        if(mFacingRight) {
            batch.draw(mCurrentPlayerFrame, mBody.getPosition().x - mSpriteXCenter, mBody.getPosition().y - mSpriteYCenter, mPlayerWidth, 1.5f);
        }else{
            batch.draw(mCurrentPlayerFrame, mBody.getPosition().x - mSpriteXCenter, mBody.getPosition().y - mSpriteYCenter, mPlayerWidth, 1.5f);
        }
    }

    public void getBodyAngle(){
        float angle = mBody.getAngle();
        mCorrectAngle = angle * MathUtils.radiansToDegrees;
    }

    public void animateSprite(float deltaTime){
        mCurrentPlayerFrame = getFrame(deltaTime);
    }

    public TextureRegion getFrame(float deltaTime){
        mCurrentState = getState();
        TextureRegion region;
        switch(mCurrentState){
            case FLYING:
                region = mFlyAnimation.getKeyFrame(mStateTimer, 0);
                break;
            case ATTACK:
                region = mAttackAnimation.getKeyFrame(mStateTimer, 1);
                break;
            case DIE:
                region = mDieAnimation.getKeyFrame(mStateTimer, 1);
                break;
            default:
                region = mAssets.getEnemy_FLYING_DIE_Anim().getKeyFrame(mStateTimer, 1);
                break;
        }
        mStateTimer = mCurrentState == mPreviousState ? mStateTimer + deltaTime : 0;
        mPreviousState = mCurrentState;
        return region;
    }

    public State getState(){
        if(mIsDead){
            return State.DIE;
        }else {
            return State.FLYING;
        }
    }

    public void reverseSpriteDirection(boolean b){
        if(b){
            mFacingRight = false;
            mSpriteXCenter = -.8f;
            mPlayerWidth = -1.6f;
        }else{
            mFacingRight = true;
            mPlayerWidth = 1.6f;
            mSpriteXCenter = .8f;
        }
    }

    public void increaseHitsTaken(){
        mHitsTaken += 1;
    }

    public Body getBody(){
        return mBody;
    }

    public Vector2 getSpawnLocation(){
        return mSpawnLocation;
    }


    public void setAttacking(boolean b){
        mAttacking = b;
    }

    public boolean isAttacking(){
        return mAttacking;
    }

    public void setIsDead(boolean b){
        mIsDead = b;
    }

    public boolean isDead(){
        return mIsDead;
    }

    public void setRemovable(boolean b){
        mRemovable = b;
    }

    public boolean isRemovable(){
        return mRemovable;
    }

    public int getID(){
        return ID;
    }

}
