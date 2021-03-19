package com.test.game.screens.play_screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class AnimationManager {
    private enum State { STANDING, JUMPING, RUNNING }

    private State currentState;
    private State previousState;

    private boolean faceRight;
    private float stateTimer;
    private final Character character;
    private final Texture texture;
    public AnimationManager(boolean faceRight, Texture texture, Character character) {
        this.faceRight = faceRight;
        this.currentState = State.STANDING;
        this.previousState = State.STANDING;
        this.character = character;
        this.texture = texture;

    }
    //@param charNum number of character in pack image, depends on which character selected
    public Animation<TextureRegion> runAnimation(int charNum) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 7; i++) {
            frames.add(new TextureRegion(this.texture, (i * 513), (charNum - 1) * 637, 513, 637));
        }
        return new Animation<>(character.getSpeedCap() * 0.08f, frames);
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) character.runAnimation.getKeyFrame(stateTimer,true);
                break;
            case JUMPING:
                region = character.jumping;
                break;
            case STANDING:
            default:
                region = character.idle;
        }
        if ((character.physicsBody.getLinearVelocity().x < 0 || !faceRight) && !region.isFlipX()){
            region.flip(true,false);
            faceRight=false;
        }
        else if ((character.physicsBody.getLinearVelocity().x >0 || faceRight) && region.isFlipX()){
            region.flip(true,false);
            faceRight=true;
        }
        stateTimer = currentState == previousState? stateTimer+delta:0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if (character.physicsBody.getLinearVelocity().y > 0)
            return State.JUMPING;
        if (character.physicsBody.getLinearVelocity().x!= 0)
            return State.RUNNING;
        else return State.STANDING;
    }
}
