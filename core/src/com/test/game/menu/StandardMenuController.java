package com.test.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public abstract class StandardMenuController {
    Stage stage;
    Table table;

    final int numOfButtons;
    public String[] stringButtonNames;
    java.util.Map<String, TextButton> textButtonMap;

    MenuStyle menuStyle;

    int currButton;

    float buttonScale;

    public StandardMenuController(int numOfButtons) {
        this.numOfButtons = numOfButtons;
        buttonScale = 1f; //buttons scale doesn't change

        stringButtonNames = new String[numOfButtons + 1];

        menuStyle = new MenuStyle();
        //initialize map
        textButtonMap = new HashMap<>();

        currButton = 0;
    }
    public StandardMenuController(int numOfButtons, float buttonScale) {
        this(numOfButtons);
        this.buttonScale = buttonScale;
    }

    public void initializeButtonMap() { // must be called int the constructor of the child screen AFTER the textButtonNames are set manually
        for (int i = 1; i <= numOfButtons; i++) {
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
    }

    public abstract void chosen(int finalI);

    void handleKeyboard() {
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    if (currButton == stringButtonNames.length - 1) {
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

    public void setActiveButton(int buttonIndex) {
        TextButton button;
        for (int i = 1; i <= numOfButtons; i++) {
            button = textButtonMap.get(stringButtonNames[i]);
            button.setChecked(false);
        }
        if (buttonIndex > 0 && buttonIndex < stringButtonNames.length) {
            button = textButtonMap.get(stringButtonNames[buttonIndex]);
            button.setChecked(true);
        }
    }

    public void setCustomButtonScale(String buttonName, float scale) {
        textButtonMap.get(buttonName).getLabel().setFontScale(scale);
    }

    public void setScreen(final Screen screen, final Screen currScreen) {
        stage.addAction(sequence(moveTo(-stage.getWidth(), 0, .3f), run(new Runnable() {

            @Override
            public void run() {
                currScreen.dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
            }
        })));
    }

    public void menuControllerShow() {
        stage = new Stage();
        table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);

        handleKeyboard();


        Label label = new Label("esht8l", menuStyle.getLabelStyle());

        //table.debug();

        //adding buttons to table
        for (int i = 1; i <= numOfButtons; i++) {
            TextButton button = textButtonMap.get(stringButtonNames[i]);
            button.getLabel().setFontScale(buttonScale);
            table.add(button);
            table.getCell(button).spaceBottom(40);
            table.row();
        }
        stage.addActor(table);
    }

    public void menuControllerRender(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void menuControllerDispose() {
        stage.dispose();
        menuStyle.dispose();
    }
}
