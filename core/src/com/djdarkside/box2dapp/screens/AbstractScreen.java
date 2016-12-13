package com.djdarkside.box2dapp.screens;

import com.badlogic.gdx.Screen;
import com.djdarkside.box2dapp.Application;

/**
 * Created by design on 12/12/2016.
 */
public abstract class AbstractScreen implements Screen {
    protected Application app;

    public AbstractScreen(Application app) {
        this.app = app;
    }

    public abstract void render (float deltaTime);
    public abstract void resize (int width, int height);
    public abstract void show ();
    public abstract void hide ();
    public abstract void pause ();
    //public void resume () {
    //    Assets.instance.init(new AssetManager());
    //}
    //public void dispose () {
    //    Assets.instance.dispose();
    //}
}
