package com.test.game.screens;

import com.badlogic.gdx.Screen;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDim;
import com.test.game.menu.MenuTextures;

public class SettingsMenuScreen extends MenuBG implements Screen, MenuTextures {
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

    @Override
    public void chosenTexture(int textureNum) {

    }

    @Override
    public void checkBoundsAndDraw(MenuTextureDim[] dim, int textureNum) {

    }
}
