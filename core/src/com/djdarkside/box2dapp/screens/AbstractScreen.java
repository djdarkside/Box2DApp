package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;

/**
 * Created by design on 12/12/2016.
 */
public abstract class AbstractScreen implements Screen {
    protected Application app;
    protected Stage stage;

    public AbstractScreen(Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE));
    }
    public abstract void render (float delta);
    @Override
    public void resize (int width, int height) {
        stage.setViewport(new FitViewport(width * Constants.SCALE, height * Constants.SCALE));
    }
    public abstract void show ();
    public abstract void hide ();
    public abstract void pause ();
    public abstract void resume ();

    @Override
    public void dispose () {
        stage.dispose();
    }


}
