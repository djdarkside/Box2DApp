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
import com.djdarkside.box2dapp.screens.TestStage;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.WorldUtils;
import com.sun.media.jfxmedia.events.PlayerStateEvent;


/**
 * Created by djdarkside on 10/22/2016.
 */
public class Player {

    private final Application app;
    public float x, y;
    public TextureRegion region;
    public Vector2 position;
    public World world;
    public Body playerBody;
    private Box2DDebugRenderer b2dr;

    public enum playerState {
        FALLING, JUMPING, STANDING, WALKING, DEAD
    }
    public playerState currentState;
    public playerState previousState;

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        this.position = new Vector2(x, y);
        initBody();
    }

    public void loadTexture() {

    }

    public Vector2 setPosition(float x, float y) {
        position.x = x * Constants.PPM;
        position.y = y * Constants.PPM;
        return position;
    }

    private Body initBody() {
        playerBody = WorldUtils.createBox(world, 140, 140, 16, 32, false, true, 2.0f);
        return playerBody;
    }

    public void update(float delta) {
        inputUpdate(delta);
        System.out.println(currentState);
    }

    public void render(float delta) {

    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        currentState = playerState.STANDING;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
            currentState = playerState.WALKING;
            TestStage.xPos += .1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
            currentState = playerState.WALKING;
            TestStage.xPos -= .1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState != playerState.JUMPING) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
            playerBody.applyForceToCenter(0, 300, true);
            currentState = playerState.JUMPING;
        }

        playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

    public playerState getState() {
        return currentState;
    }

    public void Jump() {

    }

}
