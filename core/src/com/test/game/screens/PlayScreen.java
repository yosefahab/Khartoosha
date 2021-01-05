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
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;
import com.test.game.sprites.Map;
import com.test.game.sprites.PowerUps.Armor;
import com.test.game.sprites.PowerUps.ExtraLife;
import com.test.game.sprites.PowerUps.PowerUp;
import com.test.game.sprites.PowerUps.RefillAmmo;
import com.test.game.sprites.PowerUps.SpeedBoost;
import com.test.game.sprites.Weapon;

import java.util.Random;


public class PlayScreen implements Screen
{
    // Reference to our game, used to set screens
    private Khartoosha game;

    private TextureAtlas atlas,powerupAtlas;
    private Character character,character2;
    private Weapon pistol;
    private Weapon pistol2;

    public float delta = Gdx.graphics.getDeltaTime();

    // Tiled map variables
    private Map map;

    // Box2d variables
    private World box2dWorld;
    private Box2DDebugRenderer box2dDebugRenderer;

    private Camera camera;

    //Powerups array that contains 1 of each type
    private PowerUp[] PUPs = new PowerUp[PowerUp.MAXPUPS];

    // General constructor
    public PlayScreen(Khartoosha game, int mapNum)
    {
        atlas = new TextureAtlas("Characters.pack");
        powerupAtlas = new TextureAtlas("powerups.pack");
        // Reference to our game, used to set screens
        this.game = game;

        // Create our Box2D world, setting no gravity in X, -1 gravity in Y, and allow bodies to sleep
        box2dWorld = new World(new Vector2(0, Khartoosha.GRAVITY), true);
        box2dWorld = new World(new Vector2(0, -10), true);
        box2dDebugRenderer = new Box2DDebugRenderer();
        box2dDebugRenderer.setDrawBodies(false); //hides physics body

        // Allows for debug lines of our box2d world.
        box2dDebugRenderer = new Box2DDebugRenderer();

        // Initialize map
        map = new Map(box2dWorld);
        map.loadMap(1);

        // Power Ups
        PUPs[0] = new SpeedBoost(box2dWorld,this);
        PUPs[1] = new SpeedBoost(box2dWorld,this);
        PUPs[2] = new Armor(box2dWorld,this);
        PUPs[3] = new ExtraLife(box2dWorld,this);
        PUPs[4] = new RefillAmmo(box2dWorld,this);
        PUPs[5] = new RefillAmmo(box2dWorld,this);


        WorldContactListener collisionHandler = new WorldContactListener();
        box2dWorld.setContactListener(collisionHandler);


        // Camera
        camera = new Camera();
        camera.initCam();

    }

    // 1 Player constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num)
    {
        this(game, mapNum);
        character = new Character(box2dWorld, this,  char1Num,true);


        //pistol = new Weapon(box2dWorld, this, character, 0.25f,50, 200,Input.Keys.CONTROL_LEFT);

    }

    // 2 Players constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num, int char2Num)
    {
        this(game, mapNum);
        character = new Character(box2dWorld, this,  char1Num,true);
        character2 = new Character(box2dWorld, this,  char2Num,false);

        /*
        pistol = new Weapon(box2dWorld, this, character, 0.25f,100, 200, Input.Keys.CONTROL_LEFT);
        pistol2= new Weapon(box2dWorld,this,character2,0.25f,100,200, Input.Keys.SPACE);
        */

    }
    /**
     * Handles all powerups related operations
     *  Spawns pup if it's available
     *  Deletes pups if were picked up
     */
    public void handllePups()
    {
        Random generator = new Random();
        // Spawn pups if there's space
        if (PowerUp.currentPups < PowerUp.MAXPUPS)
        {
            // choose random powerup type to spawn
            int indx = generator.nextInt(PowerUp.MAXPUPS) ;

            PUPs[indx].spawn();

        }

        for (PowerUp pup:PUPs)
        {
            if (pup.isSpawned())
                pup.update();

        }

    }


    public void update()
    {
        // Update physics world
        box2dWorld.step(1/60F, 6, 2);
        character.handleInput();
        if (character2 != null)
            character2.handleInput();

        character.update(delta);
        //pistol.update(delta);

        character.currentWeapon.update(delta);

        if (character2!=null){

            character2.currentWeapon.update(delta);
            character2.update(delta);
        }

        // Update Weapon ranks
        if (character2 != null && character.lostLife)
        {
            // Upgrade character 2 on killing character 1
            character2.setWeaponRank(character2.getWeaponRank() + 1);
            character2.isChangeWeapon = true;
            character.lostLife = false;
        }

        if (character2 != null && character2.lostLife)
        {
            // Upgrade character 2 on killing character 1
            character.setWeaponRank(character.getWeaponRank() + 1);
            character.isChangeWeapon = true;
            character2.lostLife = false;
        }


        // Update camera position wrt character position

        if (character2!=null) {
            camera.update(character,character2);
            //camera positions average of 2 players' distances
        }
        else{
            camera.update(character);
        }
        //Power Ups
        //TODO: comment handlePups to disable pups functionality
        handllePups();
        map.mapRenderer.setView(camera.gameCam);

        //////////// DEBUG /////////////
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT))
        {
            System.out.println("--------- GAME STATS --------- " );
            System.out.println("\t\t-Player 1-    -Player 2- " );
            System.out.println("Lives: \t\t"+character.current_lives+"      \t\t" + character2.current_lives);
            System.out.println("Weapon: \t"+character.getWeaponRank() +"      \t\t" + character2.getWeaponRank());
            System.out.println("Armored: "+character.isArmored+"      \t" + character2.isArmored);
            System.out.println("Ammo: \t"+character.currentWeapon.getAmmo()+"      \t" + character2.currentWeapon.getAmmo());
        }
    }



    @Override
    public void dispose()
    {
        map.dispose();
        character.dispose();
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
        update();
        if(character.isFlipX())
        {
            /*pistol.setFlip(true,false);
            pistol.faceRight=false;*/

            character.currentWeapon.setFlip(true,false);
            character.currentWeapon.faceRight=false;
        }
        else if (!character.isFlipX())
        {
//            pistol.setFlip(false,false);
//            pistol.faceRight=true;
            character.currentWeapon.setFlip(false,false);
            character.currentWeapon.faceRight=true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render();

        box2dDebugRenderer.render(box2dWorld, camera.gameCam.combined);

        game.batch.setProjectionMatrix(camera.gameCam.combined);
        game.batch.begin();
        character.draw(game.batch);

        if (character2!= null){

            character2.draw(game.batch);
//            pistol2.draw(game.batch);
//            pistol2.render(game.batch);
            character2.currentWeapon.draw(game.batch);
            character2.currentWeapon.render(game.batch);

            if(character2.isFlipX())
            {
//                pistol2.setFlip(true,false);
//                pistol2.faceRight=false;
                character2.currentWeapon.setFlip(true,false);
                character2.currentWeapon.faceRight=false;
            }
            else if (!character2.isFlipX())
            {
//                pistol2.setFlip(false,false);
//                pistol2.faceRight=true;

                character2.currentWeapon.setFlip(false,false);
                character2.currentWeapon.faceRight=true;
            }


        }

        //TODO: uncomment when textures are ready
        for (PowerUp pup:PUPs)
        {
            if (pup.isSpawned())
                pup.draw(game.batch);
        }

//        pistol.draw(game.batch);
//        pistol.render(game.batch);
        character.currentWeapon.draw(game.batch);
        character.currentWeapon.render(game.batch);
        game.batch.end();
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