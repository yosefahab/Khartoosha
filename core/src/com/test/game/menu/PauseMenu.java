package com.test.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.PlayScreen;
import com.test.game.screens.menu_screens.MainMenuScreen;

//TODO: optimize
public class PauseMenu extends StandardMenuController
{
    static final int NUM_OF_BUTTONS = 3;
    static final float BUTTON_SCALE = 0.6f;

    public static int pauseMenuPageNum = 1;

    PlayScreen currScreen;

    public PauseMenu(Khartoosha game, PlayScreen currPlayScreen)
    {
        super(NUM_OF_BUTTONS, game, BUTTON_SCALE);
        this.currScreen = currPlayScreen;
        textButtonNames[1] = "resume";
        textButtonNames[2] = "settings";
        textButtonNames[3] = "exit";
        initializeTextButtonMap();
    }

    public void showPauseMenu()
    {
        stage = new Stage();
        table = new Table();
        final float subtractFromWidth = 300, subtractFromHeight = 100;
        table.setBounds(subtractFromWidth / 2, subtractFromHeight / 2, Gdx.graphics.getWidth() - subtractFromWidth, Gdx.graphics.getHeight() - subtractFromHeight);
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);
        Sprite bg = new Sprite(new Texture("menu/menu_pauseBG-WIDE2.png"));
        table.background(new SpriteDrawable(bg));
        table.bottom();
        handleKeyboard();

        //table.debug();

        //adding buttons to table
        for (int i = 1; i <= numOfButtons; i++)
        {
            TextButton button = textButtonMap.get(textButtonNames[i]);
            button.getLabel().setFontScale(buttonScale);
            table.add(button);
            table.getCell(button).spaceBottom(40);
            table.row();
        }
        table.padBottom(20);
        stage.addActor(table);
    }

    public void renderPauseMenu(float delta)
    {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void chosen(String chosenButton, int chosenIndex)
    {
        if (chosenButton.equals("resume"))
        { //if resume is clicked
            PlayScreen.isGamePaused = false;
        }
        else if (chosenButton.equals("settings"))
        { //if settings is clicked
            //TODO: figure a way to update settings
//            initializeSettings();
            pauseMenuPageNum = 2;
            //System.out.println(pauseMenuPageNum);
        }
        else if (chosenButton.equals("exit"))
        { //if exit is clicked
            currScreen.dispose();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
        }
    }
}