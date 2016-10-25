package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.WorldUtils;


/**
 * Created by djdarkside on 10/22/2016.
 */
public class Player {
/*
    public enum playerState {
        FALLING, JUMPING, STANDING, RUNNING, DEAD
    }
    public playerState currentState;
    public playerState previousState;
*/

    private final Application app;
    public float x, y;
    public TextureRegion region;
    public Vector2 position;
    public World world;
    public Body playerBody;
    public Texture tex;

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        this.position = new Vector2(x, y);
        world = WorldUtils.createWorld();
        initBody(world);
        loadTexture();
    }

    public void loadTexture() {
        System.out.println("LOADING");
        System.out.println("1");
        region = new TextureRegion(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        System.out.println("2");
        region.setRegion(0, 0, region.getRegionWidth(), region.getRegionHeight());
        System.out.println("EXIT LOADING");
    }


    private void initBody(World world) {
        playerBody = WorldUtils.createBox(world, 140, 140, 32, 32, false, true);
    }

    public void update(float delta) {
        //set input
    }

    public void render(float delta) {
        update(delta);
        app.batch.begin();
        app.batch.draw(region, (position.x * Constants.PPM) - (region.getRegionWidth() / 2),
                (position.y * Constants.PPM) - (region.getRegionHeight() / 2));
        app.batch.end();
    }


    public Vector2 getPosition() {
        return position;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

}
