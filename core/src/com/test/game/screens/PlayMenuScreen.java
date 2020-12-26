package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDim;
import com.test.game.menu.MenuTextures;

public class PlayMenuScreen extends MenuBG implements Screen, MenuTextures {
    private static final int ONE_PLAYER_BUTTON_WIDTH = 350;
    private static final int ONE_PLAYER_BUTTON_HEIGHT = 155;
    private static final int ONE_PLAYER_BUTTON_Y = (int) (Khartoosha.Gheight - 170);
    private static final int ONE_PLAYER_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (ONE_PLAYER_BUTTON_WIDTH / 2));

    private static final int TWO_PLAYER_BUTTON_WIDTH = 340;
    private static final int TWO_PLAYER_BUTTON_HEIGHT = 145;
    private static final int TWO_PLAYER_BUTTON_Y = ONE_PLAYER_BUTTON_Y - ONE_PLAYER_BUTTON_HEIGHT - 20 + (ONE_PLAYER_BUTTON_HEIGHT-TWO_PLAYER_BUTTON_HEIGHT);
    private static final int TWO_PLAYER_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (TWO_PLAYER_BUTTON_WIDTH / 2));

    private static final int BACK_BUTTON_WIDTH = 230;
    private static final int BACK_BUTTON_HEIGHT = 77;
    private static final int BACK_BUTTON_Y = TWO_PLAYER_BUTTON_Y - TWO_PLAYER_BUTTON_HEIGHT - 40 - (BACK_BUTTON_HEIGHT-TWO_PLAYER_BUTTON_HEIGHT);
    private static final int BACK_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (BACK_BUTTON_WIDTH / 2));

    private static final int NUM_OF_TEXTURES = 3;
    private String[] textureNames = new String[NUM_OF_TEXTURES + 1];
    private MenuTextureDim[] textures = new MenuTextureDim[NUM_OF_TEXTURES + 1];
    
    Khartoosha game;

    public PlayMenuScreen(Khartoosha game) {
        this.game = game;
        
        textureNames[1] = "onePlayer";
        textureNames[2] = "twoPlayer";
        textureNames[3] = "back";
        
        textures[1] = new MenuTextureDim(ONE_PLAYER_BUTTON_WIDTH,ONE_PLAYER_BUTTON_HEIGHT,ONE_PLAYER_BUTTON_Y,ONE_PLAYER_BUTTON_X, textureNames[1]);
        textures[2] = new MenuTextureDim(TWO_PLAYER_BUTTON_WIDTH,TWO_PLAYER_BUTTON_HEIGHT,TWO_PLAYER_BUTTON_Y,TWO_PLAYER_BUTTON_X, textureNames[2]);
        textures[3] = new MenuTextureDim(BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT,BACK_BUTTON_Y,BACK_BUTTON_X, textureNames[3]);

        bg = new Texture("menu/menu_bg_darker1.png");
    }

    @Override
    public void chosenTexture(int textureNum) {
        if(textureNum == 1) { //if one player is clicked
            this.dispose();
            game.setScreen(new CharacterChoiceMenuScreen(game, false));
        } else if (textureNum == 2) { //if two players is clicked
            this.dispose();
            game.setScreen(new CharacterChoiceMenuScreen(game, true));
        } else if(textureNum == 3) { //if back is clicked
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void checkBoundsAndDraw(MenuTextureDim[] dim, int textureNum) {
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
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

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
