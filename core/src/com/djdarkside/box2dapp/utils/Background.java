package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.djdarkside.box2dapp.Application;

/**
 * Created by design on 10/26/2016.
 */
public class Background extends TextureRegion {

    private final Application app;
    public TextureRegion region;
    public Vector2 position;

    public Background(final Application app, TextureRegion region, Vector2 position) {
        this.app = app;
        this.region = region;
        this.position = position;
        initBackground();
    }

    public void initBackground() {
        //Compile textures and return a texture region
    }

    public void update(float delta) {
        //Add paralax Stuff
    }

    public void render(float delta) {
        app.batch.begin();

        app.batch.end();
    }

    public void dispose() {

    }

}
