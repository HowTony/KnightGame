package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tony Howarth on 8/2/2016.
 */
public class GunShot {

    private Body mBullet;
    private World mWorld;
    private Player mPlayer;
    final short CATEGORY_PLAYER = 0x0001;

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
        CircleShape shape = new CircleShape();
        shape.setRadius(.05f);
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



    }

    public void render(){

    }

    public void shoot(){
            mBullet.applyLinearImpulse(mPlayer.getAimDirection().x /500 , mPlayer.getAimDirection().y /500, Gdx.input.getX(), Gdx.input.getY(), false);

    }


}
