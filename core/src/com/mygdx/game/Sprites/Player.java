package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Platformer;


/**
 * Created by Tony Howarth on 6/27/2016.
 */
public class Player extends Sprite {

    private World mWorld;
    private Body mBody;
    private Texture mPlayerTexture;
    private TextureRegion[] mCurrentTextures;
    private int n = 0;
    private int mCurrentFrame = 0;
    private float mTimer = 0;

    public Player(World world){
        this.mWorld = world;
        definePlayer();
        mPlayerTexture = new Texture("armored_sprite.png");
        mCurrentTextures = new TextureRegion[9];
        for(int i = 0; i < mCurrentTextures.length; i++){
            mCurrentTextures[i] = new TextureRegion(mPlayerTexture, n, 704, 64, 64);
            n += 64;
        }
    }

    public void definePlayer(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50,50);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        mBody = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20f/ Platformer.PPM);
        shape.setPosition(new Vector2(bodyDef.position.x / Platformer.PPM, bodyDef.position.y / Platformer.PPM));

        fixtureDef.shape = shape;
        mBody.createFixture(fixtureDef);
    }

    public void update(float deltaTime){
        mTimer += deltaTime;
        if(mTimer >= .2f) {
            if (mCurrentFrame <= 7) {
                mCurrentFrame++;
            } else {
                mCurrentFrame = 0;
            }
            mTimer = 0;
        }
    }

    public Body getmBody(){
        return this.mBody;
    }

    public TextureRegion getCurrentTexture(){
        return mCurrentTextures[mCurrentFrame];
    }

    public void draw(SpriteBatch batch){
        //batch.draw(mCurrentTextures[mCurrentFrame], mBody.getPosition().x, mBody.getPosition().y);
    }
}
