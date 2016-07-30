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
                mPlayer.getBody().applyLinearImpulse(new Vector2(0, 6f), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.getArm().applyLinearImpulse(new Vector2(0, 6f), mPlayer.getArm().getWorldCenter(), true);
                mPlayer.setFoot2OnGround(false);
                mPlayer.setFoot1OnGround(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            if (mPlayer.getBody().getLinearVelocity().x <= 3.5f && mPlayer.getArm().getLinearVelocity().x <= 4f) {
                mPlayer.getBody().applyLinearImpulse(new Vector2(.18f, 0), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.getArm().applyLinearImpulse(new Vector2(.18f, 0), mPlayer.getArm().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (mPlayer.getBody().getLinearVelocity().x >= -3.5f && mPlayer.getArm().getLinearVelocity().x >= -4f) {
                mPlayer.getBody().applyLinearImpulse(new Vector2(-.18f, 0), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.getArm().applyLinearImpulse(new Vector2(-.18f, 0), mPlayer.getArm().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(true);
            }
        }
    }

    public Player getPlayer(){
        return this.mPlayer;
    }
}
