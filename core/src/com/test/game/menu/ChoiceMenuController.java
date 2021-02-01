package com.test.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.test.game.Khartoosha;
import com.test.game.soundsManager;

import java.util.HashMap;
import java.util.Map;

public abstract class ChoiceMenuController extends StandardMenuController{
//VERY IMPORTANT: here the character buttons are image buttons so they have their own string names array and map

    int numOfChoices;
    int currImageButton;

    protected String[] imageButtonNames;
    //String imageButtonActiveBgPath;
    String imageButtonPath;
    protected Map<String, ImageButton> choicesImageButtonsMap;

    protected Label heading;

    String choiceName;
    public ChoiceMenuController(final int numOfButtons, int numOfChoices, String choiceName, Khartoosha game) {
        super(numOfButtons, game);
        this.numOfChoices = numOfChoices;
        this.choiceName = choiceName;

        MovingBackground.setBg(MovingBackground.initializeMenuBG());

        //initialize heading
        heading = new Label("" ,menuStyle.getLabelStyle());

        //constructing imageButtonNames array
        imageButtonNames = new String[numOfChoices + 1];

        //initialize image buttons names
        for (int i = 1; i <= numOfChoices; i++) {
            imageButtonNames[i] = choiceName + i;
        }

        currImageButton = 1;
    }

    @Override
    public abstract void chosen(String chosenButton, int chosenIndex);

    public String getActiveButton() { //TODO: Use (instance of) to check whether the active button is image or text
                                        //and make handleKeyboard dependant on this function to unify it in both controllers
        TextButton button;
        for (int i = 1; i <= numOfButtons; i++) {
            button = textButtonMap.get(textButtonNames[i]);
            if(button.isChecked()) {
                return textButtonNames[i];
            }
        }

        return "";
    }

     protected void handleKeyboard() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(currTextButton == 0)
                {   //if the back or any other textButton is active, don't accept right or left buttons (to avoid making choices and textButtons both active at the same time)
                    if (keycode == Input.Keys.LEFT)
                    {
                        soundsManager.click();
                        if (currImageButton <= 1)
                        {
                            currImageButton = numOfChoices;
                        } else
                        {
                            currImageButton--;
                        }
                    }
                    if (keycode == Input.Keys.RIGHT)
                    {
                        soundsManager.click();
                        if (currImageButton >= numOfChoices)
                        {
                            currImageButton = 1;
                        } else
                        {
                            currImageButton++;
                        }
                    }
                    if (currImageButton >= 1 && currImageButton <= numOfChoices)
                    {
                        setActiveImageButton(currImageButton);
                    }
                }
                if(keycode == Input.Keys.UP && currTextButton > 0) {
                    soundsManager.click();
                    currTextButton--;
                    if(currTextButton == 0){
                        currImageButton = 1;
                    }
                }
                if(keycode == Input.Keys.DOWN) {
                    soundsManager.click();
                    currTextButton++; //back button
                    currImageButton = 0;
                    if(currTextButton > numOfButtons){
                        currTextButton = numOfButtons;
                    }
                }
                if(currTextButton >= 1 && currTextButton <= numOfButtons) {
                    setActiveButton(textButtonNames[currTextButton]);
                } else if(currImageButton >= 1 && currImageButton <= numOfChoices) {
                    setActiveImageButton(currImageButton);
                }
                if(keycode == Input.Keys.ESCAPE)
                {
                    soundsManager.click();
                    chosen("back", numOfButtons); //click back
                }

                if(keycode == Input.Keys.ENTER){
                    soundsManager.click();
                    if (currTextButton != 0)
                    {
                        chosen(textButtonNames[currTextButton], currTextButton);
                    }
                    if (currImageButton != 0)
                    {
                        chosen(choiceName + currImageButton, currImageButton);
                    }
                }
                return true;
            }
        });
    }

    public void deActivateAllImageButtons() {
        ImageButton imageButton;
        for (int i = 1; i <= numOfChoices; i++) {
            imageButton = choicesImageButtonsMap.get(imageButtonNames[i]);
            imageButton.setChecked(false);
        }
    }
    public void setActiveImageButton(int buttonIndex) {
        deActivateAllImageButtons();
        deActivateAllTextButtons();
        ImageButton imageButton;
        if (buttonIndex > 0 && buttonIndex < imageButtonNames.length) {
            imageButton = choicesImageButtonsMap.get(imageButtonNames[buttonIndex]);
            imageButton.setChecked(true);
        }
    }
    public void setActiveButton(String buttonName) {
        deActivateAllImageButtons();
        super.setActiveButton(buttonName);
    }
    public void initializeImageButtonMap(String imageButtonActiveBgPath/*button background*/) {
        //IMPORTANT: must be called int the constructor of the child screen AFTER the textButtonNames are set manually

        choicesImageButtonsMap = new HashMap<>();
        for (int i = 1; i <= numOfChoices; i++) {
            final int finalI = i;

            imageButtonPath = "menu/menu_" + imageButtonNames[i] + "_inactive.png";
            menuStyle.setImageButtonStyle(imageButtonPath, imageButtonActiveBgPath);

            choicesImageButtonsMap.put(imageButtonNames[i], new ImageButton(menuStyle.getImageButtonStyle()));
            ImageButton imageButton = choicesImageButtonsMap.get(imageButtonNames[i]);
            imageButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosen(imageButtonNames[finalI], finalI);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //IMPORTANT unlike text buttons here there's weird behavior enter runs multiple time 2 times i think
                    currTextButton = 0;
                    currImageButton = finalI;
                    setActiveImageButton(finalI);
                    System.out.println("enter");
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    currTextButton = 0;
                    currImageButton = 0;
                    setActiveImageButton(0);
                    //currButton.set(0,0);
                }
            });
        }
    }

    public static <Vector2, String> Vector2 getKeyByValue(Map<Vector2, String> map, String value) {
        for (Map.Entry<Vector2, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void menuControllerShow() {
        stage = new Stage();
        table = new Table();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);

        handleKeyboard();

        table.add(heading).colspan(3).align(Align.top);

        table.getCell(heading).spaceBottom(80);
        table.row();

        for (int i = 1; i <= numOfChoices; i++) {
            ImageButton imageButton = choicesImageButtonsMap.get(imageButtonNames[i]);
            table.add(imageButton);
            table.getCell(imageButton).spaceRight(120);
        }
        table.debug();
        TextButton button = textButtonMap.get(textButtonNames[1]); //TODO: change the hardcoded values
        button.getLabel().setFontScale(0.5f);

        table.row();
        table.add(button).colspan(3);
        table.getCell(button).spaceTop(80);
        stage.addActor(table);
    }

    @Override
    public void menuControllerRender(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderMenuBG();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void menuControllerDispose() {
        super.menuControllerDispose();
    }

}
