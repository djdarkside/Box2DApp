package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by djdarkside on 10/19/2016.
 */
public class WorldUtils {

    public static World createWorld() {
        return new World(new Vector2(0, Constants.GRAVITY), true);
    }

    public static Body createBox(World world, int xPos, int yPos, int width, int height, boolean isStatic, boolean fixed) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(xPos / Constants.PPM, yPos / Constants.PPM);
        def.allowSleep = false;

        if (fixed) {
            def.fixedRotation = true;
        } else {
            def.fixedRotation = false;
        }

        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public static Body createCircle(World world, float xPos, float yPos, float radius, boolean isStatic, boolean fixed,
                                    float linearDamping) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.position.set(xPos / Constants.PPM, yPos / Constants.PPM);

        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.linearDamping = linearDamping;

        if (fixed) {
            def.fixedRotation = true;
        } else {
            def.fixedRotation = false;
        }

        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 1.0f;

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
}
