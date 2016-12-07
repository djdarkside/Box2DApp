package com.djdarkside.box2dapp.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.utils.Constants;

/**
 * Created by djdarkside on 12/4/2016.
 */
public class NPC {

    protected final Application app;
    protected World world;
    protected Body body;
    protected Texture texture;
    protected Vector2 position;

    protected Array<Body> tempBodies = new Array<Body>();
    protected float stateTime;
    protected Texture walkSheet;
    protected Animation[] anim;
    protected int index = 2;
    protected TextureRegion[] walkFrames;
    protected TextureRegion currentFrame;

    public NPC(final Application app, World world) {
        this.app = app;
        this.world = world;
        world.setContactListener(app.contact);
    }

    public void render(float delta, Boolean isAnimated) {
        if (isAnimated) renderAnimation(index, delta);
        else {
            app.batch.setProjectionMatrix(app.camera.combined.scl(Constants.PPM));
            app.batch.begin();
            world.getBodies(tempBodies);
            for(Body body : tempBodies) {
                if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                    Sprite sprite = (Sprite) body.getUserData();
                    sprite.setPosition((body.getPosition().x - sprite.getWidth() / 2), body.getPosition().y - sprite.getHeight() / 2);
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                    sprite.draw(app.batch);
                }
            }
            app.batch.end();
        }
    }

    public void createAnimation(int frameCols, int frameRows) {
        walkSheet = app.manager.get(LoadingScreen.PLAYERSHEET, Texture.class);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / frameCols, walkSheet.getHeight() / frameRows);              // #10
        anim = new Animation[frameRows];
        for (int i = 0; i < frameRows; i++) {
            walkFrames = new TextureRegion[frameCols];
            for (int j = 0; j < frameCols; j++) {
                walkFrames[j] = tmp[i][j];
            }
            anim[i] = new Animation(.5f, walkFrames);
        }
        stateTime = 0f;
    }
    public void renderAnimation(int index, float delta) {
        this.index = index;
        app.batch.setProjectionMatrix(app.camera.combined);
        stateTime += delta;
        currentFrame = anim[index].getKeyFrame(stateTime, true);
        app.batch.begin();
        app.batch.draw(currentFrame, (body.getPosition().x * Constants.PPM) - (currentFrame.getRegionWidth() / 2),
                (body.getPosition().y * Constants.PPM) - (currentFrame.getRegionHeight() / 2));
        app.batch.end();
    }



    public void setPosition() {

    }

    public void update() {

    }

    public Vector2 getPosition() {
        return position;
    }

    public void DialogText() {

    }

    public void dispose() {
        world.dispose();
    }

    public Body getBody() {
        return body;
    }


}
