package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;


public class PlayState extends State {
    private Character chrc;
    private Texture background;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        chrc = new Character(50,100);
        gameCam.setToOrtho(false, Khartoosha.Gwidth,Khartoosha.Gheight);
        background = new Texture("background.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            chrc.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            chrc.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            chrc.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            chrc.fire();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        chrc.update(delta);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Khartoosha.Gwidth, Khartoosha.Gheight);
        sb.draw(chrc.getTexture(),chrc.getPosition().x,chrc.getPosition().y);
        sb.end();
    }
    @Override
    public void dispose(){
        background.dispose();
        chrc.dispose();
    }
    @Override
    public void resize(int width, int height){
        aspectRatio = (float) width / height;
        gameCam.setToOrtho(false,width,height);
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = aspectRatio * gameCam.viewportWidth;
    }
}
