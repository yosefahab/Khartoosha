package com.test.game.screens.menu_screens;

import com.badlogic.gdx.Screen;
import com.test.game.Khartoosha;
import com.test.game.menu.ChoiceMenuController;
import com.test.game.screens.PlayScreen;

import java.util.ArrayList;

public class NewCharacterChoiceMenuScreen extends ChoiceMenuController implements Screen {
    int mapID;

    static final int NUM_OF_BUTTONS = 1;
    static final int NUM_OF_CHOICES = 3;
    static final String CHOICE_NAME = "char";

    boolean isTwoPlayers;

    boolean isFirstCharChosen;
    ArrayList<Integer> chosenCharacters;

    public NewCharacterChoiceMenuScreen(boolean isTwoPlayers, int mapID, Khartoosha game) {
        super(NUM_OF_BUTTONS, NUM_OF_CHOICES, CHOICE_NAME, game);
        textButtonNames[1] = "back";
        initializeTextButtonMap();

        this.isTwoPlayers = isTwoPlayers;
        this.mapID = mapID;

        //customize label (heading)
        if (isTwoPlayers){
            heading.setText("1st character");
            heading.setFontScale(0.5f);
        } else {
            heading.setText("choose your character");
            heading.setFontScale(0.4f);
        }



        //initialize imageButton's map and style
        initializeImageButtonMap("menu/menu_char_choice_active.png");

        isFirstCharChosen = false;
        chosenCharacters = new ArrayList<>();
    }

    @Override
    public void chosen(String chosenButton, int chosenIndex) {
        if(chosenButton.contains(CHOICE_NAME)) {
            if(!isTwoPlayers){
                setScreen(new PlayScreen(game, mapID, chosenIndex), this);
            } else {
                if(!isFirstCharChosen) {
                    chosenCharacters.add(chosenIndex);
                    heading.setText("2nd character");
                    isFirstCharChosen = true;
                } else {
                    chosenCharacters.add(chosenIndex);
                    setScreen(new PlayScreen(game, mapID, chosenCharacters.get(0), chosenCharacters.get(1)), this);
                }
            }
        } else {
            switch (chosenButton) {
                case "back":
                    setScreen(new NewPlayMenuScreen(game), this);
                    break;
            }
        }
    }

    @Override
    public void show() {
        menuControllerShow();
    }

    @Override
    public void render(float delta) {
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

    }
}
