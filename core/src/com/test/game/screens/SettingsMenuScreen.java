package com.test.game.screens;

import com.badlogic.gdx.Screen;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;

public class SettingsMenuScreen extends MenuBG implements Screen {
    //TODO
    Khartoosha game;
    public SettingsMenuScreen(Khartoosha game) {
        this.game = game;
        //TODO
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //TODO
        displayBG(game);
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
