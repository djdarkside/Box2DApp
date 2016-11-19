package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;
import com.djdarkside.box2dapp.utils.MyAnimation;

/**
 * Created by djdarkside on 10/25/2016.
 */
public class Entity {

    protected final Application app;
    protected Body body;
    protected MyAnimation animation;
    protected float width, height;
    protected enum state{FALLING, JUMPING, STANDING, WALKING, DEAD, ATTACKING, DEFENDING, HIT}

    public Entity(final Application app, Body body) {
        this.app = app;
        this.body = body;
        animation = new MyAnimation();
    }

    public void setAnimation(TextureRegion region, float delay) {
        setAnimation(new TextureRegion[] {region}, delay);
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    public void render(float delta) {
        app.batch.begin();
        app.batch.draw(animation.getFrame(), (body.getPosition().x * Constants.PPM - width / 2), (int) (body.getPosition().y * Constants.PPM - height / 2));
        app.batch.end();
    }

    public void update(float delta) {
        animation.update(delta);
    }

    public Body getBody() { return body; }
    public Vector2 getPosition() { return body.getPosition(); }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

}
