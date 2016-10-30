package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;

/**
 * Created by design on 10/26/2016.
 */
public class Background {

    private final Application app;
    private TextureRegion region;
    private SpriteBatch batch;
    private int levelNum;
    private OrthographicCamera bkgCam;

    public Background(final Application app, int levelNum) {
        this.app = app;
        this.levelNum = levelNum;
        bkgCam = new OrthographicCamera();
        initBackground(levelNum);
    }

    public void initBackground(int levelNum) {
        if (levelNum == 1) {
            region = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND, Texture.class));
            region.setRegion(0, 0, region.getRegionWidth(), region.getRegionHeight());
            bkgCam.setToOrtho(false, region.getRegionWidth() / Constants.SCALE, region.getRegionHeight() / Constants.SCALE);
        } else if (levelNum == 2) {
            region = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND2, Texture.class));
            region.setRegion(0, 0, region.getRegionWidth(), region.getRegionHeight());
        }
    }

    public void update(float delta) {
        bkgCam.update();
    }

    public void render(float delta) {
        //app.batch.setProjectionMatrix(bkgCam.combined);
        app.batch.begin();
        app.batch.draw(region, 0, 0, region.getRegionWidth(), region.getRegionHeight());
        app.batch.draw(region, 0, 0, region.getRegionWidth() + region.getRegionWidth(), region.getRegionHeight());
        app.batch.end();
    }

    public void dispose() {

    }

}
