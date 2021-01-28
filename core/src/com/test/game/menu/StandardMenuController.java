package com.test.game.menu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Map;

public class StandardMenuController {
    Stage stage;
    Table table;

    final int numOfButtons;
    String[] stringButtonNames;
    java.util.Map<String, TextButton> textButtonMap;

    int currButton;

    public StandardMenuController(Stage stage, Table table, int numOfButtons, String[] stringButtonNames, Map<String, TextButton> textButtonMap, int currButton) {
        this.stage = stage;
        this.table = table;
        this.numOfButtons = numOfButtons;
        this.stringButtonNames = stringButtonNames;
        this.textButtonMap = textButtonMap;
        this.currButton = currButton;
    }

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
                    //chosen(currButton);
                }
                return true;
            }
        });
    }

    void setActiveButton(int buttonIndex) {
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
}
