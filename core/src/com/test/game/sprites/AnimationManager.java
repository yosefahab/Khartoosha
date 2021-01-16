package com.test.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class AnimationManager {
    public enum State { STANDING, JUMPING, RUNNING }

    public State currentState;
    public State previousState;
    private boolean faceRight;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Character player;
    private Texture texture;
    public AnimationManager(boolean faceRight, Texture texture, Character player) {
        this.faceRight = faceRight;
        this.currentState = State.STANDING;
        this.previousState = State.STANDING;
        this.player = player;
        this.texture = texture;

    }
    //@param charNum number of character in pack image, depends on which character selected
    public Animation<TextureRegion> runAnimation(int charNum){
        frames = new Array<>();
        for (int i=1;i<4;i++){
            frames.add(new TextureRegion(this.texture, (i * 374),(charNum-1)*471,374,471 ));
        }
        return new Animation<> (player.speedCap*0.1f,frames);
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) player.runAnimation.getKeyFrame(stateTimer,true);
                break;
            case JUMPING:
                region = player.jumping;
                break;
            case STANDING:
            default:
                region = player.idle;
        }
        if ((player.physicsBody.getLinearVelocity().x < 0 || !faceRight) && !region.isFlipX()){
            region.flip(true,false);
            faceRight=false;
        }
        else if ((player.physicsBody.getLinearVelocity().x >0 || faceRight) && region.isFlipX()){
            region.flip(true,false);
            faceRight=true;
        }
        stateTimer = currentState == previousState? stateTimer+delta:0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if (player.physicsBody.getLinearVelocity().y > 0)
            return State.JUMPING;
        if (player.physicsBody.getLinearVelocity().x!= 0)
            return State.RUNNING;
        else return State.STANDING;
    }
    public void clearFrames(){frames.clear();}
}
