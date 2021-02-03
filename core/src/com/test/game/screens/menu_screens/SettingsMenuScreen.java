package com.test.game.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.test.game.Khartoosha;
import com.test.game.SoundsManager;
import com.test.game.menu.OnOffLabel;
import com.test.game.menu.StandardMenuController;

import java.util.HashMap;
import java.util.Map;

import static com.test.game.menu.MovingBackground.initializeMenuBG;
import static com.test.game.menu.MovingBackground.setBg;

public class SettingsMenuScreen extends StandardMenuController implements Screen {
    static final int NUM_OF_ALL_BUTTONS = 3;
    static final int NUM_OF_ON_OFF_BUTTONS = 2;
    Map<String, OnOffLabel> onOffLabelMap;
    Label heading;

    public SettingsMenuScreen(Khartoosha game) {
        super(NUM_OF_ALL_BUTTONS, game);
        setBg(initializeMenuBG());

        //initialize heading
        heading = new Label("settings", menuStyle.getHeadingLabelStyle());
        heading.setFontScale(0.6f);

        textButtonNames[1] = "music";
        textButtonNames[2] = "soundfx";
        textButtonNames[3] = "back";
        initializeTextButtonMap();

        //initialize on off label map
        onOffLabelMap = new HashMap<>();
        for (int i = 1; i <= NUM_OF_ON_OFF_BUTTONS; i++) {
            Label.LabelStyle labelStyle = new Label.LabelStyle(menuStyle.getHeadingLabelStyle());
            onOffLabelMap.put(textButtonNames[i], new OnOffLabel(false, new Label("off", labelStyle), labelStyle));
        }
        //update/set settings (the boolean values in the on off label)
        updateSettings();

    }

    public void updateSettings(){
        //setting the on off labels according to the set values in SoundsManager(music) and SoundsManager(sfx vol)
        if(SoundsManager.menuMusic.isPlaying()){
            onOffLabelMap.get("music").setIsOn(true);
        } else{
            onOffLabelMap.get("music").setIsOn(false);
        }
        if (SoundsManager.soundVolume > 0f){
            onOffLabelMap.get("soundfx").setIsOn(true);
        } else{
            onOffLabelMap.get("soundfx").setIsOn(false);
        }
    }

    @Override
    public void chosen(String chosenButton, int chosenIndex) {
        if(chosenIndex <= NUM_OF_ON_OFF_BUTTONS) {
            OnOffLabel onOfflabel = onOffLabelMap.get(chosenButton);
            onOfflabel.setIsOn(!onOfflabel.getIsOn());
            if (chosenButton.equals("music")) { //if its music onOff button
                if (onOfflabel.getIsOn()) {
                    SoundsManager.playMenuMusic();
                    SoundsManager.musicVolume = SoundsManager.DEFAULT_MUSIC_VOL;
                } else {
                    SoundsManager.stopMenuMusic();
                }
            } else if (chosenButton.equals("soundfx")) { //if its soundfx onOff button
                if (onOfflabel.getIsOn()) {
                    SoundsManager.soundVolume = SoundsManager.DEFAULT_SOUND_VOL;
                } else {
                    SoundsManager.soundVolume = 0f;
                }
            }
        }
        else {
            if (chosenButton.equals("back")) {
                setScreen(new MainMenuScreen(game), this);
            }
        }
    }

    @Override
    public void show() {
        stage = new Stage();
        table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);
        table.top();
        final float fontScale = 0.6f;
        final float padding = 60;
        final int colSpan = 2;

        handleKeyboard();

        //table.debug();

        table.add(heading).colspan(colSpan).row();

        //adding buttons to table
        for (int i = 1; i <= NUM_OF_ON_OFF_BUTTONS; i++) {
            TextButton button = textButtonMap.get(textButtonNames[i]);
            Label label = onOffLabelMap.get(textButtonNames[i]).getLabel();
            button.getLabel().setFontScale(fontScale);
            label.setFontScale(fontScale);
            table.add(button).left().expandX().padLeft(padding);
            table.add(label).right().expandX().padRight(padding+30);
            table.getCell(button).spaceBottom(40);
            table.getCell(label).spaceBottom(40);
            table.row();
        }
        textButtonMap.get("back").getLabel().setFontScale(fontScale - 0.1f);
        table.add(textButtonMap.get("back")).colspan(colSpan).spaceTop(40);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderMenuBG();
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
        menuControllerDispose();
    }
}
