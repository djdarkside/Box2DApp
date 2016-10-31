package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.entities.Player;
import com.djdarkside.box2dapp.utils.*;

/**
 * Created by djdarkside on 10/25/2016.
 */
public class TestStage implements Screen {

    private final Application app;
    private Player player;
    private World world;

    private Box2DDebugRenderer b2dr;
    private Sprite key;
    private Hud hud;
    private Stage stage;
    private Background background;
    private BackgroundStage backgroundStage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    public int levelWidth = 0;
    public int levelHeight = 0;

    public static float xPos;

    public TestStage(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
        hud = new Hud(app.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        key = new Sprite(app.manager.get(LoadingScreen.KEY, Texture.class));

        backgroundStage = new BackgroundStage(app, 1);

        world = WorldUtils.createWorld();
        b2dr = new Box2DDebugRenderer();

        player = new Player(app, world);

        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map);

        MapProperties props = map.getProperties();
        levelWidth = props.get("width", Integer.class);
        levelHeight = props.get("height", Integer.class);

        TiledObjectUtil.buildShapes(map, Constants.PPM, world);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        backgroundStage.stage.draw();
        //backgroundStage.render(delta);

        app.batch.begin();
        app.batch.draw(key, 700, 280, key.getWidth() / 2, key.getHeight() / 2);
        app.batch.end();

        tmr.render();
        b2dr.render(world, app.camera.combined.scl(Constants.PPM));
        player.render(delta, false);

        hud.stage.draw();

        app.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);

        CameraStyles.lerpToTarget(app.camera, player.getPlayerBody().getPosition().scl(Constants.PPM));
        float startX = app.camera.viewportWidth / 2;
        float startY = app.camera.viewportHeight / 2;
        CameraStyles.camBoundry(app.camera, startX, startY, levelWidth * Constants.PPM - startX * 2, levelHeight * Constants.PPM - startY * 2);

        tmr.setView(app.camera);
        player.update(delta);

        //Scales the Background and Sprites and
        app.batch.setProjectionMatrix(app.camera.combined);

        hud.update(delta);
        backgroundStage.update(delta);

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
        world.dispose();
        b2dr.dispose();
        stage.dispose();
        hud.dispose();
        player.dispose();
        background.dispose();
    }
}
