package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tony Howarth on 7/13/2016.
 */
public class Assets {

    private Texture mSpriteSheet;
    private TextureRegion[] mAlienShoot = new TextureRegion[12];
    private TextureRegion[] mAlienCrouch = new TextureRegion[5];
    private TextureRegion[] mAlienStand = new TextureRegion[2];
    private TextureRegion[] mAlienDeath = new TextureRegion[12];
    private TextureRegion[] mAlienJump = new TextureRegion[6];
    private TextureRegion[] mAlienFall = new TextureRegion[3];
    private TextureRegion[] mAlienLand = new TextureRegion[3];

    private TextureRegion[] mAlienRun = new TextureRegion[12];
    private TextureRegion[] mAlienWalk = new TextureRegion[12];

    private Animation mAlienWalkingAnimation;
    private Animation mAlienStandingAnimation;
    private Animation mAlienRunningAnimation;
    private Animation mAlienJumpAnimation;
    private Animation mAlienFallingAnimation;
    private Animation mAlienLandingAnimation;
    private Animation mAlienDeathAnimation;
    private Animation mAlienCrouchAnimation;
    private Animation mAlienShootAnimation;



   public void load(){
       mSpriteSheet = loadTexture("alien_sprite.png");

       int yLocation = 0;
       int xLocation = 1;
       for(int i = 0; i < mAlienShoot.length; i++){
           mAlienShoot[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }
       yLocation += 128;
       xLocation = 0;
       for(int i = 0; i < mAlienCrouch.length; i++){
           mAlienCrouch[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }
       xLocation = 0;
       for(int i = 0; i < mAlienStand.length; i++){
           mAlienStand[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }


       yLocation += 128;
       xLocation = 0;

       for(int i = 0; i < mAlienDeath.length; i++){
           mAlienDeath[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }
       yLocation += 128;
       xLocation = 0;
       for(int i = 0; i < mAlienJump.length; i++){
           mAlienJump[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       for(int i = 0; i < mAlienFall.length; i++){
           mAlienFall[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       for(int i = 0; i < mAlienLand.length; i++){
           mAlienLand[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       yLocation += 128;
       xLocation = 0;
       for(int i = 0; i < mAlienRun.length; i++){
           mAlienRun[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }
       yLocation += 128;
       xLocation = 0;
       for(int i = 0; i < mAlienWalk.length; i++){
           mAlienWalk[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       mAlienWalkingAnimation = new Animation(0.08f, mAlienWalk);
       mAlienRunningAnimation = new Animation(0.08f, mAlienRun);
       mAlienStandingAnimation = new Animation(0.6f, mAlienStand);
       mAlienJumpAnimation = new Animation(0.05f, mAlienJump);
       mAlienFallingAnimation = new Animation(0.2f, mAlienFall);
       mAlienLandingAnimation = new Animation(0.1f, mAlienLand);
       mAlienDeathAnimation = new Animation(0.08f, mAlienDeath);
       mAlienCrouchAnimation = new Animation(0.08f, mAlienCrouch);
       mAlienShootAnimation = new Animation(0.08f, mAlienShoot);
   }

    public Animation getWalkingAnimation(){
        return this.mAlienWalkingAnimation;
    }

    public Animation getAlienRunningAnimation(){
        return this.mAlienRunningAnimation;
    }

    public Animation getAlienStandingAnimation(){
        return this.mAlienStandingAnimation;
    }

    public Animation getAlienJumpAnimation(){
        return this.mAlienJumpAnimation;
    }

    public Animation getAlienShootAnimation(){
        return this.mAlienShootAnimation;
    }

    public Animation getAlienDeathAnimation(){
        return this.mAlienDeathAnimation;
    }

    public Animation getAlienCrouchAnimation(){
        return this.mAlienCrouchAnimation;
    }

    public Animation getAlienFallingAnimation(){
        return this.mAlienFallingAnimation;
    }
    public Animation getAlienLandingAnimation(){
        return this.mAlienLandingAnimation;
    }

    public Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

}
