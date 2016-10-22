package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.B2DShapeGenerator;
import com.djdarkside.box2dapp.utils.CameraStyles;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.TiledObjectUtil;

/**
 * Created by djdarkside on 10/20/2016.
 */
public class PlayStage implements Screen {

    private final Application app;

    private World world;
    private Body player;
    private Box2DDebugRenderer b2dr;
    private TextureRegion region, bkgReg;
    private Hud hud;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Stage stage;

    public int levelWidth = 0;
    public int levelHeight = 0;

    public PlayStage(Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT));
        hud = new Hud(app.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        region = new TextureRegion(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        region.setRegion(0, 0, region.getRegionWidth(), region.getRegionHeight());
        bkgReg = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND, Texture.class));
        bkgReg.setRegion(0, 0, bkgReg.getRegionWidth(), bkgReg.getRegionHeight());

        world = new World(new Vector2(0, Constants.gravity), false);
        b2dr = new Box2DDebugRenderer();

        player = B2DShapeGenerator.createBox(world, 140, 140, 32, 32, false);

        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map);

        MapProperties props = map.getProperties();
        levelWidth = props.get("width", Integer.class);
        levelHeight = props.get("height", Integer.class);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        app.batch.begin();
        app.batch.draw(bkgReg, 0, 0, bkgReg.getRegionWidth(), bkgReg.getRegionHeight());
        app.batch.end();

        tmr.render();
        //b2dr.render(world, app.camera.combined.scl(Constants.PPM));

        app.batch.begin();
        app.batch.draw(region, (player.getPosition().x * Constants.PPM) - (region.getRegionWidth() / 2),
                (player.getPosition().y * Constants.PPM) - (region.getRegionHeight() / 2));
        app.batch.end();

        app.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);
        inputUpdate(delta);

        CameraStyles.lerpToTarget(app.camera, player.getPosition().scl(Constants.PPM));
        float startX = app.camera.viewportWidth / 2;
        float startY = app.camera.viewportHeight / 2;
        CameraStyles.camBoundry(app.camera, startX, startY, levelWidth * Constants.PPM - startX * 2, levelHeight * Constants.PPM - startY * 2);

        tmr.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);

        hud.update(delta);

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
        tmr.dispose();
        map.dispose();
        stage.dispose();
        hud.dispose();
    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0, 300, false);
        }
        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
    }

    public Hud getHud() {
        return hud;
    }
}
