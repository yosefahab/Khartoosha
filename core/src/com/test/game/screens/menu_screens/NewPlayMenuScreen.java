package com.test.game.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.test.game.Khartoosha;
import com.test.game.menu.MovingBackground;
import com.test.game.menu.StandardMenuController;

public class NewPlayMenuScreen extends StandardMenuController implements Screen {

    static final int NUM_OF_BUTTONS = 3;

    static final float BUTTONS_SCALE = 0.6f;

    public NewPlayMenuScreen(Khartoosha game) {
        super(NUM_OF_BUTTONS, game, BUTTONS_SCALE);
        MovingBackground.setBg(MovingBackground.initializeMenuBG());
        textButtonNames[1] = "1 player";
        textButtonNames[2] = "2 players";
        textButtonNames[3] = "back";
        initializeTextButtonMap();
    }
    public void chosen(String chosenButton, int chosenIndex) {
        switch (chosenButton) {
            case "1 player":
                setScreen(new MapChoiceMenuScreen(false, game), this);
                break;
            case "2 players":
                setScreen(new MapChoiceMenuScreen(true, game), this);
                break;
            case "back":
                setScreen(new NewMainMenu(game), this);
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
        //getTable().add(imageButton);
        getStage().addActor(getTable());
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
