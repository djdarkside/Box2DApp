package com.djdarkside.box2dapp.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by djdarkside on 11/12/2016.
 */
public class ParallaxLayer {
    public TextureRegion region ;
    public Vector2 parallaxRatio;
    public Vector2 startPosition;
    public Vector2 padding ;

    public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 padding){
        this(region, parallaxRatio, new Vector2(0,0),padding);
    }
    /**
     * @param region   the TextureRegion to draw , this can be any width/height
     * @param parallaxRatio   the relative speed of x,y {@link ParallaxBackground#ParallaxBackground(ParallaxLayer[], float, float, Vector2)}
     * @param startPosition the init position of x,y
     * @param padding  the padding of the region at x,y
     */
    public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 startPosition,Vector2 padding){
        this.region  = region;
        this.parallaxRatio = parallaxRatio;
        this.startPosition = startPosition;
        this.padding = padding;
    }

}
