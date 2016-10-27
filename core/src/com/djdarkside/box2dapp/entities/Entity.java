package com.djdarkside.box2dapp.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;

/**
 * Created by djdarkside on 10/25/2016.
 */
public abstract class Entity {

    protected final Application app;
    protected Sprite sprite;
    protected Texture tex;
    protected TextureRegion region;
    protected World world;
    private Body body;

    public Entity(final Application app) {
        this.app = app;
        tex = app.manager.get(LoadingScreen.PLAYER);
        sprite = new Sprite(tex);
    }

    abstract void render(float delta);

    abstract void update(float delta);

    abstract void dispose();

}
