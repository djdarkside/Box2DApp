package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by djdarkside on 10/17/2016.
 */
public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects objects) {
        for (MapObject object: objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }
            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    // This method graps all polylines connected and makes them 1 long line..hence chainshape //
    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticies = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVerticies.length; i++) {
            worldVerticies[i] = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticies);
        return cs;
    }
}
