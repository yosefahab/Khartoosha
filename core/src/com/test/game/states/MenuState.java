package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Khartoosha;

public class MenuState extends State {
    private Texture background, character;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.png");
        character = new Texture("mando.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Khartoosha.Gwidth, Khartoosha.Gheight);
        sb.draw(character, Khartoosha.Gwidth / 2 - (float)character.getWidth() / 2, Khartoosha.Gheight / 2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        character.dispose();
    }
    @Override
    public void resize(int width, int height){
        aspectRatio = (float) width / height;
        gameCam.setToOrtho(false,width,height);
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = aspectRatio * gameCam.viewportWidth;
    }
}