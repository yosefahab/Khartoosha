package com.test.game.screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.*;
import com.test.game.menu.PauseMenu;
import com.test.game.screens.menu_screens.old.OldMainMenuScreen;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;
import com.test.game.sprites.Map;
import com.test.game.sprites.PowerUps.*;

import java.util.Random;


public class PlayScreen implements Screen
{


    public static int winner;
    public static boolean gameOver=false;

    private final TextureAtlas atlas, powerupAtlas;
    public static Character character1,character2;
    public float delta = Gdx.graphics.getDeltaTime();

    // Tiled map variables
    private final Map map;

    // Box2d variables
    private final World box2dWorld;
    private final Box2DDebugRenderer box2dDebugRenderer;

    public static Camera camera;
    private final Khartoosha game;

    PowerUpsHandler powerUpsHandler;

    //Pause menu
    public static boolean isGamePaused;
    public static boolean goToMainMenu;

    public Hud H;

    public static RayHandler rayHandler;

    // General constructor
    public PlayScreen(Khartoosha game, int mapID)
    {
        this.game = game;
        mapID--; // because mapID is zero based
        SoundsManager.stopMenuMusic();
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
        map.loadMap(mapID);

        // Contact listener
        WorldContactListener collisionHandler = new WorldContactListener();
        box2dWorld.setContactListener(collisionHandler);

        // Camera
        camera = new Camera();
        camera.init();

        // Powerups
        //Powerups array that contains 1 of each type
        PowerUp[] PUPs = new PowerUp[PowerUp.MAXPUPS];
        powerUpsHandler = new PowerUpsHandler(PUPs, box2dWorld, this);
        powerUpsHandler.init();

        //Pause Menu
        PauseMenu.alloc();
        isGamePaused = false;
        goToMainMenu = false;

        H = new Hud();
    }

    // 1 Player constructor
    public PlayScreen(Khartoosha game, int mapNum, int textureNumber)
    {
        this(game, mapNum);

        character1 = new Character(box2dWorld, this,  textureNumber,true, false, map.getSpawnPoints().get(0));
        Random rand = new Random();
        character2 = new Character(box2dWorld, this,  rand.nextInt(3)+1,false, true, map.getSpawnPoints().get(1));
        character1.setEnemy(character2);
        character2.setEnemy(character1);
        character2.getAi().setJumpPoints(map.getJumpPoints());
        character2.getAi().setJumpDirections(map.getJumpDirections());
        character2.getAi().PUPs = powerUpsHandler.getPUPs();
    }

    // 2 Players constructor
    public PlayScreen(Khartoosha game, int mapNum, int player1Texture, int player2Texture)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  player1Texture,true, false, map.getSpawnPoints().get(0));
        character2 = new Character(box2dWorld, this,  player2Texture,false, false, map.getSpawnPoints().get(1));

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
        //TODO: remove redundant null check?
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
            System.out.println("Ammo: \t"+ character1.weapon.getAmmo()+"      \t" + character2.weapon.getAmmo());
        }

        powerUpsHandler.update();
        H.Lose_life(character1, character2);

    }



    @Override
    public void dispose()
    {
        //TODO: call all classes' dispose methods from here
        map.dispose();
        character1.dispose();
        character2.dispose();
        SoundsManager.dispose();
        Hud.dispose();
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
            H.heads_pos(camera ,character1 , character2);
            H.Gun_pos(camera , character1,character2);
            H.bullet_pos(camera,character1,character2);
        }
        if (gameOver){
            isGamePaused = true;
            Hud.endGame(winner);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
            {
                //this.dispose();
                //game.setScreen((new OldMainMenuScreen(game)));
            }
        }
        if(isGamePaused && !gameOver)
        {
            if(goToMainMenu)
            {
                //this.dispose();
                game.setScreen(new OldMainMenuScreen(game));
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