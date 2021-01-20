package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

import java.util.Vector;


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

    //The hearts stuff
    private Vector<Sprite> lives1 =new Vector<Sprite>(5);
    private Vector<Sprite> lives2 =new Vector<Sprite>(5);
    private Sprite s =new Sprite();
    private Texture tex = new Texture("her.png") ;

    // current character stuff
    private Texture char1 = new Texture("char1.png") ;
    private Texture char2 = new Texture("char2.png") ;
    private Texture char3 = new Texture("char3.png") ;
    private Sprite num1 =new Sprite();
    private Sprite num2 =new Sprite();
    private Sprite num3 =new Sprite();
    private Sprite player[]=new Sprite[3];

    //current weapon stuff
    private Texture gun1 = new Texture("pistol.png") ;
    private Texture gun2 = new Texture("mg.png") ;
    private Texture gun3 = new Texture("sniper.png") ;
    private Sprite gun_num1 =new Sprite();
    private Sprite gun_num2 =new Sprite();
    private Sprite gun_num3 =new Sprite();
    private Sprite arr[]=new Sprite[3];
    private Sprite ar[]=new Sprite[3];

    //Ammo stuff
    private Texture ammo1 = new Texture ("bulletCount.png");
    private Sprite amo = new Sprite ();
    private Vector <Sprite> Ammo1  = new Vector <Sprite>(30);


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

        s.setRegion(tex);
        s.setSize(25/Khartoosha.PPM ,25/Khartoosha.PPM);
        for (int i=0 ; i<5 ; i++) {
            lives1.add(s);
            lives2.add(s);

        }
        num1.setRegion(char1);
        num2.setRegion(char2);
        num3.setRegion(char3);
        num1.setSize(75f/Khartoosha.PPM,75f/Khartoosha.PPM);
        num2.setSize(75f/Khartoosha.PPM,75f/Khartoosha.PPM);
        num3.setSize(75f/Khartoosha.PPM,75f/Khartoosha.PPM);
        player[0]=num1;
        player[1]=num2;
        player[2]=num3;

        gun_num1.setRegion(gun1);
        gun_num2.setRegion(gun2);
        gun_num3.setRegion(gun3);
        gun_num1.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);
        gun_num2.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);
        gun_num3.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);
        arr[0]=gun_num1;
        arr[1]=gun_num2;
        arr[2]=gun_num3;
        ar[0]=gun_num1;
        ar[1]=gun_num2;
        ar[2]=gun_num3;

        amo.setRegion(ammo1);
        for (int i=0 ; i<30 ; i++)
        {
            Ammo1.add(amo);
        }

    }

    // 1 Player constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  char1Num,true, false);

    }

    // 2 Players constructor
    public PlayScreen(Khartoosha game, int mapNum, int char1Num, int char2Num)
    {
        this(game, mapNum);
        character1 = new Character(box2dWorld, this,  char1Num,true, false);
        character2 = new Character(box2dWorld, this,  char2Num,false, true);

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
        if (character1.getBodyPosition().y<-7.92){
            lives1.remove(lives1.size()-1);
        }
        if (character2.getBodyPosition().y<-7.92){
            lives2.remove(lives2.size()-1);
        }

        switch (character1.currentWeapon.type)
        {
            case 0:

                amo.setSize(20/Khartoosha.PPM,20/Khartoosha.PPM);
                Ammo1.setSize(7);
               //System.out.println("hamada");
                break;

                case 1:
                    amo.setSize(10/Khartoosha.PPM,10/Khartoosha.PPM);
                    Ammo1.setSize(30);
                    break;
            case 2:
                amo.flip(false,true);
                amo.setSize(25/Khartoosha.PPM,50/Khartoosha.PPM);
                Ammo1.setSize(5);
               // System.out.println(Ammo1.capacity());
                break;

        }
        //if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT))
        //{
         //Ammo1.remove(Ammo1.size()-1);

        //}

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

            float l=0;
            for (int i=0 ; i<lives1.size() ; i++) {
                l+=.3;
                lives1.elementAt(i).setPosition(camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2)+l,camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-6.0f);
                lives1.elementAt(i).draw(Khartoosha.batch);
             }

            l=0;
            for (int i=0 ; i<lives2.size() ; i++) {
                l+=.3;
                lives2.elementAt(i).setPosition(-0.2f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2)-l,camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.8f);
                lives2.elementAt(i).draw(Khartoosha.batch);

            }

            player[character1.current_char-1].setPosition(.1f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-6.76f);
            player[character1.current_char-1].draw(Khartoosha.batch);
            player[character2.current_char-1].setPosition(-.7f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.04f);
            player[character2.current_char-1].draw(Khartoosha.batch);

            ar[character1.currentWeapon.type].setPosition(.3f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-.6f);
            ar[character1.currentWeapon.type].draw(Khartoosha.batch);
            arr[character2.currentWeapon.type].setPosition(-0.7f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+6.2f);
            arr[character2.currentWeapon.type].draw(Khartoosha.batch);

            for(int i=0; i<Ammo1.size(); i++){
            Ammo1.elementAt(i).setPosition(.3f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-.6f);
            Ammo1.elementAt(i).draw(Khartoosha.batch);
            }
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