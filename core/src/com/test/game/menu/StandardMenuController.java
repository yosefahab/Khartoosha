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
import com.test.game.Khartoosha;
import com.test.game.soundsManager;

import java.util.HashMap;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public abstract class StandardMenuController {
    protected Khartoosha game;
    Stage stage;
    public Stage getStage() {
        return stage;
    }

    protected Table table;
    public Table getTable() {
        return table;
    }

    final int numOfButtons;
    public String[] textButtonNames;
    java.util.Map<String, TextButton> textButtonMap;

    MenuStyle menuStyle;

    int currTextButton;

    float buttonScale;

    public StandardMenuController(int numOfButtons, Khartoosha game) {
        this.game = game;
        this.numOfButtons = numOfButtons;
        buttonScale = 1f; //buttons scale doesn't change

        textButtonNames = new String[numOfButtons + 1];

        menuStyle = new MenuStyle();
        //initialize map
        textButtonMap = new HashMap<>();

        currTextButton = 0;
    }
    public StandardMenuController(int numOfButtons, Khartoosha game, float buttonScale) {
        this(numOfButtons, game);
        this.buttonScale = buttonScale;
    }

    public void initializeTextButtonMap() { // must be called int the constructor of the child screen AFTER the textButtonNames are set manually
        for (int i = 1; i <= numOfButtons; i++) {
            textButtonMap.put(textButtonNames[i], new TextButton(textButtonNames[i], menuStyle.getTextButtonStyle()));
            final TextButton textButton = textButtonMap.get(textButtonNames[i]);
            final int finalI = i; //just setting i as final to be able to pass it
            textButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosen(textButtonNames[finalI], finalI);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    soundsManager.click();
                    currTextButton = finalI;
                    setActiveButton(textButtonNames[finalI]);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    currTextButton = 0;
                    setActiveButton("");
                }
            });
        }
    }

    public abstract void chosen(String chosenButton, int chosenIndex);

     void handleKeyboard() {
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    if (currTextButton == textButtonNames.length - 1) {
                        currTextButton = 0;
                    }
                    currTextButton++;
                    System.out.println("down");
                }
                if (keycode == Input.Keys.UP) {
                    if (currTextButton <= 1) {
                        currTextButton = textButtonNames.length;
                    }
                    currTextButton--;
                    System.out.println("up");
                }
                setActiveButton(textButtonNames[currTextButton]);
                if (keycode == Input.Keys.ENTER) {
                    chosen(textButtonNames[currTextButton], currTextButton);
                }
                return true;
            }
        });
    }

    public void deActivateAllTextButtons() {
        TextButton button;
        for (int i = 1; i <= numOfButtons; i++) {
            button = textButtonMap.get(textButtonNames[i]);
            button.setChecked(false);
        }
    }
    public void setActiveButton(String buttonName) {
         deActivateAllTextButtons();
        TextButton button;
        if (textButtonMap.containsKey(buttonName)) {
            button = textButtonMap.get(buttonName);
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

    public void renderMenuBG() {
        Khartoosha.batch.begin();
        MovingBackground.displayMenuBG();
        Khartoosha.batch.end();
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
            TextButton button = textButtonMap.get(textButtonNames[i]);
            button.getLabel().setFontScale(buttonScale);
            //Texture texture = new Texture("menu/menu_char1_inactive.png");

            //Sprite sprite = new Sprite(texture);
            //table.add();
            table.add(button);
            table.getCell(button).spaceBottom(40);
            table.row();
        }

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
