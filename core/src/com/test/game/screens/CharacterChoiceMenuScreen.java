package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.MenuCharDim;

public class CharacterChoiceMenuScreen implements Screen {

    static final int NUM_OF_CHARS = 3;

    MenuCharDim[] Char = new MenuCharDim[NUM_OF_CHARS + 1];
    Texture tex1 = new Texture("menu/menu_choose_your_char.png");
    Texture tex2 = new Texture("menu/menu_choose_your_char.png");

     //(720, 60, (int) (Khartoosha.Gheight - 100), (int) ((Khartoosha.Gwidth / 2) - (HEADER_WIDTH / 2)));


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
    
    private int currDimWidth; //these are set by the setCurrDim() method
    private int currDimHeight;
    private int currDimY;
    private int currDimX;

    Texture header;
    Texture firstCharHeader;
    Texture secondCharHeader;
    Texture char1Active;
    Texture char1InActive;
    Texture char2Active;
    Texture char2InActive;
    Texture char3Active;
    Texture char3InActive;
    
    Khartoosha game;

    boolean twoPlayers;
    
    int char1Num, char2Num;
    
    boolean firstTime;

    private void chooseChar(int charNum)
    {
        if(!twoPlayers){
            char1Num = charNum;
            this.dispose();
            game.setScreen(new PlayScreen(game, char1Num, 0));
        } else{
            if(firstTime) {
                char1Num = charNum;
                firstTime = false;
                firstCharHeader = secondCharHeader;
            } else{
                char2Num = charNum;
                //TODO: uncomment when PlayScreen() is overloaded
                //this.dispose();
                game.setScreen(new PlayScreen(game, char1Num, char2Num,0));
            }
        }
    }

    private void setCurrDim(int width, int height, int y, int x)
    {
        currDimWidth = width;
        currDimHeight = height;
        currDimY = y;
        currDimX = x;
    }

    private void setCurrChar(int charNum){
        if(charNum == 1)
        {
            setCurrDim(CHAR_ONE_WIDTH,CHAR_ONE_HEIGHT,CHAR_ONE_Y,CHAR_ONE_X);
        }
        else if(charNum == 2)
        {
            setCurrDim(CHAR_TWO_WIDTH,CHAR_TWO_HEIGHT,CHAR_TWO_Y,CHAR_TWO_X);
        }
        //TODO
        //else if

    }
    void checkBounds(MenuCharDim []dim, int charNum)
    {
        if(Gdx.input.getX() < dim[charNum].getX() + dim[charNum].getWIDTH() && Gdx.input.getX() > dim[charNum].getX()
                && Khartoosha.Gheight - Gdx.input.getY() < dim[charNum].getY() + dim[charNum].getHEIGHT() 
                && Khartoosha.Gheight - Gdx.input.getY() > dim[charNum].getY()
        )
        {
            game.batch.draw(dim[charNum].getActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //TODO
                chooseChar(charNum);

            }
        } else {
            game.batch.draw(dim[charNum].getInActive(), dim[charNum].getX(), dim[charNum].getY(), dim[charNum].getWIDTH(), dim[charNum].getHEIGHT());
        }
    }
    
    public CharacterChoiceMenuScreen(Khartoosha game, boolean twoPlayers) {
        this.game = game;
        this.twoPlayers = twoPlayers;
        header = new Texture("menu/menu_choose_your_char.png");
        firstCharHeader = new Texture("menu/menu_first_char.png");
        secondCharHeader = new Texture("menu/menu_second_char.png");
        char1Active = new Texture("menu/menu_char1_active.png");
        char1InActive = new Texture("menu/menu_char1_inactive.png");
        char2Active = new Texture("menu/menu_char2_active.png");
        char2InActive = new Texture("menu/menu_char2_inactive.png");
        char3Active = new Texture("menu/menu_char3_active.png");
        char3InActive = new Texture("menu/menu_char3_inactive.png");
        firstTime = true;

        Char[1] = new  MenuCharDim(187, 160, HEADER_Y - HEADER_HEIGHT - 70 + (HEADER_HEIGHT-CHAR_ONE_HEIGHT), (int) ((Khartoosha.Gwidth / 3) - (CHAR_ONE_WIDTH / 2) - 2*MARGIN), char1Active, char1InActive);
        Char[2] = new MenuCharDim(CHAR_TWO_WIDTH,CHAR_TWO_HEIGHT,CHAR_TWO_Y, CHAR_TWO_X,char2Active,char2InActive );
        Char[3] = new MenuCharDim(CHAR_THREE_WIDTH,CHAR_THREE_HEIGHT,CHAR_THREE_Y, CHAR_THREE_X,char3Active,char3InActive );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        if(!twoPlayers) {
            //header
            game.batch.draw(header, HEADER_X, HEADER_Y, HEADER_WIDTH, HEADER_HEIGHT);
        } else {
            game.batch.draw(firstCharHeader,FIRST_SECOND_X,FIRST_SECOND_Y,FIRST_SECOND_WIDTH,FIRST_SECOND_HEIGHT);
        }

        for(int i = 1; i<=NUM_OF_CHARS; i++)
        {
            checkBounds(Char,i);
        }

        /*
        //char1
        if(Gdx.input.getX() < CHAR_ONE_X  + CHAR_ONE_WIDTH && Gdx.input.getX() > CHAR_ONE_X
                && Khartoosha.Gheight - Gdx.input.getY() < CHAR_ONE_Y + CHAR_ONE_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > CHAR_ONE_Y)
        {
            game.batch.draw(char1Active, CHAR_ONE_X, CHAR_ONE_Y, CHAR_ONE_WIDTH, CHAR_ONE_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //TODO
                chooseChar(1);

            }
        } else {
            game.batch.draw(char1InActive, CHAR_ONE_X, CHAR_ONE_Y, CHAR_ONE_WIDTH, CHAR_ONE_HEIGHT);
        }
        
        //char2
        if(Gdx.input.getX() < CHAR_TWO_X  + CHAR_TWO_WIDTH && Gdx.input.getX() > CHAR_TWO_X
                && Khartoosha.Gheight - Gdx.input.getY() < CHAR_TWO_Y + CHAR_TWO_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > CHAR_TWO_Y)
        {
            game.batch.draw(char2Active, CHAR_TWO_X, CHAR_TWO_Y, CHAR_TWO_WIDTH, CHAR_TWO_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //TODO
                chooseChar(2);
            }
        } else {
            game.batch.draw(char2InActive, CHAR_TWO_X, CHAR_TWO_Y, CHAR_TWO_WIDTH, CHAR_TWO_HEIGHT);
        }
        
        //char3
        if(Gdx.input.getX() < CHAR_THREE_X  + CHAR_THREE_WIDTH && Gdx.input.getX() > CHAR_THREE_X
                && Khartoosha.Gheight - Gdx.input.getY() < CHAR_THREE_Y + CHAR_THREE_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > CHAR_THREE_Y)
        {
            game.batch.draw(char3Active, CHAR_THREE_X, CHAR_THREE_Y, CHAR_THREE_WIDTH, CHAR_THREE_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //TODO
                chooseChar(3);
            }
        } else {
            game.batch.draw(char3InActive, CHAR_THREE_X, CHAR_THREE_Y, CHAR_THREE_WIDTH, CHAR_THREE_HEIGHT);
        }
        */

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
}
