package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tony Howarth on 8/2/2016.
 */
public class GunShot {

    private Body mBullet;
    private World mWorld;
    private Player mPlayer;
    final short CATEGORY_PLAYER = 0x0001;
    float mTimeAlive = 0;

    public GunShot(Player player){
        mPlayer = player;
        mWorld = mPlayer.getWorld();
        defineBullet();
        shoot();
    }

    public void defineBullet(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(mPlayer.getBulletOrigin().x, mPlayer.getBulletOrigin().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mBullet = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.06f, .06f);
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = CATEGORY_PLAYER;
        fixtureDef.density = .1f;
        mBullet.createFixture(fixtureDef).setUserData("player");
        mBullet.isBullet();

        shape.dispose();
    }

    public Body getBody(){
        return mBullet;
    }


    public void update(float deltaTime){
        mTimeAlive += deltaTime;
    }

    public void render(Batch sb){
        sb.draw(mPlayer.getGameAssets().getDucky(),mBullet.getPosition().x - .54f , mBullet.getPosition().y - .46f, 1, 1);

    }

    public void shoot(){
            mBullet.applyLinearImpulse(mPlayer.getAimDirection().x /500 , mPlayer.getAimDirection().y /400, Gdx.input.getX(), Gdx.input.getY(), false);

    }

    public float getTimeAlive(){
        return mTimeAlive;
    }


}
