package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayMenuScreen;

public class MainMenuScreen implements Screen {
    private static final int PLAY_BUTTON_WIDTH = 340;
    private static final int PLAY_BUTTON_HEIGHT = 145;
    private static final int PLAY_BUTTON_Y = (int) (Khartoosha.Gheight - 160);
    private static final int PLAY_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (PLAY_BUTTON_WIDTH / 2));

    private static final int SETTINGS_BUTTON_WIDTH = 300;
    private static final int SETTINGS_BUTTON_HEIGHT = 80;
    private static final int SETTINGS_BUTTON_Y = PLAY_BUTTON_Y - PLAY_BUTTON_HEIGHT - 50 + (PLAY_BUTTON_HEIGHT-SETTINGS_BUTTON_HEIGHT);
    private static final int SETTINGS_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (SETTINGS_BUTTON_WIDTH / 2));

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 100;
    private static final int EXIT_BUTTON_Y = SETTINGS_BUTTON_Y - SETTINGS_BUTTON_HEIGHT - 50 - (EXIT_BUTTON_HEIGHT-SETTINGS_BUTTON_HEIGHT);
    private static final int EXIT_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (EXIT_BUTTON_WIDTH / 2));

    Texture playActive, playInActive;
    Texture settingsActive, settingsInActive;
    Texture exitActive, exitInActive;

    Khartoosha game;

    public MainMenuScreen(Khartoosha game) {
        this.game = game;
        playActive = new Texture("menu/menu_play_active.png");
        playInActive = new Texture("menu/menu_play_inactive.png");
        settingsActive = new Texture("menu/menu_settings_active.png");
        settingsInActive = new Texture("menu/menu_settings_inactive.png");
        exitActive = new Texture("menu/menu_exit_active.png");
        exitInActive = new Texture("menu/menu_exit_inactive.png");
    }
    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //play button
        if(Gdx.input.getX() < PLAY_BUTTON_X  + PLAY_BUTTON_WIDTH && Gdx.input.getX() > PLAY_BUTTON_X
           && Khartoosha.Gheight - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > PLAY_BUTTON_Y)
        {
            game.batch.draw(playActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                game.setScreen(new PlayMenuScreen(game));
            }
        } else {
            game.batch.draw(playInActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        //settings button
        if(Gdx.input.getX() < SETTINGS_BUTTON_X  + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > SETTINGS_BUTTON_X
                && Khartoosha.Gheight - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > SETTINGS_BUTTON_Y
            ) {
            game.batch.draw(settingsActive, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                game.setScreen(new SettingsMenuScreen(game));
            }

        } else {
            game.batch.draw(settingsInActive, SETTINGS_BUTTON_X, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }

        //exit button
        if(Gdx.input.getX() < EXIT_BUTTON_X  + EXIT_BUTTON_WIDTH && Gdx.input.getX() > EXIT_BUTTON_X
                && Khartoosha.Gheight - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > EXIT_BUTTON_Y)
        {
            game.batch.draw(exitActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitInActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
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
