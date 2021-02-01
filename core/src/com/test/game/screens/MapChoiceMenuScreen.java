package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.test.game.Khartoosha;
import com.test.game.menu.ChoiceMenuController;

import java.util.ArrayList;

public class MapChoiceMenuScreen extends ChoiceMenuController implements Screen {

    static final int NUM_OF_BUTTONS = 1;
    static final int NUM_OF_CHOICES = 4;
    static final String CHOICE_NAME = "map";

    boolean isTwoPlayers;

    public MapChoiceMenuScreen(boolean isTwoPlayers, Khartoosha game) {
        super(NUM_OF_BUTTONS, NUM_OF_CHOICES, CHOICE_NAME, game);
        textButtonNames[1] = "back";
        initializeTextButtonMap();

        this.isTwoPlayers = isTwoPlayers;

        //customize label (heading)
        heading.setText("choose a map");
        heading.setFontScale(0.5f);

        //initialize imageButton's map and style
        initializeImageButtonMap("menu/menu_char_choice_active.png");
    }


    @Override
    public void chosen(String chosenButton, int chosenIndex) {
        if(chosenButton.contains(CHOICE_NAME)) {
            setScreen(new NewCharacterChoiceMenuScreen(isTwoPlayers, chosenIndex, game), this);
        } else {
            switch (chosenButton) {
                case "back":
                    setScreen(new NewPlayMenuScreen(game), this);
                    break;
            }
        }
    }

    @Override
    public void show() { //TODO: this should be changed and handled by menuControllerShow
        stage = new Stage();
        table = new Table();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);

        handleKeyboard();

        table.add(heading).colspan(4).align(Align.top);

        table.getCell(heading).spaceBottom(80);
        table.row();

        for (int i = 1; i <= NUM_OF_CHOICES; i++) {
            ImageButton imageButton = choicesImageButtonsMap.get(imageButtonNames[i]);
            table.add(imageButton);
            table.getCell(imageButton).spaceRight(25);
        }
        //table.debug();
        TextButton button = textButtonMap.get(textButtonNames[1]); //TODO: change the hardcoded values
        button.getLabel().setFontScale(0.5f);

        table.row();
        table.add(button).colspan(4);
        table.getCell(button).spaceTop(80);
        stage.addActor(table);
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
