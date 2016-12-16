package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.TiledObjectUtil;
import com.djdarkside.box2dapp.utils.WorldUtils;

/**
 * Created by design on 11/1/2016.
 */
public class Key {

    private final Application app;
    private World world;
    private Sprite keySprite;
    public String keyColor;
    public Body keyBody;
    private Map map;
    private Array<Body> tempBodies = new Array<Body>();

    public Key(final Application app, World world, String keyColor, Map map) {
        this.app = app;
        this.world = world;
        this.keyColor = keyColor;
        this.map = map;
        if (keyColor == "Yellow") {
            keySprite = new Sprite(app.manager.get(LoadingScreen.KEY, Texture.class));
        }
        initBody();
        initSprite();
    }

    private void initSprite() {
        keySprite.setSize((keySprite.getWidth() / 2) / Constants.PPM, (keySprite.getHeight() / 2) / Constants.PPM);
        keySprite.setOrigin(keySprite.getWidth() / 2, keySprite.getHeight() / 2);
    }

    private void initBody() {
        keyBody = WorldUtils.createKey(world, TiledObjectUtil.keySpawnPointX, TiledObjectUtil.keySpawnPointY, 10, 10, false, true);
        keyBody.setUserData(keySprite);
    }

    public void render(float delta, Body keyBody) {
        this.keyBody = keyBody;
        app.batch.setProjectionMatrix(app.camera.combined.scl(Constants.PPM));
        app.batch.begin();
        world.getBodies(tempBodies);
        for(Body body : tempBodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(app.batch);
            }
        }
        app.batch.end();
    }

    public void dispose() {
        world.dispose();
    }
}
