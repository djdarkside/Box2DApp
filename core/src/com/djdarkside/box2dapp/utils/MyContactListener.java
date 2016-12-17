package com.djdarkside.box2dapp.utils;


import com.badlogic.gdx.physics.box2d.*;
import com.djdarkside.box2dapp.screens.Hud;

/**
 * Created by djdarkside on 11/15/2016.
 */
public class MyContactListener implements ContactListener {

    private boolean playerOnGround = false;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null && fa.getUserData().equals("Feet")) {
            playerOnGround = true;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("Feet")) {
            playerOnGround = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null && fa.getUserData().equals("Feet")) {
            playerOnGround = false;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("Feet")) {
            playerOnGround = false;
        }
        //if (fa.getUserData() != null && fa.getUserData().equals("Keys") || fb.getUserData() != null && fb.getUserData().equals("Keys")) {
        //    Hud.addKey(1);
        //}
    }

    public boolean isPlayerOnGround() {
        return playerOnGround;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
