package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;


public class PlayState extends State {
    private Character chrc,char2;
    private Texture background;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        chrc = new Character(10,20,false);
        char2 = new Character(100,20,true);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
            chrc.fire();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            char2.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            char2.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            char2.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            char2.fire();
        }
    }

    @Override
    public void update() {
        handleInput();
        chrc.update();
        char2.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Khartoosha.Gwidth, Khartoosha.Gheight);
        sb.draw(chrc.getTexture(),chrc.getPosition().x,chrc.getPosition().y,50,100);
        sb.draw(char2.getTexture(),char2.getPosition().x,char2.getPosition().y,50,100);
        sb.end();
    }
    @Override
    public void dispose(){
        background.dispose();
        chrc.dispose();
        char2.dispose();
    }
    @Override
    public void resize(int width, int height){
        aspectRatio = (float) width / height;
        gameCam.setToOrtho(false,width,height);
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = aspectRatio * gameCam.viewportWidth;
    }
}
