package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by djdarkside on 11/13/2016.
 */
public class GameOver implements Screen {

    private final Application app;
    private Stage stage;
    private Skin skin;
    private TextButton tryAgain, exit;

    public GameOver(final Application app) {
        this.app = app;
        skin = new Skin();
        stage = new Stage(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT));
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
        tryAgain = new TextButton("Try Again?", skin, "default");
        tryAgain.setPosition(Constants.V_WIDTH / 2 - 140, 260);
        tryAgain.setSize(280, 60);
        tryAgain.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        tryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.tStage2);
            }
        });

        exit = new TextButton("Main Menu", skin, "default");
        exit.setPosition(Constants.V_WIDTH / 2 - 140, 190);
        exit.setSize(280, 60);
        exit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mStage);
            }
        });

        stage.addActor(tryAgain);
        stage.addActor(exit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        app.batch.begin();
        app.font.draw(app.batch, "GAME OVER", Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2);
        app.batch.end();
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
