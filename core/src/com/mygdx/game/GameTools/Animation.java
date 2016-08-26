package com.mygdx.game.GameTools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tony Howarth on 7/6/2016.
 */
public class Animation {


    private final int ANIMATION_LOOPING = 0;
    private final int ANIMATION_NONLOOPING = 1;

    final TextureRegion[] keyFrames;
    final float frameDuration;

    public Animation(float frameDuration, TextureRegion[] keyFrames) {
        this.frameDuration = frameDuration;
        this.keyFrames = keyFrames;
    }

    public TextureRegion getKeyFrame(float stateTime, int mode) {
        int frameNumber = (int) (stateTime / frameDuration);

        if (mode == ANIMATION_NONLOOPING) {
            frameNumber = Math.min(keyFrames.length - 1, frameNumber);
        } else {
            frameNumber = frameNumber % keyFrames.length;
        }
        return keyFrames[frameNumber];
    }


    public int getAnimLoop(){
        return this.ANIMATION_LOOPING;
    }

    public int getAnimNONLoop(){
        return this.ANIMATION_NONLOOPING;
    }
}


