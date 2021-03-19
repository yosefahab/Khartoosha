package com.test.game.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MovingBackground;
import com.test.game.menu.StandardMenuController;

public class MainMenuScreen extends StandardMenuController implements Screen {

    static final int NUM_OF_BUTTONS = 3;
    MovingBackground mbg;
    public MainMenuScreen(Khartoosha game)
    {
        super(NUM_OF_BUTTONS, game);
         mbg = new MovingBackground(MovingBackground.initializeMenuBG());
        textButtonNames[1] = "play";
        textButtonNames[2] = "settings";
        textButtonNames[3] = "exit";
        initializeTextButtonMap();
    }
    public void chosen(String chosenButton, int chosenIndex) {
        switch (chosenButton) {
            case "play":
                setScreen(new PlayMenuScreen(game), this);
                break;
            case "settings":
                setScreen(new SettingsMenuScreen(game), this);
                break;
            case "exit":
                this.dispose();
                Gdx.app.exit();
                break;
            default:

        }
    }


    @Override
    public void show() {
        menuControllerShow();
        //setting custom button scaling
        setCustomButtonScale("settings", 0.5f);
        setCustomButtonScale("exit", 0.7f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        renderMenuBG();
        Khartoosha.batch.begin();
        MovingBackground.displayMenuBG();
        Khartoosha.batch.end();

        menuControllerRender(delta);
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

/*
* --------------------image button implementation----------------
* ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = new SpriteDrawable(sprite);
        imageButtonStyle.down = new SpriteDrawable(sprite2);
        imageButtonStyle.over = new SpriteDrawable(sprite2);
        imageButtonStyle.checked = new SpriteDrawable(sprite2);



        imageButton = new ImageButton(imageButtonStyle);
        imageButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);

                System.out.println("bitch it works");
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(-stage.getWidth(), 0, .3f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsMenuScreen(game));
                    }
                })));
            }
        });
        table.add(imageButton);
*
*/