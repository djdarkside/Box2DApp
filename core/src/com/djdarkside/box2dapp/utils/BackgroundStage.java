package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;

/**
 * Created by djdarkside on 10/30/2016.
 */
public class BackgroundStage implements Disposable{

    private final Application app;
    private SpriteBatch batch;
    private OrthographicCamera bkgCam;
    public Stage stage;
    public Image image;
    public TextureRegion regionTex;

    public BackgroundStage(final Application app, int levelNum) {
        this.app = app;
        bkgCam = new OrthographicCamera();
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, bkgCam));
        initBackground(levelNum);
    }

    public void initBackground(int levelNum) {
        if (levelNum == 1) {
            regionTex = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND, Texture.class));
            image = new Image(regionTex);
            image.setSize(image.getWidth(), image.getHeight());
            stage.addActor(image);
        } else if (levelNum == 2) {
            regionTex = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND2, Texture.class));
            image = new Image(regionTex);
            image.setSize(image.getWidth() * 2, image.getHeight() * 2);
            stage.addActor(image);
        }
    }

    public void update(float delta) {
        stage.act(delta);
        bkgCam.update();
    }

    public void render(float delta) {
        //stage.act(delta);
    }

    public void dispose() {
        stage.dispose();
    }
}


