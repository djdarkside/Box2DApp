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

    //public static final String PLAYER = "images/cat.png";
    public static final String PLAYER = "images/you1.png";
    public static final String PLAYERSHEET = "images/you.png";
    public static final String BACKGROUND = "maps/bg_volcano.png";
    public static final String MAP = "maps/test_map_3.tmx";
    public static final String animPlayer = "images/sprite-animation4.png";

    public static final String BKG_FAR = "maps/1/set4_background.png";
    public static final String BKG_MID = "maps/1/set4_hills.png";
    public static final String BKG_FOR = "maps/1/set4_tiles.png";

    public static final String UISKINATLAS = "ui/uiskin.atlas";
    public static final String UISKINJSON = "ui/uiskin.json";
    public static final String UISKIN= "ui/uiskin.png";
    public static final String KEY = "images/keyYellow.png";
    public static final String BACKGROUND2 = "maps/background.png";

    public static final String BKG_ATLAS = "images/bg.atlas";
    public static final String BKG_PNG = "images/bg.png";

    //layered bg
    public static final String BKG5A = "images/bg1/bg5_a.png";
    public static final String BKG5B = "images/bg1/bg5_b.png";
    public static final String BKG5C = "images/bg1/bg5_c.png";
    public static final String BKG5D = "images/bg1/bg5_d.png";
    public static final String BKG5E = "images/bg1/bg5_e.png";
    public static final String BKG5F = "images/bg1/bg5_f.png";
    public static final String BKG5G = "images/bg1/bg5_g.png";
    public static final String COL = "images/bg1/col.png";

    private float progress;
    private ShapeRenderer renderer;

    public LoadingScreen(final Application app) {
        this.app = app;
        renderer = new ShapeRenderer();
    }

    private void queueAssets() {
        app.manager.load(PLAYER, Texture.class);
        app.manager.load(PLAYERSHEET, Texture.class);
        app.manager.load(BACKGROUND, Texture.class);
        app.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        app.manager.load(MAP, TiledMap.class);
        app.manager.load(animPlayer, Texture.class);
        app.manager.load(KEY, Texture.class);
        app.manager.load(BACKGROUND2, Texture.class);

        app.manager.load(BKG_FAR, Texture.class);
        app.manager.load(BKG_FOR, Texture.class);
        app.manager.load(BKG_MID, Texture.class);

        app.manager.load(UISKIN, Texture.class);
        app.manager.load(UISKINATLAS, TextureAtlas.class);

        app.manager.load(BKG_PNG, Texture.class);
        app.manager.load(BKG_ATLAS, TextureAtlas.class);

        //load layered bg
        app.manager.load(BKG5A, Texture.class);
        app.manager.load(BKG5B, Texture.class);
        app.manager.load(BKG5C, Texture.class);
        app.manager.load(BKG5D, Texture.class);
        app.manager.load(BKG5E, Texture.class);
        app.manager.load(BKG5F, Texture.class);
        app.manager.load(BKG5G, Texture.class);
        app.manager.load(COL, Texture.class);
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
