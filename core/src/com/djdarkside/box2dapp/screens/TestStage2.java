package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.entities.Player2;
import com.djdarkside.box2dapp.entities.Player3;
import com.djdarkside.box2dapp.utils.*;

/**
 * Created by djdarkside on 10/25/2016.
 */
public class TestStage2 implements Screen {

    private final Application app;
    private Player3 player3;
    private World world;

    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCam;
    private Hud hud;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    public int levelWidth = 0;
    public int levelHeight = 0;

    public static float xPos;
    private ParallaxBackground rbg;
    private TextureAtlas atlas;
    private TextureRegion bkg;

    //layered bg
    private TextureRegion bg5a;
    private TextureRegion bg5b;
    private TextureRegion bg5c;
    private TextureRegion bg5d;
    private TextureRegion bg5e;
    private TextureRegion bg5f;
    private TextureRegion bg5g;

    public TestStage2(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
        hud = new Hud(app.batch);
        b2dCam = new OrthographicCamera();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        world = WorldUtils.createWorld();
        world.setContactListener(app.contact);

        b2dr = new Box2DDebugRenderer();

        player3 = new Player3(app, world);

        map = app.manager.get(LoadingScreen.MAP);
        tmr = new OrthogonalTiledMapRenderer(map, app.batch);

        MapProperties props = map.getProperties();
        levelWidth = props.get("width", Integer.class);
        levelHeight = props.get("height", Integer.class);

        TiledObjectUtil.buildShapes(map, Constants.PPM, world, "collision-layer");

        //bkg = new TextureRegion(app.manager.get(LoadingScreen.BACKGROUND, Texture.class));
        //bkg.setRegion(bkg, 0, 0, Constants.V_WIDTH, Constants.V_HEIGHT);
        //atlas = app.manager.get(LoadingScreen.BKG_ATLAS, TextureAtlas.class);
        //rbg = new ParallaxBackground(app, new ParallaxLayer[]{
        //        new ParallaxLayer(bkg, new Vector2(.01f,0), new Vector2(0,0), new Vector2()),
        //        new ParallaxLayer(atlas.findRegion("bg"), new Vector2(.01f,0), new Vector2(0,0), new Vector2()),
        //        new ParallaxLayer(atlas.findRegion("cloud"),new Vector2(0.06f,0),new Vector2(0,Constants.V_HEIGHT-450),new Vector2(0, 0)),
        //}, Constants.V_WIDTH, Constants.V_HEIGHT,new Vector2(85,0));
        bg5a = new TextureRegion(app.manager.get(LoadingScreen.BKG5A, Texture.class));
        bg5b = new TextureRegion(app.manager.get(LoadingScreen.BKG5B, Texture.class));
        bg5c = new TextureRegion(app.manager.get(LoadingScreen.BKG5C, Texture.class));
        bg5d = new TextureRegion(app.manager.get(LoadingScreen.BKG5D, Texture.class));
        bg5e = new TextureRegion(app.manager.get(LoadingScreen.BKG5E, Texture.class));
        bg5f = new TextureRegion(app.manager.get(LoadingScreen.BKG5F, Texture.class));
        bg5g = new TextureRegion(app.manager.get(LoadingScreen.BKG5G, Texture.class));

        rbg = new ParallaxBackground(app, new ParallaxLayer[]{
                new ParallaxLayer(bg5g, new Vector2(.02f,0f), new Vector2(0,0), new Vector2()),
                new ParallaxLayer(bg5f, new Vector2(.04f,0f), new Vector2(0,275), new Vector2(0, 400)),
                new ParallaxLayer(bg5e, new Vector2(.08f,0f), new Vector2(0,0), new Vector2(0, 400)),
                new ParallaxLayer(bg5d, new Vector2(.16f,0), new Vector2(0,0), new Vector2(0, 400)),
                new ParallaxLayer(bg5c, new Vector2(.24f,0), new Vector2(0,0), new Vector2(0, 400)),
                new ParallaxLayer(bg5b, new Vector2(.32f,0), new Vector2(0,0), new Vector2(0, 400)),
                new ParallaxLayer(bg5a, new Vector2(.64f,0), new Vector2(0,0), new Vector2(0, 320)),
        }, Constants.V_WIDTH, Constants.V_HEIGHT,new Vector2(285,0));
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        rbg.render(delta);

        tmr.setView(app.camera);
        tmr.render();

        //b2dCam.setToOrtho(false, Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
        b2dr.render(world, app.camera.combined.scl(Constants.PPM));

        player3.render(delta, true);
        //keys.render(delta, keys.keyBody);

        hud.stage.draw();

        app.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        app.batch.begin();
        app.font.draw(app.batch, "Cam X:" + app.camera.position.x, 24, 564);
        app.font.draw(app.batch, "Cam Y:" + app.camera.position.y, 24, 534);

        app.font.draw(app.batch, "Player State:" + player3.currentState, 24, 434);

        app.font.draw(app.batch, "Box Pos X:" + player3.getPlayerBody().getPosition().x, 24, 334);
        app.font.draw(app.batch, "Box Pos Y:" + player3.getPlayerBody().getPosition().y, 24, 304);

        app.font.draw(app.batch, "Pos X:" + player3.getPlayerBody().getPosition().x * Constants.PPM, 24, 264);
        app.font.draw(app.batch, "Pos Y:" + player3.getPlayerBody().getPosition().y * Constants.PPM, 24, 234);

        app.font.draw(app.batch, "Controller: " + player3.hasControllers, 24, 204);

        app.batch.end();
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);

        if (player3.getPlayerBody().getPosition().y <= 0) {
            stage.clear();
            app.setScreen(app.gOver);
        }

        CameraStyles.lerpToTarget(app.camera, player3.getPlayerBody().getPosition().scl(Constants.PPM));
        float startX = app.camera.viewportWidth / 2;
        float startY = app.camera.viewportHeight / 2;
        CameraStyles.camBoundry(app.camera, startX, startY, levelWidth * Constants.PPM - startX * 2, levelHeight * Constants.PPM - startY * 2);

        tmr.setView(app.camera);
        player3.update(delta);

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
        stage.dispose();
        hud.dispose();
        player3.dispose();
    }
}
