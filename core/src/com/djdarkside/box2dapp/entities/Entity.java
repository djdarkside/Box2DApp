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
    protected World world;
    protected Body body;
    protected enum EntityState{FALLING, JUMPING, STANDING, WALKING, DEAD}

    protected Entity(final Application app, World world) {
        this.app = app;
        this.world = world;
    }

    protected void render(float delta) {}

    protected void update(float delta) {}

    protected void dispose() {}

}
