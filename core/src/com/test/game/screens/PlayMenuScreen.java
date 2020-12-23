package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;

public class PlayMenuScreen implements Screen {
    private static final int ONE_PLAYER_BUTTON_WIDTH = 350;
    private static final int ONE_PLAYER_BUTTON_HEIGHT = 155;
    private static final int ONE_PLAYER_BUTTON_Y = (int) (Khartoosha.Gheight - 200);
    private static final int ONE_PLAYER_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (ONE_PLAYER_BUTTON_WIDTH / 2));

    private static final int TWO_PLAYER_BUTTON_WIDTH = 340;
    private static final int TWO_PLAYER_BUTTON_HEIGHT = 145;
    private static final int TWO_PLAYER_BUTTON_Y = ONE_PLAYER_BUTTON_Y - ONE_PLAYER_BUTTON_HEIGHT - 80 + (ONE_PLAYER_BUTTON_HEIGHT-TWO_PLAYER_BUTTON_HEIGHT);
    private static final int TWO_PLAYER_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (TWO_PLAYER_BUTTON_WIDTH / 2));
    
    Texture onePlayerActive, onePlayerInActive;
    Texture twoPlayerActive, twoPlayerInActive;
    
    Khartoosha game;
    
    public PlayMenuScreen(Khartoosha game) {
        this.game = game;
        onePlayerActive = new Texture("menu/menu_onePlayer_active.png");
        onePlayerInActive = new Texture("menu/menu_onePlayer_inactive.png");
        twoPlayerActive = new Texture("menu/menu_twoPlayer_active.png");
        twoPlayerInActive = new Texture("menu/menu_twoPlayer_inactive.png");
    }

    
    
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //onePlayer button
        if(Gdx.input.getX() < ONE_PLAYER_BUTTON_X  + ONE_PLAYER_BUTTON_WIDTH && Gdx.input.getX() > ONE_PLAYER_BUTTON_X
                && Khartoosha.Gheight - Gdx.input.getY() < ONE_PLAYER_BUTTON_Y + ONE_PLAYER_BUTTON_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > ONE_PLAYER_BUTTON_Y)
        {
            game.batch.draw(onePlayerActive, ONE_PLAYER_BUTTON_X, ONE_PLAYER_BUTTON_Y, ONE_PLAYER_BUTTON_WIDTH, ONE_PLAYER_BUTTON_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                game.setScreen(new PlayScreen(game));
            }
        } else {
            game.batch.draw(onePlayerInActive, ONE_PLAYER_BUTTON_X, ONE_PLAYER_BUTTON_Y, ONE_PLAYER_BUTTON_WIDTH, ONE_PLAYER_BUTTON_HEIGHT);
        }

        //twoPlayer button
        if(Gdx.input.getX() < TWO_PLAYER_BUTTON_X  + TWO_PLAYER_BUTTON_WIDTH && Gdx.input.getX() > TWO_PLAYER_BUTTON_X
                && Khartoosha.Gheight - Gdx.input.getY() < TWO_PLAYER_BUTTON_Y + TWO_PLAYER_BUTTON_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > TWO_PLAYER_BUTTON_Y
        ) {
            game.batch.draw(twoPlayerActive, TWO_PLAYER_BUTTON_X, TWO_PLAYER_BUTTON_Y, TWO_PLAYER_BUTTON_WIDTH, TWO_PLAYER_BUTTON_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                //TODO
            }

        } else {
            game.batch.draw(twoPlayerInActive, TWO_PLAYER_BUTTON_X, TWO_PLAYER_BUTTON_Y, TWO_PLAYER_BUTTON_WIDTH, TWO_PLAYER_BUTTON_HEIGHT);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
