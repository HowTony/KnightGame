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
    private TextureRegion mArmGunRIGHT = new TextureRegion();
    private TextureRegion mArmGunLEFT = new TextureRegion();
    private TextureRegion mDuckyBullet = new TextureRegion();
    private TextureRegion[] mRingBullet = new TextureRegion[4];
    private TextureRegion[] mEnemy_FLYING_Fly = new TextureRegion[4];
    private TextureRegion[] mEnemy_FLYING_ATK = new TextureRegion[2];
    private TextureRegion[] mEnemy_FLYING_DIE = new TextureRegion[2];
    private TextureRegion mAlienHead = new TextureRegion();
    private TextureRegion mMouseCursor = new TextureRegion();

    private Animation mAlienWalkingAnimation;
    private Animation mAlienStandingAnimation;
    private Animation mAlienRunningAnimation;
    private Animation mAlienJumpAnimation;
    private Animation mAlienFallingAnimation;
    private Animation mAlienLandingAnimation;
    private Animation mAlienDeathAnimation;
    private Animation mAlienCrouchAnimation;
    private Animation mAlienShootAnimation;
    private Animation mRingAnimation;
    private Animation mEnemy_FLYING_Fly_Anim;
    private Animation mEnemy_FLYING_ATK_Anim;
    private Animation mEnemy_FLYING_DIE_Anim;

   public void load(){
       mSpriteSheet = loadTexture("alien_sprite_NOARMS.png");

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
       yLocation += 128;
       xLocation = 0;
       mArmGunRIGHT = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
       xLocation += 128;
       mArmGunLEFT = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
       xLocation += 128;
       mDuckyBullet = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
       xLocation += 128;
       for(int i = 0; i < mRingBullet.length; i++){
           mRingBullet[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       mAlienHead = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);

       xLocation += 128;
       mMouseCursor = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);

       yLocation += 128;
       xLocation = 0;
       for(int i = 0; i < mEnemy_FLYING_Fly.length; i++){
           mEnemy_FLYING_Fly[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       for(int i = 0; i < mEnemy_FLYING_ATK.length; i++){
           mEnemy_FLYING_ATK[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       for(int i = 0; i < mEnemy_FLYING_DIE.length; i++){
           mEnemy_FLYING_DIE[i] = new TextureRegion(mSpriteSheet, xLocation, yLocation, 128, 128);
           xLocation += 128;
       }

       mAlienWalkingAnimation = new Animation(0.1f, mAlienWalk);
       mAlienRunningAnimation = new Animation(0.1f, mAlienRun);
       mAlienStandingAnimation = new Animation(0.6f, mAlienStand);
       mAlienJumpAnimation = new Animation(0.08f, mAlienJump);
       mAlienFallingAnimation = new Animation(0.2f, mAlienFall);
       mAlienLandingAnimation = new Animation(0.1f, mAlienLand);
       mAlienDeathAnimation = new Animation(0.1f, mAlienDeath);
       mAlienCrouchAnimation = new Animation(0.08f, mAlienCrouch);
       mAlienShootAnimation = new Animation(0.08f, mAlienShoot);
       mRingAnimation = new Animation(0.1f, mRingBullet);
       mEnemy_FLYING_Fly_Anim = new Animation(0.3f, mEnemy_FLYING_Fly);
       mEnemy_FLYING_ATK_Anim = new Animation(0.15f, mEnemy_FLYING_ATK);
       mEnemy_FLYING_DIE_Anim = new Animation(0.8f, mEnemy_FLYING_DIE);
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

    public Animation getRingAnimation(){
        return mRingAnimation;
    }

    public Animation getEnemy_FLYING_Fly_Anim(){
        return mEnemy_FLYING_Fly_Anim;
    }

    public Animation getEnemy_FLYING_ATK_Anim(){
        return mEnemy_FLYING_ATK_Anim;
    }

    public Animation getEnemy_FLYING_DIE_Anim(){
        return mEnemy_FLYING_DIE_Anim;
    }

    public TextureRegion getArmGunRIGHT(){
        return this.mArmGunRIGHT;
    }

    public TextureRegion getArmGunLEFT(){
        return this.mArmGunLEFT;
    }

    public TextureRegion getDucky(){
        return this.mDuckyBullet;
    }

    public TextureRegion getAlienHead(){
        return mAlienHead;
    }

    public TextureRegion getMousecursor(){
        return mMouseCursor;
    }

    public Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

}
