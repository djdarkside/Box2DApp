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

    public enum playerState {
        FALLING, JUMPING, STANDING, RUNNING, DEAD
    }
    public playerState currentState;
    public playerState previousState;


    private final Application app;
    public float x, y;
    public Texture tex;
    public TextureRegion region;

    public Vector2 position;

    public World world;
    public Body body;


    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        position = new Vector2(x, y);
        initBody();
        region = new TextureRegion(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        region.setRegion(0, 0, region.getRegionWidth(), region.getRegionHeight());
    }


    private Body initBody() {
        body = new Body(app, world);
        WorldUtils.createBox(world, 140, 140, 32, 32, false, true);
    }

    public void update(float delta) {
        //set input
    }

    public void render(float delta) {
        update(delta);
        app.batch.begin();
        app.batch.draw(region, (player.getPosition().x * Constants.PPM) - (region.getRegionWidth() / 2),
                (player.getPosition().y * Constants.PPM) - (region.getRegionHeight() / 2));
        app.batch.end();
    }


    public Vector2 getPosition() {
        position.x = x;
        position.y = y;
        return position;
    }

}
