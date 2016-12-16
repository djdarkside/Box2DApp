package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.djdarkside.box2dapp.entities.Player3;

import java.util.Iterator;

/**
 * Created by djdarkside on 10/17/2016.
 */
public class TiledObjectUtil {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float PPM = 0;
    public static int spawnPointX, spawnPointY;

    public static Array<Body> buildShapes(Map map, float pixels, World world, String mapLayer) {
        PPM = pixels;
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

            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.FLOOR_BIT;
            //fdef.filter.maskBits = Constants.PLAYER_BIT;
            fdef.density = 1;
            body.createFixture(fdef).setUserData("GROUND");
            bodies.add(body);
            shape.dispose();
/*
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1.0f);
            bodies.add(body);
            shape.dispose();
*/
        }
        return bodies;
    }
    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / PPM,
                (rectangle.y + rectangle.height * 0.5f ) / PPM);
        polygon.setAsBox(rectangle.width * 0.5f / PPM,
                rectangle.height * 0.5f / PPM,
                size,
                0.0f);
        return polygon;
    }
    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / PPM);
        circleShape.setPosition(new Vector2(circle.x / PPM, circle.y / PPM));
        return circleShape;
    }
    private static CircleShape getEllipse(EllipseMapObject ellipseObject) {
        Ellipse ellipse = ellipseObject.getEllipse();
        CircleShape circleShape = new CircleShape();
        //Needed to find Radius of an ellyspe (c = 2pi R squared)
        circleShape.setRadius((ellipse.circumference() / (2 * MathUtils.PI)) / PPM);
        circleShape.setPosition(new Vector2(ellipse.x / PPM, ellipse.y / PPM));
        return circleShape;
    }
    public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }
    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    public static void setPlayerSpawn(Map map) {
        boolean hasPlayer = false;
        MapObjects objects = map.getLayers().get("player-spawn").getObjects();
        Iterator<MapObject> objectIterator = objects.iterator();

        while (objectIterator.hasNext()) {
            MapObject object = objectIterator.next();

            if (object.getProperties().get("toSpawn", String.class).equalsIgnoreCase("Player")) {
                hasPlayer = true;
                spawnPointX = object.getProperties().get("x", float.class).intValue();
                spawnPointY = object.getProperties().get("y", float.class).intValue();
            }
        }

        if (!hasPlayer) {
            Gdx.app.log("Error", "Player spawn point is undefined in the level");
        }
        System.out.println(spawnPointX);
        System.out.println(spawnPointY);
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
