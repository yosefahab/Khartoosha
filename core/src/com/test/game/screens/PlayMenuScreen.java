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
import com.test.game.menu.MenuTextureDimDynamic;
import com.test.game.menu.MenuTextureDimStatic;
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

    private static final int NUM_OF_DYNAMIC_TEXTURES = 3;
    private String[] dynamicTextureNames = new String[NUM_OF_DYNAMIC_TEXTURES + 1];
    private MenuTextureDimDynamic[] dynamicTextures = new MenuTextureDimDynamic[NUM_OF_DYNAMIC_TEXTURES + 1];
    
    Khartoosha game;

    public PlayMenuScreen(Khartoosha game) {
        this.game = game;
        
        dynamicTextureNames[1] = "onePlayer";
        dynamicTextureNames[2] = "twoPlayer";
        dynamicTextureNames[3] = "back";
        
        dynamicTextures[1] = new MenuTextureDimDynamic(ONE_PLAYER_BUTTON_WIDTH,ONE_PLAYER_BUTTON_HEIGHT,ONE_PLAYER_BUTTON_Y,ONE_PLAYER_BUTTON_X, dynamicTextureNames[1]);
        dynamicTextures[2] = new MenuTextureDimDynamic(TWO_PLAYER_BUTTON_WIDTH,TWO_PLAYER_BUTTON_HEIGHT,TWO_PLAYER_BUTTON_Y,TWO_PLAYER_BUTTON_X, dynamicTextureNames[2]);
        dynamicTextures[3] = new MenuTextureDimDynamic(BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT,BACK_BUTTON_Y,BACK_BUTTON_X, dynamicTextureNames[3]);
    }

    @Override
    public void chosenTexture(int dynamicTextureNum) {
        if(dynamicTextureNum == 1) { //if one player is clicked
            this.dispose();
            game.setScreen(new CharacterChoiceMenuScreen(game, false));
        } else if (dynamicTextureNum == 2) { //if two players is clicked
            this.dispose();
            game.setScreen(new CharacterChoiceMenuScreen(game, true));
        } else if(dynamicTextureNum == 3) { //if back is clicked
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void drawStatic() {
        //no static textures
    }

    @Override
    public void checkBoundsAndDrawDynamic(MenuTextureDimDynamic[] dim, int dynamicTextureNum) {
        if(Gdx.input.getX() < dim[dynamicTextureNum].getX() + dim[dynamicTextureNum].getWIDTH() && Gdx.input.getX() > dim[dynamicTextureNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[dynamicTextureNum].getY() + dim[dynamicTextureNum].getHEIGHT()
                && Khartoosha.Gheight - Gdx.input.getY() > dim[dynamicTextureNum].getY()
        )
        {
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
        for (int dynamicTextureNum = 1; dynamicTextureNum <= NUM_OF_DYNAMIC_TEXTURES; dynamicTextureNum++){
            dynamicTextures[dynamicTextureNum].dispose();
        }
        bg.dispose();
    }
}
