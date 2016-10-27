package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.entities.Player;
import com.djdarkside.box2dapp.utils.CameraStyles;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.TiledObjectUtil;
import com.djdarkside.box2dapp.utils.WorldUtils;

/**
 * Created by djdarkside on 10/25/2016.
 */
public class TestStage implements Screen, ControllerListener {

    private final Application app;
    private Player player;
    private World world;

    private Box2DDebugRenderer b2dr;
    private TextureRegion bkgReg;
    private Hud hud;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    public int levelWidth = 0;
    public int levelHeight = 0;

    public static float xPos = 0;

    public TestStage(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
        hud = new Hud(app.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        bkgReg = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND, Texture.class));
        bkgReg.setRegion(0, 0, bkgReg.getRegionWidth(), bkgReg.getRegionHeight());

        world = WorldUtils.createWorld();
        b2dr = new Box2DDebugRenderer();

        player = new Player(app, world);

        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map);

        MapProperties props = map.getProperties();
        levelWidth = props.get("width", Integer.class);
        levelHeight = props.get("height", Integer.class);
        //TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
        TiledObjectUtil.buildShapes(map, Constants.PPM, world);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        app.batch.begin();
        app.batch.draw(bkgReg, xPos, 0, bkgReg.getRegionWidth(), bkgReg.getRegionHeight());
        app.batch.draw(bkgReg, xPos, 0, bkgReg.getRegionWidth() + bkgReg.getRegionWidth(), bkgReg.getRegionHeight());
        app.batch.end();

        tmr.render();
        b2dr.render(world, app.camera.combined.scl(Constants.PPM));

        player.render(delta);
        //app.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);

        CameraStyles.lerpToTarget(app.camera, player.getPlayerBody().getPosition().scl(Constants.PPM));
        float startX = app.camera.viewportWidth / 2;
        float startY = app.camera.viewportHeight / 2;
        CameraStyles.camBoundry(app.camera, startX, startY, levelWidth * Constants.PPM - startX * 2, levelHeight * Constants.PPM - startY * 2);

        tmr.setView(app.camera);

        //Scales the BAckground and Sprites
        app.batch.setProjectionMatrix(app.camera.combined);

        hud.update(delta);
        player.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            app.camera.zoom += .1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            app.camera.zoom -= .1;
        }

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
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
