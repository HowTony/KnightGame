package com.mygdx.game.Backgrounds;


import com.badlogic.gdx.graphics.Texture;
/**
 * Created by Tony Howarth on 8/26/2016.
 */
public class ParallaxLayer {

    private Texture region ;
    private int mSpeed;

    public ParallaxLayer(Texture texture,int speed){
        this.region  = texture;
        mSpeed = speed;
    }

    public Texture getTexture(){
        return region;
    }

    public int getSpeed(){
        return mSpeed;
    }

}
