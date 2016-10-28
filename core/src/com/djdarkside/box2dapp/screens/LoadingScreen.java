package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
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
    public static final String animPlayer = "images/sprite-animation4.png";

    public static final String UISKINATLAS = "ui/uiskin.atlas";
    public static final String UISKINJSON = "ui/uiskin.json";
    public static final String UISKIN= "ui/uiskin.png";

    private float progress;
    private ShapeRenderer renderer;

    public LoadingScreen(final Application app) {
        this.app = app;
        renderer = new ShapeRenderer();
    }

    private void queueAssets() {
        System.out.println("QUEUE");
        app.manager.load(PLAYER, Texture.class);
        app.manager.load(BACKGROUND, Texture.class);
        app.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        app.manager.load(MAP, TiledMap.class);
        app.manager.load(animPlayer, Texture.class);

        app.manager.load(UISKIN, Texture.class);
        app.manager.load(UISKINATLAS, TextureAtlas.class);
    }

    public void update(float delta) {
        progress = MathUtils.lerp(progress, app.manager.getProgress(), .1f);
        if (app.manager.update() && progress >= app.manager.getProgress() - .01f) {
            app.setScreen(app.mStage);
        }
    }

    @Override
    public void show() {
        progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // Loading Bar
        // Empty
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(32, app.camera.viewportHeight / 10 - 8, Constants.V_WIDTH - 64, 16);
        //Full - Fills when assets are loaded
        renderer.setColor(Color.BLUE);
        renderer.rect(32, app.camera.viewportHeight / 10 - 8, progress * (Constants.V_WIDTH - 64), 16);
        renderer.end();
        //End Loading Bar

        app.batch.begin();
        app.font.draw(app.batch, "Loading...", Constants.V_WIDTH / 2 - 24, 64);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        app.camera.setToOrtho(false, width / Constants.SCALE, height / Constants.SCALE);
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
        renderer.dispose();
    }
}
