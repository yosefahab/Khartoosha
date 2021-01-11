package com.test.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Khartoosha;
import com.test.game.screens.MainMenuScreen;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Camera;

public class PauseMenu{
    private static float resumeWidth;
    private static float resumeHeight;
    private static float resumeX;
    private static float resumeY;

    private static float exitWidth;
    private static float exitHeight;
    private static float exitX;
    private static float exitY;

    private static float pauseBGWidth;
    private static float pauseBGHeight;
    private static float pauseBGX;
    private static float pauseBGY;
    private static Texture pauseBGTexture;

    public static final int NUM_OF_DYNAMIC_TEXTURES = 2;
    public static String[] textureNames = new String[NUM_OF_DYNAMIC_TEXTURES + 1];
    public static MenuTextureDimDynamic[] textures = new MenuTextureDimDynamic[NUM_OF_DYNAMIC_TEXTURES + 1];

    private static int currDynamicTexture = 1;

    private static PlayScreen currPlayScreen;

    private static Khartoosha currGame;

    public static void displayPauseScreen(Khartoosha game, Camera camera, PlayScreen playScreen)
    {
        Vector2 vec;
        currPlayScreen = playScreen;
        currGame = game;
        vec = camera.getMid();

        resumeWidth = 400F / Khartoosha.PPM;
        resumeHeight =  100F / Khartoosha.PPM;
        resumeX = vec.x - (resumeWidth / 2);
        resumeY = vec.y + (80F / Khartoosha.PPM / 2);

        exitWidth = 300F / Khartoosha.PPM;
        exitHeight =  100F / Khartoosha.PPM;
        exitX = vec.x - (exitWidth / 2);
        exitY = resumeY - (400F / Khartoosha.PPM / 2);

        pauseBGWidth = 449F / Khartoosha.PPM;
        pauseBGHeight =  450F / Khartoosha.PPM;
        pauseBGX = vec.x - (pauseBGWidth / 2);
        pauseBGY = vec.y - (pauseBGHeight / 2);
        
        textures[1].setPos(resumeY,resumeX);
        textures[1].setDim(resumeWidth,resumeHeight);
        
        textures[2].setPos(exitY,exitX);
        textures[2].setDim(exitWidth,exitHeight);

        game.batch.draw(pauseBGTexture, pauseBGX, pauseBGY, pauseBGWidth, pauseBGHeight);

        for (int textureNum = 1; textureNum <= NUM_OF_DYNAMIC_TEXTURES; textureNum++)
        {
            game.batch.draw(textures[textureNum].getInActive(), textures[textureNum].getX(), textures[textureNum].getY(), textures[textureNum].getWIDTH(), textures[textureNum].getHEIGHT());
        }
        
        handleKeyboard();
    }

    public static void alloc(){
        textureNames[1] = "resume";
        textureNames[2] = "exit";

        textures[1] = new MenuTextureDimDynamic(resumeWidth, resumeHeight, resumeY, resumeX, textureNames[1]);
        textures[2] = new MenuTextureDimDynamic(exitWidth, exitHeight, exitY, exitX, textureNames[2]);

        pauseBGTexture = new Texture("menu/menu_pauseBG.png");
    }

    static void handleKeyboard(){
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

            currGame.batch.draw(textures[currDynamicTexture].getActive(), textures[currDynamicTexture].getX(), textures[currDynamicTexture].getY(), textures[currDynamicTexture].getWIDTH(), textures[currDynamicTexture].getHEIGHT());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            chosenTexture(currDynamicTexture);
        }
    }

    public static void chosenTexture (int textureNum){
        if(textureNum == 1) { //if resume is clicked
            PlayScreen.isGamePaused = false;
        } else if(textureNum == 2) { //if exit is clicked
                dispose();
                PlayScreen.goToMainMenu = true;
        }
    }

    public static void dispose()
    {
        //currPlayScreen.dispose();
        for (int textureNum = 1; textureNum <= NUM_OF_DYNAMIC_TEXTURES; textureNum++){
            textures[textureNum].dispose();
        }
        pauseBGTexture.dispose();
        //currPlayScreen.goToMainMenu();
    }

    //mouse functionality
    /*    public static void checkBoundsAndDraw (int textureNum,Khartoosha game, Vector2 initCamPos)
    {
        //game.batch.draw(background,vec.x - (450F  / Khartoosha.PPM / 2), vec.y - (370F / Khartoosha.PPM / 2),450F / Khartoosha.PPM,370F / Khartoosha.PPM);
        //game.batch.draw(textures[1].getActive(),resumeX, resumeY,resumeWidth,resumeHeight);
        //PauseMenu.displayPauseMenu(game,camera);
        System.out.println(GdxInputGetX_PPM() + "\t" + textures[textureNum].getX());
        if(GdxInputGetX_PPM() < textures[textureNum].getX() + resumeWidth  && GdxInputGetX_PPM() > textures[textureNum].getX()
                && Khartoosha.Gheight / Khartoosha.PPM - GdxInputGetY_PPM() < textures[textureNum].getY() + textures[textureNum].getHEIGHT()
                && Khartoosha.Gheight / Khartoosha.PPM - GdxInputGetY_PPM() > textures[textureNum].getY()
        )
        {
            currDynamicTexture = 0; // if mouse is active disable handle keyboard

            game.batch.draw(textures[textureNum].getActive(), textures[textureNum].getX(), textures[textureNum].getY(), textures[textureNum].getWIDTH(), textures[textureNum].getHEIGHT());
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                //chosenTexture(charNum);
            }
        } else {
            game.batch.draw(textures[textureNum].getInActive(), textures[textureNum].getX(), textures[textureNum].getY(), textures[textureNum].getWIDTH(), textures[textureNum].getHEIGHT());
        }
    }*/
    /* static float GdxInputGetX_PPM()
    {
        return (Gdx.input.getX() / Khartoosha.PPM);
    }
    static float GdxInputGetY_PPM()
    {
        return (Gdx.input.getY() / Khartoosha.PPM);
    }*/
}
