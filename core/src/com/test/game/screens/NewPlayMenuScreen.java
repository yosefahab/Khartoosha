package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.test.game.Khartoosha;
import com.test.game.menu.MovingBackground;
import com.test.game.menu.StandardMenuController;

public class NewPlayMenuScreen extends StandardMenuController implements Screen {


    static final int NUM_OF_BUTTONS = 3;

    static final float BUTTONS_SCALE = 0.6f;

    public NewPlayMenuScreen() {
        super(NUM_OF_BUTTONS, BUTTONS_SCALE);
        MovingBackground.setBg(MovingBackground.initializeMenuBG());
        stringButtonNames[1] = "1 player";
        stringButtonNames[2] = "2 players";
        stringButtonNames[3] = "back";
        initializeButtonMap();
    }
    public void chosen(int chosenIndex) {
        switch (stringButtonNames[chosenIndex]) {
            case "1 player":
                //TODO: uncomment when new character choice menu is ready
                //setScreen(new CharacterChoiceMenuScreen(game, false), this);
                break;
            case "2 players":
                //TODO: uncomment when new character choice menu is ready
                //setScreen(new CharacterChoiceMenuScreen(game, true), this);
                break;
            case "back":
                setScreen(new NewMainMenu(), this);
                break;
        }
    }

    @Override
    public void show() {
        menuControllerShow();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Khartoosha.batch.begin();
        MovingBackground.displayMenuBG();
        Khartoosha.batch.end();

        menuControllerRender(delta);
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
        menuControllerDispose();
    }
}
