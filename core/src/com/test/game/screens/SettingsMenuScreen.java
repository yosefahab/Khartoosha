package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDim;
import com.test.game.menu.MenuTextureDimDynamic;
import com.test.game.menu.MenuTextureDimStatic;
import com.test.game.menu.MenuTextures;

import javax.xml.soap.Text;

public class SettingsMenuScreen extends MenuBG implements Screen, MenuTextures {
    
    private static final int SETTINGS_BUTTON_WIDTH = 300;
    private static final int SETTINGS_BUTTON_HEIGHT = 80;
    private static final int SETTINGS_BUTTON_Y = (int) (Khartoosha.Gheight - 95);
    private static final int SETTINGS_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (SETTINGS_BUTTON_WIDTH / 2));

    private static final int MUSIC_WIDTH = 380;
    private static final int MUSIC_HEIGHT = 78;
    private static final int MUSIC_Y = SETTINGS_BUTTON_Y - SETTINGS_BUTTON_HEIGHT - 40 - (MUSIC_HEIGHT-SETTINGS_BUTTON_HEIGHT);
    private static final int MUSIC_X = (int) ((Khartoosha.Gwidth / 4) - (MUSIC_WIDTH / 2) + 40);

    private static final int ON_OFF_BUTTON1_WIDTH = 141;
    private static final int ON_OFF_BUTTON1_HEIGHT = 78;
    private static final int ON_OFF_BUTTON1_Y = MUSIC_Y;
    private static final int ON_OFF_BUTTON1_X = (int) ((Khartoosha.Gwidth) - (ON_OFF_BUTTON1_WIDTH*1.5));

    private static final int SOUNDFX_WIDTH = 380;
    private static final int SOUNDFX_HEIGHT = 78;
    private static final int SOUNDFX_Y = MUSIC_Y - MUSIC_HEIGHT - 50;
    private static final int SOUNDFX_X = MUSIC_X;//(int) ((Khartoosha.Gwidth / 4) - (MUSIC_WIDTH / 2));

    private static final int ON_OFF_BUTTON2_WIDTH = 141;
    private static final int ON_OFF_BUTTON2_HEIGHT = 78;
    private static final int ON_OFF_BUTTON2_Y = SOUNDFX_Y;
    private static final int ON_OFF_BUTTON2_X = (int) ((Khartoosha.Gwidth) - (ON_OFF_BUTTON1_WIDTH*1.5));

    private static final int BACK_BUTTON_WIDTH = 209;
    private static final int BACK_BUTTON_HEIGHT = 70;
    private static final int BACK_BUTTON_Y = ON_OFF_BUTTON2_Y - ON_OFF_BUTTON2_HEIGHT - 55 - (BACK_BUTTON_HEIGHT-ON_OFF_BUTTON2_HEIGHT);
    private static final int BACK_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (BACK_BUTTON_WIDTH / 2));

    private static final int NUM_OF_ON_OFF_BUTTONS = 2;
    private String[] onOffButtonTextureNames = new String[NUM_OF_ON_OFF_BUTTONS + 1];
    private MenuTextureDim[] onOffButtonTextures = new MenuTextureDim[NUM_OF_ON_OFF_BUTTONS + 1];
    private boolean[] isOn = new boolean[NUM_OF_ON_OFF_BUTTONS + 1];

    private static final int NUM_OF_STATIC_TEXTURES = 3;
    private String[] staticTextureNames = new String[NUM_OF_STATIC_TEXTURES + 1];
    private MenuTextureDimStatic[] staticTextures = new MenuTextureDimStatic[NUM_OF_STATIC_TEXTURES + 1];

    private static final int NUM_OF_DYNAMIC_TEXTURES = 1;
    private String[] dynamicTextureNames = new String[NUM_OF_DYNAMIC_TEXTURES + 1];
    private MenuTextureDimDynamic[] dynamicTextures = new MenuTextureDimDynamic[NUM_OF_DYNAMIC_TEXTURES + 1];

    Texture on, off;

    Khartoosha game;

    public SettingsMenuScreen(Khartoosha game) {
        this.game = game;
        on = new Texture("menu/menu_on.png");
        off = new Texture("menu/menu_off.png");

        //on/off buttons
        onOffButtonTextureNames[1] = "on/off_button1";
        onOffButtonTextureNames[2] = "on/off_button2";
        //static
        staticTextureNames[1] = "settings_inactive";
        staticTextureNames[2] = "music";
        staticTextureNames[3] = "soundfx";
        //dynamic
        dynamicTextureNames[1] = "back";

        //on/off buttons
        onOffButtonTextures[1] = new MenuTextureDim(ON_OFF_BUTTON1_WIDTH,ON_OFF_BUTTON1_HEIGHT,ON_OFF_BUTTON1_Y,ON_OFF_BUTTON1_X);
        onOffButtonTextures[2] = new MenuTextureDim(ON_OFF_BUTTON2_WIDTH,ON_OFF_BUTTON2_HEIGHT,ON_OFF_BUTTON2_Y,ON_OFF_BUTTON2_X);
        //static
        staticTextures[1] = new MenuTextureDimStatic(SETTINGS_BUTTON_WIDTH,SETTINGS_BUTTON_HEIGHT,SETTINGS_BUTTON_Y,SETTINGS_BUTTON_X,staticTextureNames[1]);
        staticTextures[2] = new MenuTextureDimStatic(MUSIC_WIDTH,MUSIC_HEIGHT,MUSIC_Y,MUSIC_X,staticTextureNames[2]);
        staticTextures[3] = new MenuTextureDimStatic(SOUNDFX_WIDTH,SOUNDFX_HEIGHT,SOUNDFX_Y,SOUNDFX_X,staticTextureNames[3]);
        //dynamic
        dynamicTextures[1] = new MenuTextureDimDynamic(BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT,BACK_BUTTON_Y,BACK_BUTTON_X,dynamicTextureNames[1]);

        for (int onOffTextureNum = 1; onOffTextureNum <= NUM_OF_ON_OFF_BUTTONS; onOffTextureNum++) { //TODO:(when sounds logic is ready)this need to be done only once in the create method to save settings
            isOn[onOffTextureNum] = true;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //TODO

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        displayBG(game);
        drawStatic();
        checkBoundsAndDrawOnOff(onOffButtonTextures);
        checkBoundsAndDrawDynamic(dynamicTextures,0);
        game.batch.end();
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

    @Override
    public void chosenTexture(int dynamicTextureNum) { //TODO: when sounds logic is ready
        if(dynamicTextureNum == 1){ //if back is clicked
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void drawStatic() {
        for (int staticTextureNum = 1; staticTextureNum <= NUM_OF_STATIC_TEXTURES; staticTextureNum++) {
            game.batch.draw(staticTextures[staticTextureNum].getTexture(),staticTextures[staticTextureNum].getX(),staticTextures[staticTextureNum].getY(),staticTextures[staticTextureNum].getWIDTH(),staticTextures[staticTextureNum].getHEIGHT());
        }
    }
    
    @Override
    public void checkBoundsAndDrawDynamic(MenuTextureDimDynamic[] dim, int dynamicTextureNum) {
        for (dynamicTextureNum = 1; dynamicTextureNum <= NUM_OF_DYNAMIC_TEXTURES; dynamicTextureNum++) {
            if (Gdx.input.getX() < dim[dynamicTextureNum].getX() + dim[dynamicTextureNum].getWIDTH() && Gdx.input.getX() > dim[dynamicTextureNum].getX()
                    && Khartoosha.Gheight - Gdx.input.getY() < dim[dynamicTextureNum].getY() + dim[dynamicTextureNum].getHEIGHT()
                    && Khartoosha.Gheight - Gdx.input.getY() > dim[dynamicTextureNum].getY()
            ) {
                game.batch.draw(dim[dynamicTextureNum].getActive(), dim[dynamicTextureNum].getX(), dim[dynamicTextureNum].getY(), dim[dynamicTextureNum].getWIDTH(), dim[dynamicTextureNum].getHEIGHT());
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    chosenTexture(dynamicTextureNum);
                }
            } else {
                game.batch.draw(dim[dynamicTextureNum].getInActive(), dim[dynamicTextureNum].getX(), dim[dynamicTextureNum].getY(), dim[dynamicTextureNum].getWIDTH(), dim[dynamicTextureNum].getHEIGHT());
            }
        }
    }
    
    public void checkBoundsAndDrawOnOff(MenuTextureDim[] dim){
        for (int onOffTextureNum = 1; onOffTextureNum <= NUM_OF_ON_OFF_BUTTONS; onOffTextureNum++) {
            if(isOn[onOffTextureNum]){
                game.batch.draw(on, dim[onOffTextureNum].getX(), dim[onOffTextureNum].getY(), dim[onOffTextureNum].getWIDTH(), dim[onOffTextureNum].getHEIGHT());
            } else {
                game.batch.draw(off, dim[onOffTextureNum].getX(), dim[onOffTextureNum].getY(), dim[onOffTextureNum].getWIDTH(), dim[onOffTextureNum].getHEIGHT());
            }

            if (Gdx.input.getX() < dim[onOffTextureNum].getX() + dim[onOffTextureNum].getWIDTH() && Gdx.input.getX() > dim[onOffTextureNum].getX()
                    && Khartoosha.Gheight - Gdx.input.getY() < dim[onOffTextureNum].getY() + dim[onOffTextureNum].getHEIGHT()
                    && Khartoosha.Gheight - Gdx.input.getY() > dim[onOffTextureNum].getY()
            ) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    isOn[onOffTextureNum] = !isOn[onOffTextureNum];
                }
            }
        }
    }
}
