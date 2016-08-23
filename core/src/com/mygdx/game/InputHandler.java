package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.ShotManager;

/**
 * Created by Tony Howarth on 7/19/2016.
 */
public class InputHandler {
    private Player mPlayer;
    private ShotManager mShoot;
    private boolean mCanShoot;

    public InputHandler(Player player, ShotManager shots) {
        mPlayer = player;
        mShoot = shots;
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (mPlayer.isPlayerOnGround()) {
                mPlayer.getBody().applyLinearImpulse(new Vector2(0, 6f), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.setFoot2OnGround(false);
                mPlayer.setFoot1OnGround(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            if (mPlayer.getBody().getLinearVelocity().x <= 3.5f) {
                mPlayer.getBody().applyLinearImpulse(new Vector2(.18f, 0), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (mPlayer.getBody().getLinearVelocity().x >= -3.5f) {
                mPlayer.getBody().applyLinearImpulse(new Vector2(-.18f, 0), mPlayer.getBody().getWorldCenter(), true);
                mPlayer.reverseSpriteDirection(true);
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if(mPlayer.isPlayerOnGround() && mCanShoot) {
                mShoot.addBullets();
                mCanShoot = false;
            }
        }else{
            mCanShoot = true;
        }
    }

    public Player getPlayer(){
        return this.mPlayer;
    }

    public void update(float deltatime, Batch sb) {
        handleInput(deltatime);
        mShoot.update(deltatime);
        mShoot.render(sb);
    }
}
