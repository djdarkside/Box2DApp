package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.input.XBox360Pad;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.screens.TestStage;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.WorldUtils;
import com.sun.media.jfxmedia.events.PlayerStateEvent;


/**
 * Created by djdarkside on 10/22/2016.
 */
public class Player {

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
    public Animation walkAnimation;
    public TextureRegion[] walkFrames;
    public TextureRegion currentFrame;
    public float stateTime;
    public Texture walkSheet;
    public Animation[] anim;
    public static int index = 2;
    public boolean hasControllers = true;
    public boolean movingRight = false;
    public boolean movingLeft = false;

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        //Controllers.addListener(this);
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
            anim[i] = new Animation(.5f, walkFrames);// #11
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
        playerBody = WorldUtils.createBox(world, 140, 140, 18, 30, false, true, 1.0f);
        playerBody.setUserData(playerSprite);
    }
    public void update(float delta) {
        inputUpdate(delta);
        Controllers.addListener(new ControllerListener() {

            @Override
            public void connected(Controller controller) {

            }

            @Override
            public void disconnected(Controller controller) {

            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                System.out.println(controller.getAxis(XBox360Pad.AXIS_LX));
                Boolean isWalking = false;
                float horizontalForce = 0f;
                if (controller.getAxis(XBox360Pad.AXIS_LX) > 0.3f || controller.getAxis(XBox360Pad.AXIS_LX) < -0.3f) {
                    isWalking = true;
                    if (isWalking) {
                        horizontalForce += (controller.getAxis(XBox360Pad.AXIS_LX));
                    }
                    playerBody.setLinearVelocity(horizontalForce * 5f, playerBody.getLinearVelocity().y);
                }
                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
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

    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        currentState = playerState.STANDING;
        //System.out.println(currentState);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
            currentState = playerState.WALKING;
            index = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
            currentState = playerState.WALKING;
            index = 2;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState != playerState.JUMPING) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
            playerBody.applyForceToCenter(0, 300, true);
            currentState = playerState.JUMPING;
        }
        playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            app.camera.zoom += .1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            app.camera.zoom -= .1;
        }
        //System.out.println(index);
    }

/*
    @Override
    public void connected(Controller controller) {
        hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
        hasControllers = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        int horizontalForce = 0;
        if(buttonCode == XBox360Pad.BUTTON_A) {
            playerBody.applyForceToCenter(0, 300, true);
        }
        playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        //System.out.println(controller.getAxis(XBox360Pad.AXIS_LX));
        Boolean isWalking;
        float horizontalForce = 0f;
        if(controller.getAxis(XBox360Pad.AXIS_LX) > 0.3f  || controller.getAxis(XBox360Pad.AXIS_LX) < -0.3f) {
            isWalking = true;
            if (isWalking) {
                horizontalForce = (controller.getAxis(XBox360Pad.AXIS_LX));
                System.out.println(isWalking);
            }
        }
        playerBody.setLinearVelocity(horizontalForce * 5f, playerBody.getLinearVelocity().y);
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
*/
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
