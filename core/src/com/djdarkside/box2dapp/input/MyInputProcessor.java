package com.djdarkside.box2dapp.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.entities.Player;
import com.djdarkside.box2dapp.entities.Player3;

/**
 * Created by design on 11/16/2016.
 */
public class MyInputProcessor implements ControllerListener, InputProcessor {

    private final Application app;
    private Body body;
    private Stage stage;
    private Player3 player;

    public MyInputProcessor(final Application app, Body body, Stage stage) {
        this.app = app;
        this.body = body;
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
    }

    public void updateMotion(float delta) {
        float horizontalForce = 0;
        player.currentState = Player3.playerState.STANDING;
        if (player.movingLeft) {
            horizontalForce -= 1;
            player.currentState = Player3.playerState.WALKING;
            player.index = 1;
        }
        if (player.movingRight) {
            horizontalForce += 1;
            player.currentState = Player3.playerState.WALKING;
            player.index = 2;
        }
        player.getPlayerBody().setLinearVelocity(horizontalForce * 5f, player.getPlayerBody().getLinearVelocity().y);
    }

    public void setLeftMove(boolean t) {
        if(player.movingRight && t) player.movingRight = false;
        player.movingLeft = t;
    }
    public void setRightMove(boolean t) {
        if(player.movingLeft && t) player.movingLeft = false;
        player.movingRight = t;
    }

    public void jump(float delta) {
        //if (isJumping == true) {
        if (app.contact.isPlayerOnGround()) {
            player.getPlayerBody().setLinearVelocity(player.getPlayerBody().getLinearVelocity().x, 0);
            player.getPlayerBody().applyForceToCenter(0, 320f, true);
            player.currentState = Player3.playerState.JUMPING;
        }
        //}
        //isJumping = false;
    }

    public void update(float delta) {
        updateMotion(delta);
    }

    ////////////////////////Input
    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            setLeftMove(true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

//////Controller
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
}
