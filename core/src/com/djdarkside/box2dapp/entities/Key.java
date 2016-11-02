package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;

/**
 * Created by design on 11/1/2016.
 */
public class Key {

    private final Application app;
    private World world;
    private Sprite keySprite;
    public String keyColor;

    public Key(final Application app, World world, String keyColor) {
        this.app = app;
        this.world = world;
        this.keyColor = keyColor;
        if (keyColor == "Yellow") {
            keySprite = new Sprite(app.manager.get(LoadingScreen.KEY, Texture.class));
        }
        initBody();
        initSprite();
    }

    private void initSprite() {

    }

    private void initBody() {

    }


}
