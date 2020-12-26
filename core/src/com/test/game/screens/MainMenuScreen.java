package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDim;
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

    private static final int NUM_OF_TEXTURES = 3;
    private String[] textureNames = new String[NUM_OF_TEXTURES + 1];
    private MenuTextureDim[] textures = new MenuTextureDim[NUM_OF_TEXTURES + 1];

    Khartoosha game;

    public MainMenuScreen(Khartoosha game) {
        this.game = game;

        textureNames[1] = "play";
        textureNames[2] = "settings";
        textureNames[3] = "exit";

        textures[1] = new MenuTextureDim(PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT,PLAY_BUTTON_Y,PLAY_BUTTON_X, textureNames[1]);
        textures[2] = new MenuTextureDim(SETTINGS_BUTTON_WIDTH,SETTINGS_BUTTON_HEIGHT,SETTINGS_BUTTON_Y,SETTINGS_BUTTON_X, textureNames[2]);
        textures[3] = new MenuTextureDim(EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT,EXIT_BUTTON_Y,EXIT_BUTTON_X,textureNames[3]);
        bg = new Texture("menu/menu_bg_darker1.png");
    }

    @Override
    public void chosenTexture(int textureNum){
        if(textureNum == 1) { //if play is clicked
            this.dispose();
            game.setScreen(new PlayMenuScreen(game));
        } else if (textureNum == 2) { //if settings is clicked
            this.dispose();
            game.setScreen(new SettingsMenuScreen(game));
        } else if(textureNum == 3) { //if exit is clicked
            this.dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void checkBoundsAndDraw(MenuTextureDim[]dim, int textureNum)
    {
        if(Gdx.input.getX() < dim[textureNum].getX() + dim[textureNum].getWIDTH() && Gdx.input.getX() > dim[textureNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[textureNum].getY() + dim[textureNum].getHEIGHT()
                && Khartoosha.Gheight - Gdx.input.getY() > dim[textureNum].getY()
        )
        {
            game.batch.draw(dim[textureNum].getActive(), dim[textureNum].getX(), dim[textureNum].getY(), dim[textureNum].getWIDTH(), dim[textureNum].getHEIGHT());
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                chosenTexture(textureNum);
            }
        } else {
            game.batch.draw(dim[textureNum].getInActive(), dim[textureNum].getX(), dim[textureNum].getY(), dim[textureNum].getWIDTH(), dim[textureNum].getHEIGHT());
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

        for (int textureNum = 1; textureNum <= NUM_OF_TEXTURES; textureNum++){
            checkBoundsAndDraw(textures, textureNum);
        }

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
        for (int textureNum = 1; textureNum <= NUM_OF_TEXTURES; textureNum++){
            textures[textureNum].dispose();
        }
        bg.dispose();
    }
}
