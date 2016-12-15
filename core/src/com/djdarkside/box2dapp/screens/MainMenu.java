package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by design on 10/21/2016.
 */
public class MainMenu implements Screen {

    private final Application app;
    private Stage stage;
    private Skin skin;
    private ShapeRenderer renderer;
    private TextButton buttonPlay, buttonExit, buttonSettings;

    public MainMenu(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT));
        renderer = new ShapeRenderer();
        skin = new Skin();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        skin.addRegions(app.manager.get(LoadingScreen.UISKINATLAS, TextureAtlas.class));
        skin.add("default-font", app.font);
        skin.load(Gdx.files.internal(LoadingScreen.UISKINJSON));
        initButtons();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.batch.begin();
        app.font.draw(app.batch, "Game...", Constants.V_WIDTH / 2 - 24, 564);
        app.batch.end();

        update(delta);

        stage.draw();
    }

    private void initButtons() {
        buttonPlay = new TextButton("Play", skin, "default");
        buttonPlay.setPosition(Constants.V_WIDTH / 2 - 140, 260);
        buttonPlay.setSize(280, 60);
        buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.tStage3);
            }
        });

        buttonSettings = new TextButton("Settings", skin, "default");
        buttonSettings.setPosition(Constants.V_WIDTH / 2 - 140, 190);
        buttonSettings.setSize(280, 60);
        buttonSettings.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.sMenu);
            }
        });

        buttonExit = new TextButton("Exit", skin, "default");
        buttonExit.setPosition(Constants.V_WIDTH / 2 - 140, 120);
        buttonExit.setSize(280, 60);
        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonSettings);
        stage.addActor(buttonExit);
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
        renderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
