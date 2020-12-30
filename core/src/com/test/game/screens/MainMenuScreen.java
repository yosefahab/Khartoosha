package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDimDynamic;
import com.test.game.menu.MenuTextureDimStatic;
import com.test.game.menu.MenuTextures;

public class MainMenuScreen extends MenuBG implements Screen, MenuTextures
{
    private static final int PLAY_BUTTON_WIDTH = 340;
    private static final int PLAY_BUTTON_HEIGHT = 145;
    private static final int PLAY_BUTTON_Y = (int) (Khartoosha.Gheight - 160);
    private static final int PLAY_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (PLAY_BUTTON_WIDTH / 2));

    private static final int SETTINGS_BUTTON_WIDTH = 300;
    private static final int SETTINGS_BUTTON_HEIGHT = 80;
    private static final int SETTINGS_BUTTON_Y = PLAY_BUTTON_Y - PLAY_BUTTON_HEIGHT - 50 + (PLAY_BUTTON_HEIGHT-SETTINGS_BUTTON_HEIGHT);
    private static final int SETTINGS_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (SETTINGS_BUTTON_WIDTH / 2));

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 100;
    private static final int EXIT_BUTTON_Y = SETTINGS_BUTTON_Y - SETTINGS_BUTTON_HEIGHT - 50 - (EXIT_BUTTON_HEIGHT-SETTINGS_BUTTON_HEIGHT);
    private static final int EXIT_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (EXIT_BUTTON_WIDTH / 2));

    private static final int NUM_OF_DYNAMIC_TEXTURES = 3;
    private String[] dynamicTextureNames = new String[NUM_OF_DYNAMIC_TEXTURES + 1];
    private MenuTextureDimDynamic[] dynamicTextures = new MenuTextureDimDynamic[NUM_OF_DYNAMIC_TEXTURES + 1];
    
    private static int currDynamicTexture = 0;

    Khartoosha game;

    public MainMenuScreen(Khartoosha game) {
        this.game = game;

        dynamicTextureNames[1] = "play";
        dynamicTextureNames[2] = "settings";
        dynamicTextureNames[3] = "exit";

        dynamicTextures[1] = new MenuTextureDimDynamic(PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT,PLAY_BUTTON_Y,PLAY_BUTTON_X, dynamicTextureNames[1]);
        dynamicTextures[2] = new MenuTextureDimDynamic(SETTINGS_BUTTON_WIDTH,SETTINGS_BUTTON_HEIGHT,SETTINGS_BUTTON_Y,SETTINGS_BUTTON_X, dynamicTextureNames[2]);
        dynamicTextures[3] = new MenuTextureDimDynamic(EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT,EXIT_BUTTON_Y,EXIT_BUTTON_X,dynamicTextureNames[3]);
    }
    
    void handleKeyboard(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(currDynamicTexture <= 1){
                currDynamicTexture = NUM_OF_DYNAMIC_TEXTURES;
            } else {
                currDynamicTexture--;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(currDynamicTexture >= NUM_OF_DYNAMIC_TEXTURES){
                currDynamicTexture = 1;
            } else {
                currDynamicTexture++;
            }
        }
        if(currDynamicTexture >= 1 && currDynamicTexture <= NUM_OF_DYNAMIC_TEXTURES) {

            game.batch.draw(dynamicTextures[currDynamicTexture].getActive(), dynamicTextures[currDynamicTexture].getX(), dynamicTextures[currDynamicTexture].getY(), dynamicTextures[currDynamicTexture].getWIDTH(), dynamicTextures[currDynamicTexture].getHEIGHT());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            chosenTexture(currDynamicTexture);
        }
    }

    @Override
    public void chosenTexture(int dynamicTextureNum){
        if(dynamicTextureNum == 1) { //if play is clicked
            this.dispose();
            game.setScreen(new PlayMenuScreen(game));
        } else if (dynamicTextureNum == 2) { //if settings is clicked
            this.dispose();
            game.setScreen(new SettingsMenuScreen(game));
        } else if(dynamicTextureNum == 3) { //if exit is clicked
            this.dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void drawStatic() {
        //No static textures
    }

    @Override
    public void checkBoundsAndDrawDynamic(MenuTextureDimDynamic[]dim, int dynamicTextureNum)
    {
        if(Gdx.input.getX() < dim[dynamicTextureNum].getX() + dim[dynamicTextureNum].getWIDTH() && Gdx.input.getX() > dim[dynamicTextureNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[dynamicTextureNum].getY() + dim[dynamicTextureNum].getHEIGHT()
                && Khartoosha.Gheight - Gdx.input.getY() > dim[dynamicTextureNum].getY()
        )
        {
            currDynamicTexture = 0; // if mouse is active disable handle keyboard
            game.batch.draw(dim[dynamicTextureNum].getActive(), dim[dynamicTextureNum].getX(), dim[dynamicTextureNum].getY(), dim[dynamicTextureNum].getWIDTH(), dim[dynamicTextureNum].getHEIGHT());
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                chosenTexture(dynamicTextureNum);
            }
        } else {
            game.batch.draw(dim[dynamicTextureNum].getInActive(), dim[dynamicTextureNum].getX(), dim[dynamicTextureNum].getY(), dim[dynamicTextureNum].getWIDTH(), dim[dynamicTextureNum].getHEIGHT());
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        displayBG(game);
        for (int dynamicTextureNum = 1; dynamicTextureNum <= NUM_OF_DYNAMIC_TEXTURES; dynamicTextureNum++){
            checkBoundsAndDrawDynamic(dynamicTextures, dynamicTextureNum);
        }
        handleKeyboard();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        for (int dynamicTextureNum = 1; dynamicTextureNum <= NUM_OF_DYNAMIC_TEXTURES; dynamicTextureNum++){
            dynamicTextures[dynamicTextureNum].dispose();
        }
        bg.dispose();
    }
}
