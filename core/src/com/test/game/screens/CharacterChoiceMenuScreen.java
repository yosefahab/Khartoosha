package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.menu.MenuTextureDimDynamic;
import com.test.game.menu.MenuBG;
import com.test.game.menu.MenuTextureDimStatic;
import com.test.game.menu.MenuTextures;
import com.test.game.soundEffects;

public class CharacterChoiceMenuScreen extends MenuBG implements Screen, MenuTextures
{
    private static final int MARGIN = 50;

    private static final int HEADER_WIDTH = 720;
    private static final int HEADER_HEIGHT = 60;
    private static final int HEADER_Y = (int) (Khartoosha.Gheight - 200);
    private static final int HEADER_X = (int) ((Khartoosha.Gwidth / 2) - (HEADER_WIDTH / 2));

    private static final int FIRST_SECOND_HEADER_WIDTH = HEADER_WIDTH;
    private static final int FIRST_SECOND_HEADER_HEIGHT = HEADER_HEIGHT;
    private static final int FIRST_SECOND_HEADER_Y = HEADER_Y;
    private static final int FIRST_SECOND_HEADER_X = HEADER_X;

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
    
    private static final int NUM_OF_DYNAMIC_TEXTURES = 1;
    private MenuTextureDimDynamic[] Char = new MenuTextureDimDynamic[Khartoosha.NUM_OF_CHARS + NUM_OF_DYNAMIC_TEXTURES + 1]; //array containing all character info
    private String[] charNames = new String[Khartoosha.NUM_OF_CHARS + NUM_OF_DYNAMIC_TEXTURES + 1]; //textureNames[] here is charNames[]

    private static final int NUM_OF_STATIC_TEXTURES = 3;
    private String[] staticTextureNames = new String[NUM_OF_STATIC_TEXTURES + 1];
    private MenuTextureDimStatic[] staticTextures = new MenuTextureDimStatic[NUM_OF_STATIC_TEXTURES + 1];

    private Khartoosha game;

    private boolean twoPlayers;

    private static int currHeader = 1;

    private static int currDynamicTexture = 0;

    private int char1Num, char2Num;
    
    private boolean firstTime;


    public CharacterChoiceMenuScreen(Khartoosha game, boolean twoPlayers) {
        this.game = game;
        this.twoPlayers = twoPlayers;
        firstTime = true;

        //setting static texture names
        staticTextureNames[1] = "choose_your_char";
        staticTextureNames[2] = "first_char";
        staticTextureNames[3] = "second_char";
        //setting dynamic texture names
        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS; charNum++) {
            charNames[charNum] = "char"+charNum;
        }
        charNames[Khartoosha.NUM_OF_CHARS + 1] = "back";

        //below, the index of Char[] is the same as textureNames[] (the last parameter)
        //Char[1] = new MenuTextureDimDynamic(187, 160, HEADER_Y - HEADER_HEIGHT - 70 + (HEADER_HEIGHT-CHAR_ONE_HEIGHT), (int) ((Khartoosha.Gwidth / 3) - (CHAR_ONE_WIDTH / 2) - 2*MARGIN), 1);
        Char[1] = new MenuTextureDimDynamic(CHAR_ONE_WIDTH,CHAR_ONE_HEIGHT,CHAR_ONE_Y, CHAR_ONE_X,charNames[1]);
        Char[2] = new MenuTextureDimDynamic(CHAR_TWO_WIDTH,CHAR_TWO_HEIGHT,CHAR_TWO_Y, CHAR_TWO_X,charNames[2]);
        Char[3] = new MenuTextureDimDynamic(CHAR_THREE_WIDTH,CHAR_THREE_HEIGHT,CHAR_THREE_Y, CHAR_THREE_X,charNames[3]);
        Char[Khartoosha.NUM_OF_CHARS + 1] = new MenuTextureDimDynamic(BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT,BACK_BUTTON_Y,BACK_BUTTON_X,charNames[Khartoosha.NUM_OF_CHARS + 1]);
        staticTextures[1] = new MenuTextureDimStatic(HEADER_WIDTH,HEADER_HEIGHT,HEADER_Y,HEADER_X,staticTextureNames[1]);
        staticTextures[2] = new MenuTextureDimStatic(FIRST_SECOND_HEADER_WIDTH,FIRST_SECOND_HEADER_HEIGHT,FIRST_SECOND_HEADER_Y,FIRST_SECOND_HEADER_X,staticTextureNames[2]);
        staticTextures[3] = new MenuTextureDimStatic(FIRST_SECOND_HEADER_WIDTH,FIRST_SECOND_HEADER_HEIGHT,FIRST_SECOND_HEADER_Y,FIRST_SECOND_HEADER_X,staticTextureNames[3]);
    }

    void handleKeyboard(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(currDynamicTexture <= 1){
                currDynamicTexture = Khartoosha.NUM_OF_CHARS;
            } else {
                currDynamicTexture--;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(currDynamicTexture >= Khartoosha.NUM_OF_CHARS){
                currDynamicTexture = 1;
            } else {
                currDynamicTexture++;
            }
        }
        if(currDynamicTexture >= 1 && currDynamicTexture <= Khartoosha.NUM_OF_CHARS) {

            Khartoosha.batch.draw(Char[currDynamicTexture].getActive(), Char[currDynamicTexture].getX(), Char[currDynamicTexture].getY(), Char[currDynamicTexture].getWIDTH(), Char[currDynamicTexture].getHEIGHT());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            chosenTexture(currDynamicTexture);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            chosenTexture(Khartoosha.NUM_OF_CHARS + 1); //click back
        }
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

            } else{
                char2Num = charNum;
                this.dispose();
                game.setScreen(new PlayScreen(game, 0,char1Num, char2Num));
            }
        }
    }

    @Override
    public void drawStatic() {
        if(!twoPlayers){
            //choose your character
            currHeader = 1;
        } else {
            if (firstTime) {
                //1st character
                currHeader = 2;

            } else {
                //2nd character
                currHeader = 3;
            }
        }
        Khartoosha.batch.draw(staticTextures[currHeader].getTexture(),staticTextures[currHeader].getX(),staticTextures[currHeader].getY(), staticTextures[currHeader].getWIDTH(), staticTextures[currHeader].getHEIGHT());
    }

    @Override
    public void checkBoundsAndDrawDynamic(MenuTextureDimDynamic[]dim, int charNum)
    {
        if(Gdx.input.getX() < dim[charNum].getX() + dim[charNum].getWIDTH() && Gdx.input.getX() > dim[charNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[charNum].getY() + dim[charNum].getHEIGHT()
                && Khartoosha.Gheight - Gdx.input.getY() > dim[charNum].getY()
        )
        {
            currDynamicTexture = 0; // if mouse is active disable handle keyboard
            Khartoosha.batch.draw(dim[charNum].getActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
            Khartoosha.batch.draw(dim[charNum].getInActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                chosenTexture(charNum);
                soundEffects.click();
            }
        }
        else {

            Khartoosha.batch.draw(dim[charNum].getInActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
        }
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Khartoosha.batch.begin();
        displayBG(game);
        drawStatic();
        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS + NUM_OF_DYNAMIC_TEXTURES; charNum++)
        {
            checkBoundsAndDrawDynamic(Char,charNum);
        }
        handleKeyboard();
        Khartoosha.batch.end();
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
        for (int staticTextureNum = 1; staticTextureNum < NUM_OF_STATIC_TEXTURES; staticTextureNum++) {
            staticTextures[staticTextureNum].dispose();
        }

        for(int charNum = 1; charNum <= Khartoosha.NUM_OF_CHARS; charNum++) {
            Char[charNum].dispose();
        }


        bg.dispose();
    }
}
