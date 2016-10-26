package com.djdarkside.box2dapp.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;

/**
 * Created by djdarkside on 10/25/2016.
 */
public class Player2 extends Sprite {

    private final Application app;
    public Sprite sprite;
    public Texture tex;

    public Player2(final Application app) {
        this.app = app;
        tex = app.manager.get(LoadingScreen.PLAYER);
        sprite = new Sprite(tex);
    }

    public void render(float delta) {

    }

    public void update(float delta) {

    }

    public void dispose() {
        tex.dispose();
    }

}
