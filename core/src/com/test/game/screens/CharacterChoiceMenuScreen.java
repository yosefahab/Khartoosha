package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuTextureDim;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextures;

public class CharacterChoiceMenuScreen extends MenuBG implements Screen, MenuTextures {
    private static final int MARGIN = 50;

    private static final int HEADER_WIDTH = 720;
    private static final int HEADER_HEIGHT = 60;
    private static final int HEADER_Y = (int) (Khartoosha.Gheight - 100);
    private static final int HEADER_X = (int) ((Khartoosha.Gwidth / 2) - (HEADER_WIDTH / 2));

    private static final int FIRST_SECOND_WIDTH = HEADER_WIDTH;
    private static final int FIRST_SECOND_HEIGHT = HEADER_HEIGHT;
    private static final int FIRST_SECOND_Y = HEADER_Y;
    private static final int FIRST_SECOND_X = HEADER_X;

    private static final int CHAR_ONE_WIDTH = 187;
    private static final int CHAR_ONE_HEIGHT = 160;
    private static final int CHAR_ONE_Y = HEADER_Y - HEADER_HEIGHT - 70 + (HEADER_HEIGHT-CHAR_ONE_HEIGHT);
    private static final int CHAR_ONE_X = (int) ((Khartoosha.Gwidth / 3) - (CHAR_ONE_WIDTH / 2) - 2*MARGIN);

    private static final int CHAR_TWO_WIDTH = CHAR_ONE_WIDTH;
    private static final int CHAR_TWO_HEIGHT = CHAR_ONE_HEIGHT;
    private static final int CHAR_TWO_Y = CHAR_ONE_Y;
    private static final int CHAR_TWO_X = (int) ((Khartoosha.Gwidth / 3) - (CHAR_TWO_WIDTH / 2) + CHAR_ONE_X + MARGIN);

    private static final int CHAR_THREE_WIDTH = CHAR_ONE_WIDTH;
    private static final int CHAR_THREE_HEIGHT = CHAR_ONE_HEIGHT;
    private static final int CHAR_THREE_Y = CHAR_ONE_Y;
    private static final int CHAR_THREE_X = (int) ((Khartoosha.Gwidth / 3) - (CHAR_THREE_WIDTH / 2) + CHAR_TWO_X + MARGIN);

    private static final int BACK_BUTTON_WIDTH = 230;
    private static final int BACK_BUTTON_HEIGHT = 77;
    private static final int BACK_BUTTON_Y = CHAR_THREE_Y - CHAR_THREE_HEIGHT - 60 - (BACK_BUTTON_HEIGHT-CHAR_THREE_HEIGHT);
    private static final int BACK_BUTTON_X = (int) ((Khartoosha.Gwidth / 2) - (BACK_BUTTON_WIDTH / 2));
    
    private static final int NUM_OF_TEXTURES = 1;
    private MenuTextureDim[] Char = new MenuTextureDim[Khartoosha.NUM_OF_CHARS + NUM_OF_TEXTURES + 1]; //array containing all character info
    private String[] charNames = new String[Khartoosha.NUM_OF_CHARS + NUM_OF_TEXTURES + 1]; //textureNames[] here is charNames[]

    private Texture header;
    private Texture firstCharHeader;
    private Texture secondCharHeader;
    
    private Khartoosha game;

    private boolean twoPlayers;
    
    private int char1Num, char2Num;
    
    private boolean firstTime;

    public CharacterChoiceMenuScreen(Khartoosha game, boolean twoPlayers) {
        this.game = game;
        this.twoPlayers = twoPlayers;
        header = new Texture("menu/menu_choose_your_char.png");
        firstCharHeader = new Texture("menu/menu_first_char.png");
        secondCharHeader = new Texture("menu/menu_second_char.png");
        firstTime = true;

        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS; charNum++) {
            charNames[charNum] = "char"+charNum;
        }

        charNames[Khartoosha.NUM_OF_CHARS + 1] = "back";

        //below, the index of Char[] is the same as textureNames[] (the last parameter)
        //Char[1] = new MenuTextureDim(187, 160, HEADER_Y - HEADER_HEIGHT - 70 + (HEADER_HEIGHT-CHAR_ONE_HEIGHT), (int) ((Khartoosha.Gwidth / 3) - (CHAR_ONE_WIDTH / 2) - 2*MARGIN), 1);
        Char[1] = new MenuTextureDim(CHAR_ONE_WIDTH,CHAR_ONE_HEIGHT,CHAR_ONE_Y, CHAR_ONE_X,charNames[1]);
        Char[2] = new MenuTextureDim(CHAR_TWO_WIDTH,CHAR_TWO_HEIGHT,CHAR_TWO_Y, CHAR_TWO_X,charNames[2]);
        Char[3] = new MenuTextureDim(CHAR_THREE_WIDTH,CHAR_THREE_HEIGHT,CHAR_THREE_Y, CHAR_THREE_X,charNames[3]);
        Char[Khartoosha.NUM_OF_CHARS + 1] = new MenuTextureDim(BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT,BACK_BUTTON_Y,BACK_BUTTON_X,charNames[Khartoosha.NUM_OF_CHARS + 1]);

        bg = new Texture("menu/menu_bg_darker1.png");
    }

    @Override
    public void chosenTexture(int charNum)
    {
        if(charNum == Khartoosha.NUM_OF_CHARS + 1){ //back was clicked
            this.dispose();
            game.setScreen(new PlayMenuScreen(game));
        } else if(!twoPlayers){
            char1Num = charNum;
            this.dispose();
            game.setScreen(new PlayScreen(game, 0, char1Num));
        } else{
            if(firstTime) {
                char1Num = charNum;
                firstTime = false;
                firstCharHeader = secondCharHeader; // changing the header of the screen
            } else{
                char2Num = charNum;
                this.dispose();
                game.setScreen(new PlayScreen(game, 0,char1Num, char2Num));
            }
        }
    }

    @Override
    public void checkBoundsAndDraw(MenuTextureDim[]dim, int charNum)
    {
        if(Gdx.input.getX() < dim[charNum].getX() + dim[charNum].getWIDTH() && Gdx.input.getX() > dim[charNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[charNum].getY() + dim[charNum].getHEIGHT()
                && Khartoosha.Gheight - Gdx.input.getY() > dim[charNum].getY()
        )
        {
            game.batch.draw(dim[charNum].getActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                chosenTexture(charNum);
            }
        } else {
            game.batch.draw(dim[charNum].getInActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
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
        if(!twoPlayers) {
            //header
            game.batch.draw(header, HEADER_X, HEADER_Y, HEADER_WIDTH, HEADER_HEIGHT);
        } else {
            game.batch.draw(firstCharHeader,FIRST_SECOND_X,FIRST_SECOND_Y,FIRST_SECOND_WIDTH,FIRST_SECOND_HEIGHT);
        }

        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS + NUM_OF_TEXTURES; charNum++)
        {
            checkBoundsAndDraw(Char,charNum);
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
    public void hide() { }

    @Override
    public void dispose() {
        header.dispose();
        firstCharHeader.dispose();
        secondCharHeader.dispose();
        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS; charNum++) {
            Char[charNum].dispose();
        }
        bg.dispose();
    }
}
