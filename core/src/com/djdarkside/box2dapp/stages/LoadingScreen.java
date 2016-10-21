package com.djdarkside.box2dapp.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;

/**
 * Created by djdarkside on 10/21/2016.
 */
public class LoadingScreen implements Screen {

    private final Application app;

    public static final String PLAYER = "images/cat.png";
    public static final String BACKGROUND = "maps/bg_volcano.png";
    public static final String MAP = "maps/test_map_2.tmx";

    private float progress;

    public LoadingScreen(final Application app) {
        this.app = app;
    }

    private void queueAssets() {
        app.manager.load(PLAYER, Texture.class);
        app.manager.load(BACKGROUND, Texture.class);
        app.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        app.manager.load(MAP, TiledMap.class);
    }

    public void update(float delta) {
        progress = MathUtils.lerp(progress, app.manager.getProgress(), .1f);
        if (app.manager.update() && progress >= app.manager.getProgress() - .01f) {
            app.setScreen(app.pStage);
        }
    }

    @Override
    public void show() {
        System.out.println("Loading Screen");
        this.progress = 0f;
        queueAssets();
        while(!app.manager.update()) {
            System.out.println(app.manager.getProgress() * 100 + "%");
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        app.batch.begin();
        app.font.draw(app.batch, "Loading", Constants.V_WIDTH / 2 - 24, 54);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
