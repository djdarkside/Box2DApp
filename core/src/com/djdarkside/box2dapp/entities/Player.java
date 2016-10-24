package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.djdarkside.box2dapp.Application;


/**
 * Created by djdarkside on 10/22/2016.
 */
public class Player {

    private final Application app;
    public enum playerState {
        FALLING, JUMPING, STANDING, RUNNING, DEAD
    }
    public playerState currentState;
    public playerState previousState;

    public World world;
    public Body body;

    private TextureRegion region;

    public Player(Application app, TextureRegion region) {
        this.app = app;
    }

    public void update(float delta) {
        //set input
    }

    public void render(float delta) {
        update(delta);
    }

}
