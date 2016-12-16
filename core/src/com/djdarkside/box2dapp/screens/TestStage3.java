package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.entities.Player3;
import com.djdarkside.box2dapp.input.MyInputProcessor;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.TiledObjectUtil;
import com.djdarkside.box2dapp.utils.WorldUtils;

/**
 * Created by design on 12/12/2016.
 */
public class TestStage3 implements Screen {

    private final Application app;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Player3 player;
    private MyInputProcessor input;


    public TestStage3(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
    }


    @Override
    public void show() {
        world = WorldUtils.createWorld();
        player = new Player3(app, world);
        input = new MyInputProcessor(app, player.getPlayerBody(), stage);
        b2dr = new Box2DDebugRenderer();
        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map, app.batch);
        TiledObjectUtil.buildShapes(map, Constants.PPM, world, "collision-layer");
        TiledObjectUtil.setPlayerSpawn(map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        //b2dr.render(world, app.camera.combined);
        tmr.setView(app.camera);
        tmr.render();
        player.render(delta, true);
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);
        input.update(delta);
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
        player.dispose();
        b2dr.dispose();
        map.dispose();
    }
}
