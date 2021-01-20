package com.test.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.soundsManager;
import com.test.game.sprites.Camera;
//TODO: menu: add sound clicks when moving in menu options and clicking
public class PauseMenu{
    private static float pauseBGWidth;
    private static float pauseBGHeight;
    private static float pauseBGX;
    private static float pauseBGY;
    private static Texture pauseBGTexture;

    private static float resumeWidth;
    private static float resumeHeight;
    private static float resumeX;
    private static float resumeY;

    private static float settingsWidth;
    private static float settingsHeight;
    private static float settingsX;
    private static float settingsY;

    private static float exitWidth;
    private static float exitHeight;
    private static float exitX;
    private static float exitY;

    private static float musicWidth;
    private static float musicHeight;
    private static float musicX;
    private static float musicY;

    private static float soundfxWidth;
    private static float soundfxHeight;
    private static float soundfxX;
    private static float soundfxY;

    private static float backWidth;
    private static float backHeight;
    private static float backX;
    private static float backY;

    public static int pauseMenuPageNum = 1;

    private static final int NUM_OF_DYNAMIC_TEXTURES_PAGE1 = 3;
    private static final int NUM_OF_DYNAMIC_TEXTURES_PAGE2 = 3;
    private static final int NUM_OF_DYNAMIC_TEXTURES = NUM_OF_DYNAMIC_TEXTURES_PAGE1 + NUM_OF_DYNAMIC_TEXTURES_PAGE2;
    public static String[] textureNames = new String[NUM_OF_DYNAMIC_TEXTURES + 1];
    public static MenuTextureDimDynamic[] textures = new MenuTextureDimDynamic[NUM_OF_DYNAMIC_TEXTURES + 1];

    private static final int NUM_OF_ON_OFF_BUTTONS = 2;
   // private final String[] onOffButtonTextureNames = new String[NUM_OF_ON_OFF_BUTTONS + 1];
    private static final MenuTextureDim[] onOffButtonTextures = new MenuTextureDim[NUM_OF_ON_OFF_BUTTONS + 1];
    private static boolean[] isOn = new boolean[NUM_OF_ON_OFF_BUTTONS + 1];
    private static Texture on, off;

    private static int currDynamicTexture = 1;

    public static void alloc(){
        textureNames[1] = "resume";
        textureNames[2] = "settings";
        textureNames[3] = "exit";
        textureNames[4] = "music";
        textureNames[5] = "soundfx";
        textureNames[6] = "back";

        textures[1] = new MenuTextureDimDynamic(resumeWidth, resumeHeight, resumeY, resumeX, textureNames[1]);
        textures[2] = new MenuTextureDimDynamic(settingsWidth,settingsHeight,settingsY,settingsX,textureNames[2]);
        textures[3] = new MenuTextureDimDynamic(exitWidth, exitHeight, exitY, exitX, textureNames[3]);
        textures[4] = new MenuTextureDimDynamic(resumeWidth, resumeHeight, resumeY, resumeX, textureNames[4]);
        textures[5] = new MenuTextureDimDynamic(settingsWidth,settingsHeight,settingsY,settingsX,textureNames[5]);
        textures[6] = new MenuTextureDimDynamic(exitWidth, exitHeight, exitY, exitX, textureNames[6]);

        onOffButtonTextures[1] = new MenuTextureDim(0,0,0,0);
        onOffButtonTextures[2] = new MenuTextureDim(0,0,0,0);

        pauseBGTexture = new Texture("menu/menu_pauseBG-WIDE2.png");

        on = new Texture("menu/menu_on.png");
        off = new Texture("menu/menu_off.png");
    }
    
    public static void displayPauseScreen(Camera camera)
    {
        Vector2 vec;
        vec = camera.getMid();

        pauseBGWidth = 742F / Khartoosha.PPM;
        pauseBGHeight =  550F / Khartoosha.PPM;
        pauseBGX = vec.x - (pauseBGWidth / 2);
        pauseBGY = vec.y - (pauseBGHeight / 2);

        resumeWidth = 400F / Khartoosha.PPM;
        resumeHeight =  100F / Khartoosha.PPM;
        resumeX = vec.x - (resumeWidth / 2);
        resumeY = vec.y + (70F / Khartoosha.PPM);

        settingsWidth = 375F / Khartoosha.PPM;
        settingsHeight =  100F / Khartoosha.PPM;
        settingsX = vec.x - (settingsWidth / 2);
        settingsY = vec.y - (90F / Khartoosha.PPM);

        exitWidth = 300F / Khartoosha.PPM;
        exitHeight =  100F / Khartoosha.PPM;
        exitX = vec.x - (exitWidth / 2);
        exitY = settingsY - (160F / Khartoosha.PPM);

        musicWidth = 334F / Khartoosha.PPM;
        musicHeight =  70F / Khartoosha.PPM;
        musicX = (float) (vec.x - (musicWidth / 2.2) - pauseBGX);
        musicY = resumeY;
        
        soundfxWidth = 334F / Khartoosha.PPM;
        soundfxHeight =  70F / Khartoosha.PPM;
        soundfxX = musicX;
        soundfxY = musicY - (160F / Khartoosha.PPM);
        
        backWidth = 209F / Khartoosha.PPM;
        backHeight =  70F / Khartoosha.PPM;
        backX = vec.x - (backWidth / 2);
        backY = settingsY - (160F / Khartoosha.PPM);

        onOffButtonTextures[1].WIDTH = 141F / Khartoosha.PPM;
        onOffButtonTextures[1].HEIGHT =  78F / Khartoosha.PPM;
        onOffButtonTextures[1].X = (float) (vec.x + (onOffButtonTextures[1].WIDTH / 2.2) + pauseBGX);
        onOffButtonTextures[1].Y = musicY;

        onOffButtonTextures[2].WIDTH = 141F / Khartoosha.PPM;
        onOffButtonTextures[2].HEIGHT =  78F / Khartoosha.PPM;
        onOffButtonTextures[2].X = (float) (vec.x + (onOffButtonTextures[2].WIDTH / 2.2) + pauseBGX);
        onOffButtonTextures[2].Y = soundfxY;
        
        textures[1].setPos(resumeY,resumeX);
        textures[1].setDim(resumeWidth,resumeHeight);

        textures[2].setPos(settingsY,settingsX);
        textures[2].setDim(settingsWidth,settingsHeight);
        
        textures[3].setPos(exitY,exitX);
        textures[3].setDim(exitWidth,exitHeight);

        textures[4].setPos(musicY,musicX);
        textures[4].setDim(musicWidth,musicHeight);

        textures[5].setPos(soundfxY,soundfxX);
        textures[5].setDim(soundfxWidth,soundfxHeight);

        textures[6].setPos(backY,backX);
        textures[6].setDim(backWidth,backHeight);

        Khartoosha.batch.draw(pauseBGTexture, pauseBGX, pauseBGY, pauseBGWidth, pauseBGHeight);

        if(pauseMenuPageNum == 1) //display main pause menu (page 1)
        {
            for (int textureNum = 1; textureNum <= NUM_OF_DYNAMIC_TEXTURES_PAGE1; textureNum++)
            {
                Khartoosha.batch.draw(textures[textureNum].getInActive(), textures[textureNum].getX(), textures[textureNum].getY(), textures[textureNum].getWIDTH(), textures[textureNum].getHEIGHT());
            }
        }
        else if(pauseMenuPageNum == 2) //display settings pause menu
        {
            for (int textureNum = NUM_OF_DYNAMIC_TEXTURES_PAGE1 + 1; textureNum <= NUM_OF_DYNAMIC_TEXTURES; textureNum++)
            {
                Khartoosha.batch.draw(textures[textureNum].getInActive(), textures[textureNum].getX(), textures[textureNum].getY(), textures[textureNum].getWIDTH(), textures[textureNum].getHEIGHT());
            }
            for (int textureNum = 1; textureNum <= NUM_OF_ON_OFF_BUTTONS; textureNum++)
            {
                if (isOn[textureNum])
                {
                    Khartoosha.batch.draw(on, onOffButtonTextures[textureNum].getX(), onOffButtonTextures[textureNum].getY(), onOffButtonTextures[textureNum].getWIDTH(), onOffButtonTextures[textureNum].getHEIGHT());

                }
                else
                {
                    Khartoosha.batch.draw(off, onOffButtonTextures[textureNum].getX(), onOffButtonTextures[textureNum].getY(), onOffButtonTextures[textureNum].getWIDTH(), onOffButtonTextures[textureNum].getHEIGHT());
                }
            }
        }

        handleKeyboard();
    }

    static void handleKeyboard(){
        int toBeComparedToTextureNum = 0; //IMPORTANT: this variable changes the values of each if condition according to the page number
        if(pauseMenuPageNum == 1){    // in order not to display textures from the first page into the second and vice versa
            toBeComparedToTextureNum = 0;
        } else if(pauseMenuPageNum == 2){
            toBeComparedToTextureNum = NUM_OF_DYNAMIC_TEXTURES_PAGE1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(currDynamicTexture <= 1 + toBeComparedToTextureNum){
                currDynamicTexture = NUM_OF_DYNAMIC_TEXTURES_PAGE1 + toBeComparedToTextureNum;
            } else {
                currDynamicTexture--;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(currDynamicTexture >= NUM_OF_DYNAMIC_TEXTURES_PAGE1 + toBeComparedToTextureNum){
                currDynamicTexture = 1 + toBeComparedToTextureNum;
            } else {
                currDynamicTexture++;
            }
        }
        if(currDynamicTexture >= 1 + toBeComparedToTextureNum && currDynamicTexture <= NUM_OF_DYNAMIC_TEXTURES_PAGE1 + toBeComparedToTextureNum) {

            Khartoosha.batch.draw(textures[currDynamicTexture].getActive(), textures[currDynamicTexture].getX(), textures[currDynamicTexture].getY(), textures[currDynamicTexture].getWIDTH(), textures[currDynamicTexture].getHEIGHT());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            chosenTexture(currDynamicTexture);
        }
    }

    public static void chosenTexture (int textureNum){
        if(textureNum == 1) { //if resume is clicked
            PlayScreen.isGamePaused = false;
        } else if(textureNum == 2){ //if settings is clicked
            initializeSettings();
            pauseMenuPageNum = 2;
            currDynamicTexture = NUM_OF_DYNAMIC_TEXTURES_PAGE1 + 1;
        } else if(textureNum == 3) { //if exit is clicked
            dispose();
            PlayScreen.goToMainMenu = true;
        } else if(textureNum == 4 || textureNum == 5) {
            chosenOnOff(textureNum - NUM_OF_DYNAMIC_TEXTURES_PAGE1);
        } else if(textureNum == 6){ //if back is clicked
            pauseMenuPageNum = 1;
            currDynamicTexture = 1;
        }

    }
    public static void chosenOnOff(int onOffTextureNum) {
        isOn[onOffTextureNum] = !isOn[onOffTextureNum];
        if (onOffTextureNum == 1) { //if its music onOff button
            if (isOn[onOffTextureNum]){
                soundsManager.playGameMusic();
                soundsManager.musicVolume = soundsManager.DEFAULT_MUSIC_VOL;
            } else{
                soundsManager.stopGameMusic();
            }
        }
        if (onOffTextureNum == 2) { //if its soundfx onOff button
            if (isOn[onOffTextureNum]){
                soundsManager.soundVolume = soundsManager.DEFAULT_SOUND_VOL;
            } else{
                soundsManager.soundVolume = 0f;
            }
        }
    }
    public static void initializeSettings(){
        //setting the on off buttons according to the set values in soundsManager(music) and soundsManager(sfx vol)
        if(soundsManager.gameMusic.isPlaying()){
            isOn[1] = true;
        } else{
            isOn[1] = false;
        }
        if (soundsManager.soundVolume > 0f){
            isOn[2] = true;
        } else{
            isOn[2] = false;
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
