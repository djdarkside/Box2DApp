package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by djdarkside on 10/17/2016.
 */
public class TiledObjectUtil {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 0;

    public static Array<Body> buildShapes(Map map, float pixels, World world, String mapLayer) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get(mapLayer).getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)object);
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else if (object instanceof EllipseMapObject) {
                shape = getEllipse((EllipseMapObject)object);
            }
            else {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            // Added by me for contact bits
            FixtureDef fdef = new FixtureDef();
            fdef.filter.categoryBits = Constants.FLOOR_BIT;
            fdef.density = 1;
            body.createFixture(fdef);
            ///
            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                rectangle.height * 0.5f / ppt,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }
    private static CircleShape getEllipse(EllipseMapObject ellipseObject) {
        Ellipse ellipse = ellipseObject.getEllipse();
        CircleShape circleShape = new CircleShape();
        //Needed to find Radius of an ellyspe (c = 2pi R squared)
        circleShape.setRadius((ellipse.circumference() / (2 * MathUtils.PI)) / ppt);
        circleShape.setPosition(new Vector2(ellipse.x / ppt, ellipse.y / ppt));
        return circleShape;
    }

    public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    ///////////////////////////////////////////////////////MY STUFF
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

    // This method grabs all polylines connected and makes them 1 long line..hence chainshape //
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
