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

    public InputHandler(Player player) {
        mPlayer = player;
    }

    public void update(float deltatime) {
        handleInput(deltatime);
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (mPlayer.isPlayerOnGround()) {
                mPlayer.getmBody().applyLinearImpulse(new Vector2(0, 6f), mPlayer.getmBody().getWorldCenter(), true);
                mPlayer.setFoot2OnGround(false);
                mPlayer.setFoot1OnGround(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            if (mPlayer.getmBody().getLinearVelocity().x <= 4) {
                mPlayer.getmBody().applyLinearImpulse(new Vector2(.2f, 0), mPlayer.getmBody().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (mPlayer.getmBody().getLinearVelocity().x >= -4) {
                mPlayer.getmBody().applyLinearImpulse(new Vector2(-.2f, 0), mPlayer.getmBody().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(true);
            }
        }
    }

    public Player getPlayer(){
        return this.mPlayer;
    }
}
