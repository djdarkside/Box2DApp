package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera cam;

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        this.position = new Vector2(x, y);
        b2dr = new Box2DDebugRenderer();
        world = WorldUtils.createWorld();
        initBody(world);
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
        inputUpdate(delta);
    }

    public void render(float delta, World world, OrthographicCamera cam) {
        this.cam = cam;
        cam = app.camera;
        update(delta);
        b2dr.render(world, app.camera.combined.scl(Constants.PPM));
    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            playerBody.applyForceToCenter(0, 300, false);
        }
        playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);
    }


    public Vector2 getPosition() {
        return position;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

}
