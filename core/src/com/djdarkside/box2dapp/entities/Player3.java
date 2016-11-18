package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.input.XBox360Pad;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.MyContactListener;
import com.djdarkside.box2dapp.utils.WorldUtils;


/**
 * Created by djdarkside on 10/22/2016.
 */
public class Player3 {

    public enum playerState {FALLING, JUMPING, STANDING, WALKING, DEAD, ATTACKING}
    public playerState currentState;

    private final Application app;
    private World world;
    private Body playerBody;
    private Sprite playerSprite;
    private Sprite playerSpriteLeft;
    private Array<Body> tempBodies = new Array<Body>();

    //Animation Stuff
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    public TextureRegion[] walkFrames;
    public TextureRegion currentFrame;
    public float stateTime;
    public Texture walkSheet;
    public Animation[] anim;
    public static int index = 2;

    public boolean hasControllers = true;
    public boolean movingRight = false;
    public boolean movingLeft = false;
    public boolean isJumping = false;

    public Player3(final Application app, World world) {
        this.app = app;
        this.world = world;
        world.setContactListener(app.contact);
        if(Controllers.getControllers().size == 0) hasControllers = false;
        playerSprite = new Sprite(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        playerSpriteLeft = new Sprite(app.manager.get(LoadingScreen.PLAYER, Texture.class));
        playerSpriteLeft.flip(true, false);
        initBody();
        initSprite();
        createAnimation();
    }

    public void createAnimation() {
        walkSheet = app.manager.get(LoadingScreen.PLAYERSHEET, Texture.class);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        anim = new Animation[FRAME_ROWS];
        //int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            walkFrames = new TextureRegion[FRAME_COLS];
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[j] = tmp[i][j];
            }
            //Full Animation
            anim[i] = new Animation(.5f, walkFrames);// #11

            //need logic to make sure standing is pointing the right way..all need to do is change value in walkframes and that the index anim[1] is pointing to the right side
            //if (currentState == playerState.STANDING) {
            //    anim[1] = new Animation(.5f, walkFrames[0]); left
            //    anim[2] = new Animation(.5f, walkFrames[1]); right
            //}
            //
        }
        stateTime = 0f;
        
    }
    public void renderAnimation(int index, float delta) {
        this.index = index;
        app.batch.setProjectionMatrix(app.camera.combined);
        stateTime += delta;
        currentFrame = anim[index].getKeyFrame(stateTime, true);
        app.batch.begin();
        app.batch.draw(currentFrame, (playerBody.getPosition().x * Constants.PPM) - (currentFrame.getRegionWidth() / 2),
                (playerBody.getPosition().y * Constants.PPM) - (currentFrame.getRegionHeight() / 2));
        app.batch.end();
    }
    private void initSprite() {
        playerSprite.setSize(playerSprite.getWidth() / Constants.PPM, playerSprite.getHeight() / Constants.PPM);
        playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        playerSpriteLeft.setSize(playerSpriteLeft.getWidth() / Constants.PPM, playerSpriteLeft.getHeight() / Constants.PPM);
        playerSpriteLeft.setOrigin(playerSpriteLeft.getWidth() / 2, playerSpriteLeft.getHeight() / 2);
    }
    private void initBody() {
        playerBody = WorldUtils.createPlayer(world, 140, 140, 18, 30, false, true, 1.0f, true);
        playerBody.setUserData(playerSprite);
    }

    public void update(float delta) {
        inputUpdate(delta);
        updateMotion(delta);
        
        Controllers.addListener(new ControllerAdapter() {
            float delta = Gdx.graphics.getDeltaTime();
            @Override
            public void connected(Controller controller) {
            }

            @Override
            public void disconnected(Controller controller) {
            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                if (buttonCode == XBox360Pad.BUTTON_A && currentState != playerState.JUMPING && currentState != playerState.FALLING) {
                    //isJumping = true;
                    jump(delta);
                }
                //isJumping = false;
                return false;
            }
            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                if (buttonCode == XBox360Pad.BUTTON_A) {
                    //isJumping = false;
                }
                return false;
            }
            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                if (controller.getAxis(XBox360Pad.AXIS_LX) > 0.3f || controller.getAxis(XBox360Pad.AXIS_LX) < -0.3f) {
                    if (value == -1.0f)
                        setLeftMove(true);
                    if (value == 1.0f) {
                        setRightMove(true);
                    }
                } else {
                    setLeftMove(false);
                    setRightMove(false);
                }
                return false;
            }
            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                return false;
            }
        });
    }

    public void render(float delta, Boolean isAnimated) {
        if (isAnimated && currentState != playerState.STANDING) renderAnimation(index, delta);
        else {
            currentState = playerState.STANDING;
            app.batch.setProjectionMatrix(app.camera.combined.scl(Constants.PPM));
            app.batch.begin();
            world.getBodies(tempBodies);
            for(Body body : tempBodies) {
                if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                    Sprite sprite = (Sprite) body.getUserData();
                    sprite.setPosition((body.getPosition().x - sprite.getWidth() / 2), body.getPosition().y - sprite.getHeight() / 2);
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                    sprite.draw(app.batch);
                }
            }
            app.batch.end();
        }
    }

    public void updateMotion(float delta) {
        float horizontalForce = 0;
        currentState = playerState.STANDING;
        if (movingLeft) {
            horizontalForce -= 1;
            currentState = playerState.WALKING;
            index = 1;
        }
        if (movingRight) {
            horizontalForce += 1;
            currentState = playerState.WALKING;
            index = 2;
        }
        playerBody.setLinearVelocity(horizontalForce * 5f, playerBody.getLinearVelocity().y);
    }

    public void setLeftMove(boolean t) {
        if(movingRight && t) movingRight = false;
        movingLeft = t;
    }
    public void setRightMove(boolean t) {
        if(movingLeft && t) movingLeft = false;
        movingRight = t;
    }

    public void jump(float delta) {
        //if (isJumping == true) {
            if (app.contact.isPlayerOnGround()) {
                playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
                playerBody.applyForceToCenter(0, 200f, true);
                currentState = playerState.JUMPING;
            }
        //}
        //isJumping = false;
    }

    public void inputUpdate(float delta) {
        currentState = playerState.STANDING;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setLeftMove(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setRightMove(true);
        } else {
            setLeftMove(false);
            setRightMove(false);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (currentState != playerState.JUMPING) {
                //isJumping = true;
                jump(delta);
            } else {
                //isJumping = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            app.camera.zoom += .1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            app.camera.zoom -= .1;
        }
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
