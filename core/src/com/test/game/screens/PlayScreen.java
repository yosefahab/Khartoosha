package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.*;
import com.test.game.menu.PauseMenu;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;
import com.test.game.sprites.Map;
import com.test.game.sprites.PowerUps.*;

import java.util.Random;


public class PlayScreen implements Screen
{

    private TextureAtlas atlas,powerupAtlas;
    public static Character character1,character2;
    public float delta = Gdx.graphics.getDeltaTime();

    // Tiled map variables
    private Map map;

    // Box2d variables
    private World box2dWorld;
    private Box2DDebugRenderer box2dDebugRenderer;

    public static Camera camera;
    private Khartoosha game;

    //Powerups array that contains 1 of each type
    private PowerUp[] PUPs = new PowerUp[PowerUp.MAXPUPS];
    PowerUpsHandler powerUpsHandler;

    //Pause menu
    public static boolean isGamePaused;
    public static boolean goToMainMenu;

     public HUD H;
    // General constructor
    public PlayScreen(Khartoosha game, int mapNum)
    {
        this.game = game;

        soundsManager.stopMenuMusic();
        //TODO: uncomment if you want game music to start by default
        //soundsManager.playGameMusic();

        atlas = new TextureAtlas("Characters.pack");
        powerupAtlas = new TextureAtlas("powerups.pack");

        // Create our Box2D world, setting no gravity in X, -1 gravity in Y, and allow bodies to sleep
        box2dWorld = new World(new Vector2(0, -10), true);

        // Allows for debug lines of our box2d world.
        box2dDebugRenderer = new Box2DDebugRenderer();


        // Initialize map
        map = new Map(box2dWorld);
        map.loadMap(3);

        // Contact listener
        WorldContactListener collisionHandler = new WorldContactListener();
        box2dWorld.setContactListener(collisionHandler);

        // Camera
        camera = new Camera();
        camera.init();

        // Powerups
        powerUpsHandler = new PowerUpsHandler(PUPs, box2dWorld, this);
        powerUpsHandler.init();

        //Pause Menu
        PauseMenu.alloc();
        isGamePaused = false;
        goToMainMenu = false;

        H=new HUD();
    }

    // 1 Player constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num)
    {
        this(game, mapNum);

        character1 = new Character(box2dWorld, this,  char1Num,true, false, map.getSpawnPoints().get(0));

        Random rand = new Random();
        character2 = new Character(box2dWorld, this,  rand.nextInt(3)+1,false, true, map.getSpawnPoints().get(1));
        character1.setEnemy(character2);
        character2.setEnemy(character1);
        character2.getAi().setJumpPoints(map.getJumpPoints());
        character2.getAi().setJumpDirections(map.getJumpDirections());
        character2.getAi().PUPs = powerUpsHandler.getPUPs();
    }

    // 2 Players constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num, int char2Num)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  char1Num,true, false, map.getSpawnPoints().get(0));
        character2 = new Character(box2dWorld, this,  char2Num,false, false, map.getSpawnPoints().get(1));

        character1.setEnemy(character2);
        character2.setEnemy(character1);

    }
    /**
     * Handles all powerups related operations
     *  Spawns pup if it's available
     *  Deletes pups if were picked up
     */


    public void update()
    {
        // Update physics world
        box2dWorld.step(1/60F, 6, 2);

        character1.update();
        if (character2 != null)
        {
            character2.update();
            camera.update(character1,character2);
        }
        else
        {
            camera.update(character1);
        }

        map.mapRenderer.setView(camera.gameCam);

        //////////// DEBUG /////////////
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT))
        {
            System.out.println("--------- GAME STATS --------- " );
            System.out.println("\t\t-Player 1-    -Player 2- " );
            System.out.println("Lives: \t\t"+ character1.current_lives+"      \t\t" + character2.current_lives);
            System.out.println("Armored: "+ character1.isArmored+"      \t" + character2.isArmored);
            System.out.println("Ammo: \t"+ character1.currentWeapon.getAmmo()+"      \t" + character2.currentWeapon.getAmmo());
        }

        powerUpsHandler.update();
        H.Lose_life(character1, character2);

    }



    @Override
    public void dispose()
    {
        map.dispose();
        character1.dispose();
        if (character2!= null){character2.dispose();}
        box2dDebugRenderer.dispose();
        box2dWorld.dispose();
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)|| Gdx.input.isKeyJustPressed(Input.Keys.P))
        {
            isGamePaused = !isGamePaused;
            PauseMenu.pauseMenuPageNum = 1;
        }
        if(!isGamePaused)
        {
            update();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render();
        box2dDebugRenderer.render(box2dWorld, camera.gameCam.combined);
        Khartoosha.batch.setProjectionMatrix(camera.gameCam.combined);
        Khartoosha.batch.begin();
        powerUpsHandler.render();
        character1.render();
        if (character2 != null)
        {
            character2.render();

            H.Hearts_pos(camera);
            H.Hud_pos(camera);
            H.char_pos(camera ,character1 , character2);
            H.Gun_pos(camera , character1,character2);
        }
        if(isGamePaused)
        {
            if(goToMainMenu)
            {
                //this.dispose();

                game.setScreen(new MainMenuScreen(game));
            }
            PauseMenu.displayPauseScreen(camera);
        }
        Khartoosha.batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        camera.viewport.update(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }
    //TODO: implement functions unfala7i
    public TextureAtlas getAtlas(){return atlas;}
    public TextureAtlas GetAtlas(){return powerupAtlas;}

}