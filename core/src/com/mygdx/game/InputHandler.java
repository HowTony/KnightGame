package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Sprites.Player;

/**
 * Created by Tony Howarth on 7/19/2016.
 */
public class InputHandler {

    private Player mPlayer;
    private boolean mPlayerOnGround;

    public InputHandler(Player player){
        mPlayer = player;
    }

    public void update(float deltatime){
        handleInput(deltatime);
    }

    public void setPlayerOnGround(boolean b){
        this.mPlayerOnGround = b;
    }

    public void handleInput(float deltaTime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(mPlayerOnGround) {
                mPlayer.getmBody().applyLinearImpulse(new Vector2(0, 6f), mPlayer.getmBody().getWorldCenter(), true);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && mPlayer.getmBody().getLinearVelocity().x <= 3){
            mPlayer.getmBody().applyLinearImpulse(new Vector2(.2f,0), mPlayer.getmBody().getWorldCenter(), true);
            mPlayer.reverseSpriteDirection(false);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && mPlayer.getmBody().getLinearVelocity().x >= -3){
            mPlayer.getmBody().applyLinearImpulse(new Vector2(-.2f,0), mPlayer.getmBody().getWorldCenter(), true);
            mPlayer.reverseSpriteDirection(true);
        }
    }

}
