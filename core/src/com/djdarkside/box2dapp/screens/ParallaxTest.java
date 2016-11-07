package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.OrthoCamController;
import com.djdarkside.box2dapp.utils.ParallaxCamera;


/**
 * Created by djdarkside on 11/6/2016.
 */
public class ParallaxTest {

    private final Application app;
    public Stage stage;

    TextureRegion[] layers;
    ParallaxCamera camera;
    OrthoCamController controller;


    public ParallaxTest(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT));
        initBackground();
        camera = new ParallaxCamera(480, 320);
    }

    public void initBackground() {
        layers = new TextureRegion[3];
        layers[0] = new TextureRegion(app.manager.get(LoadingScreen.BKG_FAR, Texture.class));
        layers[1] = new TextureRegion(app.manager.get(LoadingScreen.BKG_MID, Texture.class));
        layers[2] = new TextureRegion(app.manager.get(LoadingScreen.BKG_FOR, Texture.class));
        //image = new Image(regionTex);
        //image1 = new Image(regionTex1);
        //image2 = new Image(regionTex2);
        //image.setSize(image.getWidth() * 2, image.getHeight() * 2);
        //image1.setSize(image.getWidth(), image.getHeight());
        //image2.setSize(image.getWidth(), image.getHeight());
        //stage.addActor(image);
        //stage.addActor(image2);
        //stage.addActor(image1);

    }

    public void dispose () {
        layers[0].getTexture().dispose();
    }

    public void render (float delta) {

        // keep camera in foreground layer bounds
        boolean updateCamera = false;
        if (camera.position.x < -1024 + camera.viewportWidth / 2) {
            camera.position.x = -1024 + (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }

        if (camera.position.x > 1024 - camera.viewportWidth / 2) {
            camera.position.x = 1024 - (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }

        if (camera.position.y < 0) {
            camera.position.y = 0;
            updateCamera = true;
        }
        // arbitrary height of scene
        if (camera.position.y > 400 - camera.viewportHeight / 2) {
            camera.position.y = 400 - (int)(camera.viewportHeight / 2);
            updateCamera = true;
        }

        // background layer, no parallax, centered around origin
        app.batch.setProjectionMatrix(camera.calculateParallaxMatrix(0, 0));
        app.batch.disableBlending();
        app.batch.begin();
        app.batch.draw(layers[0], -(int)(layers[0].getRegionWidth() / 2), -(int)(layers[0].getRegionHeight() / 2));
        app.batch.end();
        app.batch.enableBlending();

        // midground layer, 0.5 parallax (move at half speed on x, full speed on y)
        // layer is 1024x320
        app.batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.5f, 1));
        app.batch.begin();
        app.batch.draw(layers[1], -512, -160);
        app.batch.end();

        // foreground layer, 1.0 parallax (move at full speed)
        // layer is 2048x320
        app.batch.setProjectionMatrix(camera.calculateParallaxMatrix(1f, 1));
        app.batch.begin();
        for (int i = 0; i < 9; i++) {
            app.batch.draw(layers[2], i * layers[2].getRegionWidth() - 1024, -160);
        }
        app.batch.end();

    }
}
