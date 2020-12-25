package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;

public class CharacterChoiceMenuScreen implements Screen {
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
        
        //char1
        if(Gdx.input.getX() < CHAR_ONE_X  + CHAR_ONE_WIDTH && Gdx.input.getX() > CHAR_ONE_X
                && Khartoosha.Gheight - Gdx.input.getY() < CHAR_ONE_Y + CHAR_ONE_HEIGHT && Khartoosha.Gheight - Gdx.input.getY() > CHAR_ONE_Y)
        {
            game.batch.draw(char1Active, CHAR_ONE_X, CHAR_ONE_Y, CHAR_ONE_WIDTH, CHAR_ONE_HEIGHT);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //TODO
                
                if(!twoPlayers){
                    char1Num = 1;
                    this.dispose();
                    game.setScreen(new PlayScreen(game, char1Num, 0));
                } else{
                    if(firstTime) {
                        char1Num = 1;
                        firstTime = false;
                        firstCharHeader = secondCharHeader;
                    } else{
                        char2Num = 1;
                        //TODO: uncomment when PlayScreen() is overloaded
                        //this.dispose();
                        game.setScreen(new PlayScreen(game, char1Num, char2Num,0));
                    }
                }
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

                if(!twoPlayers){
                    char1Num = 2;
                    this.dispose();
                    game.setScreen(new PlayScreen(game, char1Num, 0));
                } else{
                    if(firstTime) {
                        char1Num = 2;
                        firstTime = false;
                        firstCharHeader = secondCharHeader;
                    } else{
                        char2Num = 2;
                        //TODO: uncomment when PlayScreen() is overloaded
                        //this.dispose();
                        game.setScreen(new PlayScreen(game, char1Num, char2Num,0));
                    }

                }
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

                if(!twoPlayers){
                    char1Num = 3;
                    this.dispose();
                    game.setScreen(new PlayScreen(game, char1Num, 0));
                } else{
                    if(firstTime) {
                        char1Num = 3;
                        firstTime = false;
                        firstCharHeader = secondCharHeader;
                    } else{
                        char2Num = 3;
                        //TODO: uncomment when PlayScreen() is overloaded
                        //this.dispose();
                        game.setScreen(new PlayScreen(game, char1Num, char2Num,0));
                    }

                }
            }
        } else {
            game.batch.draw(char3InActive, CHAR_THREE_X, CHAR_THREE_Y, CHAR_THREE_WIDTH, CHAR_THREE_HEIGHT);
        }
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
