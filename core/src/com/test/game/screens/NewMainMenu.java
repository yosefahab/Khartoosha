package com.test.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.test.game.Khartoosha;
import com.test.game.menu.MenuStyle;
import com.test.game.menu.MovingBackground;

public class NewMainMenu extends MovingBackground implements Screen {
    Stage stage;
    Table table;

    static final int NUM_OF_BUTTONS = 3;
    Map<String, TextButton> textButtonMap;
    String[] stringButtonNames = new String[NUM_OF_BUTTONS + 1];

    int currButton;

    MenuStyle menuStyle;

    public NewMainMenu() {
        super(new Texture("menu/menu_bg_darker1.png"));
        stringButtonNames[1] = "play";
        stringButtonNames[2] = "settings";
        stringButtonNames[3] = "exit";

        menuStyle = new MenuStyle();

        //initialize map
        textButtonMap = new HashMap<>();
        for (int i = 1; i <= NUM_OF_BUTTONS; i++) {
            textButtonMap.put(stringButtonNames[i], new TextButton(stringButtonNames[i], menuStyle.getButtonStyle()));
            final TextButton textButton = textButtonMap.get(stringButtonNames[i]);
            final int finalI = i; //just setting i as final to be able to pass it
            textButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosen(finalI);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    currButton = finalI;
                    setActiveButton(finalI);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    currButton = 0;
                    setActiveButton(0);
                }
            });
        }
        currButton = 0;
    }
    void chosen(int chosenIndex) {
        switch (stringButtonNames[chosenIndex]) {
            case "play":
                setScreen(new NewPlayMenuScreen());
                break;
            case "settings":
                setScreen(new NewSettingsMenuScreen());
                break;
            case "exit":
                Gdx.app.exit();
                break;
        }
    }
    void handleKeyboard() {
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    if(currButton == stringButtonNames.length - 1) {
                        currButton = 0;
                    }
                    currButton++;
                    System.out.println("down");
                }
                if (keycode == Input.Keys.UP) {
                    if (currButton == 1) {
                        currButton = stringButtonNames.length;
                    }
                    currButton--;
                    System.out.println("up");
                }
                setActiveButton(currButton);
                if (keycode == Input.Keys.ENTER) {
                    chosen(currButton);
                }
                return true;
            }
        });
    }
    void setActiveButton(int buttonIndex) {
        TextButton button;
        for (int i = 1; i <= NUM_OF_BUTTONS; i++) {
            button = textButtonMap.get(stringButtonNames[i]);
            button.setChecked(false);
        }
        if (buttonIndex > 0 && buttonIndex < stringButtonNames.length) {
            button = textButtonMap.get(stringButtonNames[buttonIndex]);
            button.setChecked(true);
        }
    }
    void setScreen(final Screen screen) {
        stage.addAction(sequence(moveTo(-stage.getWidth(), 0, .3f), run(new Runnable() {

            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
            }
        })));
    }

    @Override
    public void show() {
        stage = new Stage();

        table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);

        handleKeyboard();


        Label label = new Label("esht8l", menuStyle.getLabelStyle());

        //table.debug();

        //adding buttons to table
        for (int i = 1; i <= NUM_OF_BUTTONS; i++) {
            TextButton button = textButtonMap.get(stringButtonNames[i]);
            table.add(button);
            table.getCell(button).spaceBottom(40);
            table.row();

            //setting custom button scaling
            if(stringButtonNames[i].equals("settings") || stringButtonNames[i].equals("exit")) {
                button.getLabel().setFontScale(0.6f);
            }
        }
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Khartoosha.batch.begin();
        displayBG();
        Khartoosha.batch.end();

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        menuStyle.dispose();
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