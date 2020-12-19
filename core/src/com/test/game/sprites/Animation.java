package com.test.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; //how long frame stays before switching
    private float currentFrameTime; //which second are we in/frame

    private int frameCount; //how many frames in image
    private int frame; //which frame we're in

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth()/frameCount;
        for (int i=0;i<frameCount;i++){
            frames.add(new TextureRegion(region,i* frameWidth, 0,frameWidth,region.getRegionHeight()));
        }
        this.frameCount= frameCount;
        maxFrameTime = cycleTime/frameCount;
        frame=0;
        // @param cycletime the total time the whole sprite should take
    }
    public void update(float delta){
        currentFrameTime+=delta;
        if(currentFrameTime>maxFrameTime){ //if current frame time exceeds max frame time, move on to next frame, reset current frame time
            frame++;
            frame%=frameCount;
            currentFrameTime=0;
        }
    }
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
