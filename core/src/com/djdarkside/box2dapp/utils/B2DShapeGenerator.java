package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by djdarkside on 10/19/2016.
 */
public class B2DShapeGenerator {

    public static Body createBox(World world, int xPos, int yPos, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(xPos / Constants.PPM, yPos / Constants.PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
}
