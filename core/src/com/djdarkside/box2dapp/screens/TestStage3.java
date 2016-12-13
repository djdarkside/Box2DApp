package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;
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

    public TestStage3(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
    }


    @Override
    public void show() {
        world = WorldUtils.createWorld();
        b2dr = new Box2DDebugRenderer();
        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map, app.batch);
    }

    @Override
    public void render(float delta) {

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
