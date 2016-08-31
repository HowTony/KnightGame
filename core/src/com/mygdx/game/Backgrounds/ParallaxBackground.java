package com.mygdx.game.Backgrounds;

/**
 * Created by Tony Howarth on 8/26/2016.
 */

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameTools.Platformer;
import com.mygdx.game.MoveableObjects.Player;

public class ParallaxBackground {

    private ParallaxLayer[] layers;
    private Camera camera;
    private Player mPlayer;
    private int currentX;


    public ParallaxBackground(ParallaxLayer[] layers, float width, float height, Player player) {
        this.layers = layers;
        mPlayer = player;
        camera = new OrthographicCamera(width,height);
    }

    public void update(float deltaTime) {

    }


    public void render(SpriteBatch mBatch) {
        mBatch.setProjectionMatrix(camera.projection);
        mBatch.begin();
        for (int i = 0; i < layers.length; i++) {
            layers[i].getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            currentX = (int)-mPlayer.getX() * layers[i].getSpeed();
            if(i == 0){
                mBatch.draw(layers[i].getTexture(),-Platformer.V_WIDTH /2 , -Platformer.V_HEIGHT /2, -currentX,0, layers[i].getTexture().getWidth(), layers[i].getTexture().getHeight()+100);
            }else{
                mBatch.draw(layers[i].getTexture(),-Platformer.V_WIDTH /2 , -Platformer.V_HEIGHT /2, -currentX,0, layers[i].getTexture().getWidth(), layers[i].getTexture().getHeight());
            }
        }
        mBatch.end();
    }
}
