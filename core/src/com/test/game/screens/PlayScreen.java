package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.WorldContactListener;
import com.test.game.Khartoosha;
import com.test.game.menu.PauseMenu;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;
import com.test.game.sprites.Map;
import com.test.game.sprites.PowerUps.*;


public class PlayScreen implements Screen
{

    private TextureAtlas atlas,powerupAtlas;
    private Character character1,character2;
    public float delta = Gdx.graphics.getDeltaTime();

    // Tiled map variables
    private Map map;

    // Box2d variables
    private World box2dWorld;
    private Box2DDebugRenderer box2dDebugRenderer;

    private Camera camera;
    private Khartoosha game;

    //Powerups array that contains 1 of each type
    private PowerUp[] PUPs = new PowerUp[PowerUp.MAXPUPS];
    PowerUpsHandler powerUpsHandler;

    //Pause menu
    public static boolean isGamePaused;
    public static boolean goToMainMenu;


    // General constructor
    public PlayScreen(Khartoosha game, int mapNum)
    {
        this.game = game;
        atlas = new TextureAtlas("Characters.pack");
        powerupAtlas = new TextureAtlas("powerups.pack");

        // Create our Box2D world, setting no gravity in X, -1 gravity in Y, and allow bodies to sleep
        box2dWorld = new World(new Vector2(0, -10), true);

        // Allows for debug lines of our box2d world.
        box2dDebugRenderer = new Box2DDebugRenderer();


        // Initialize map
        map = new Map(box2dWorld);
        map.loadMap(1);

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
    }

    // 1 Player constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  char1Num,true);

    }

    // 2 Players constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num, int char2Num)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  char1Num,true);
        character2 = new Character(box2dWorld, this,  char2Num,false);

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

        character1.update(delta);
        if (character2 != null)
        {
            character2.update(delta);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            isGamePaused = !isGamePaused;
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
        }
        if(isGamePaused)
        {
            if(goToMainMenu)
            {
                //this.dispose();

                game.setScreen(new MainMenuScreen(game));
            }
            PauseMenu.displayPauseScreen(game,camera,this);
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