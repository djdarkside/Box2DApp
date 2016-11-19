package com.djdarkside.box2dapp.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;

/**
 * Created by design on 11/18/2016.
 */
public class SettingsMenu implements Screen {

    private final Application app;
    private Stage stage;
    private Skin skin;
    private ShapeRenderer renderer;
    private TextButton buttonController, buttonResolution, buttonBack;

    public SettingsMenu(final Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT));
        skin = new Skin();
        renderer = new ShapeRenderer();
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

    public void initButtons() {
        //Make Buttons
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.batch.begin();

        //render stuff

        app.batch.end();

        update(delta);
        stage.draw();
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
        stage.dispose();
        skin.dispose();

    }
}
