package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.djdarkside.box2dapp.Application;

/**
 * Created by design on 11/21/2016.
 */
public class ParallaxForeground {

    private ParallaxLayer[] layers;
    private Camera camera;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();
    private final Application app;
    private float width, height;

    /**
     * @param layers  The  background layers
     * @param width   The screenWith
     * @param height The screenHeight
     * @param speed A Vector2 attribute to point out the x and y speed
     */
    public ParallaxForeground(final Application app, ParallaxLayer[] layers, float width, float height , Vector2 speed){
        this.app = app;
        this.layers = layers;
        this.speed.set(speed);
        this.width = width;
        this.height = height;
        camera = app.camera;//new OrthographicCamera(width, height);
        batch = app.batch;
    }


    // Needs to be rendered after all maps and backgrounds
    public void render(float delta) {
        //this.camera.position.add(speed.x * delta, speed.y * delta, 0);
        for(ParallaxLayer layer : layers){
            batch.setProjectionMatrix(camera.projection);
            //batch.setProjectionMatrix(camera.combined);
            batch.begin();
            float currentX = -camera.position.x * layer.parallaxRatio.x % (layer.region.getRegionWidth() + layer.padding.x);
            if (speed.x < 0) currentX += -(layer.region.getRegionWidth() + layer.padding.x);
            do {
                //This seems to fix the layers moving up and down on the y axis
                float currentY = -camera.position.y;
                //Original
                //float currentY = -camera.position.y * layer.parallaxRatio.y % (layer.region.getRegionHeight() + layer.padding.y);
                if (speed.y < 0) currentY += -(layer.region.getRegionHeight() + layer.padding.y);
                do {
                    batch.draw(layer.region,
                            -this.camera.viewportWidth / 2 + currentX + layer.startPosition.x,
                            -this.camera.viewportHeight / 2 + currentY + layer.startPosition.y);
                    currentY += (layer.region.getRegionHeight() + layer.padding.y);
                } while (currentY < camera.viewportHeight);
                currentX += ( layer.region.getRegionWidth() + layer.padding.x);
            } while (currentX < camera.viewportWidth);
            batch.end();
        }
    }
}
