package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    public enum playerState {FALLING, JUMPING, STANDING, WALKING, DEAD}
    public playerState currentState;

    private final Application app;
    private World world;
    private Body playerBody;
    private Sprite playerSprite;
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

    public Player(final Application app, World world) {
        this.app = app;
        this.world = world;
        playerSprite = new Sprite(app.manager.get(LoadingScreen.PLAYER, Texture.class));
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
    }
    private void initBody() {
        playerBody = WorldUtils.createBox(world, 140, 140, 18, 30, false, true, 1.0f);
        playerBody.setUserData(playerSprite);
    }
    public void update(float delta) {
        inputUpdate(delta);
        //System.out.println(currentState);
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
        System.out.println(currentState);

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
        System.out.println(index);
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
