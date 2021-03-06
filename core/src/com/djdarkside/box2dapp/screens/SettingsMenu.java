package com.djdarkside.box2dapp.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

/**
 * Created by design on 11/18/2016.
 */
public class SettingsMenu implements Screen {

    private final Application app;
    private Stage stage;
    private Skin skin;
    private ShapeRenderer renderer;
    private TextButton buttonController, buttonResolution, buttonBack;

    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkSound;
    private Slider sldSound;
    private CheckBox chkMusic;
    private Slider sldMusic;
    //private SelectBox<CharacterSkin> selCharSkin;
    private Image imgCharSkin;
    private CheckBox chkShowFpsCounter;

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
        buttonBack = new TextButton("Back", skin, "default");
        buttonBack.setPosition(Constants.V_WIDTH / 2 - 140, 120);
        buttonBack.setSize(280, 60);
        buttonBack.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mStage);
            }
        });

        stage.addActor(buttonBack);
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        generateMenus(app.batch, app.font);

        update(delta);
        stage.draw();
    }

    public void generateMenus(SpriteBatch batch, BitmapFont font) {
        font.setColor(Color.WHITE);
        batch.begin();
        font.draw(app.batch, "RESOLUTION: ", Constants.V_WIDTH / 2 - 224, 494);
        font.draw(app.batch, "CONTROLLER: ", Constants.V_WIDTH / 2 - 224, 464);
        font.draw(app.batch, "SOUND VOLUME: ", Constants.V_WIDTH / 2 - 224, 434);
        font.draw(app.batch, "MUSIC VOLUME: ", Constants.V_WIDTH / 2 - 224, 404);
        batch.end();
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
