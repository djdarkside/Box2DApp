package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
    private World world;
    private Body playerBody;
    private Sprite playerSprite;

    private Array<Body> tempBodies = new Array<Body>();

    public enum playerState {
        FALLING, JUMPING, STANDING, WALKING, DEAD
    }

    public playerState currentState;
    public playerState previousState;

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        playerSprite = new Sprite(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        initBody();
        initSprite();
    }

    private Sprite initSprite() {
        playerSprite.setSize(playerSprite.getWidth() / Constants.PPM, playerSprite.getHeight() / Constants.PPM);
        playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        return playerSprite;
    }

    private Body initBody() {
        playerBody = WorldUtils.createBox(world, 140, 140, 32, 32, false, false, 2.0f);
        playerBody.setUserData(playerSprite);
        return playerBody;
    }

    public void update(float delta) {
        inputUpdate(delta);
        System.out.println(currentState);
    }

    public void render(float delta) {
        app.batch.setProjectionMatrix(app.camera.combined);
        app.batch.begin();
        world.getBodies(tempBodies);
        for(Body playerBody : tempBodies) {
            if (playerBody.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) playerBody.getUserData();
                sprite.setPosition(playerBody.getPosition().x - sprite.getWidth() / 2, playerBody.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(playerBody.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(app.batch);
            }
        }
        app.batch.end();
    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        currentState = playerState.STANDING;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
            currentState = playerState.WALKING;
            //TestStage.xPos += .1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
            currentState = playerState.WALKING;
            //TestStage.xPos -= .1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState != playerState.JUMPING) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
            playerBody.applyForceToCenter(0, 300, true);
            currentState = playerState.JUMPING;
        }
        playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);
    }

    public void dispose() {
        world.dispose();
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }
    public Body getPlayerBody() {
        return playerBody;
    }
    public playerState getPlayerState() {
        return currentState;
    }
}
