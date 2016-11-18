package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by design on 11/17/2016.
 */
public class MyAnimation {

    private TextureRegion[] frames;
    private float time;
    private float delay;
    private int currentFrame;

    public MyAnimation() {
    }

    public MyAnimation(TextureRegion[] frames) {
        this(frames, 1 / 12f);
    }

    public MyAnimation(TextureRegion[] frames, float delay) {
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
    }

    public void setFrames(TextureRegion[] frames, float delay) {
        this.frames = frames;
        time = 0;
        currentFrame = 0;
        this.delay = delay;
    }

    public void update(float dt) {
        if(delay <= 0) return;
        time += dt;
        while(time >= delay) {
            step();
        }
    }

    private void step() {
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length) {
            currentFrame = 0;
        }
    }

    public void setDelay(float f) { delay = f; }
    public void setCurrentFrame(int i) { if(i < frames.length) currentFrame = i; }
    public void setFrames(TextureRegion[] frames) {
        setFrames(frames, 1 / 12f);
    }
    public TextureRegion getFrame() { return frames[currentFrame]; }

}
