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
public class Entity {

    protected final Application app;
    protected Sprite sprite;
    protected World world;
    protected Body body;
    protected enum state{FALLING, JUMPING, STANDING, WALKING, DEAD, ATTACKING, DEFENDING, HIT}

    public Entity(final Application app, World world) {
        this.app = app;
        this.world = world;
    }

    public void render(float delta) {}

    public void update(float delta) {}

    public void dispose() {}

}
