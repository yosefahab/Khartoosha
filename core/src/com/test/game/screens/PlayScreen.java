package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;
import com.test.game.sprites.PowerUps.PowerUp;
import com.test.game.sprites.PowerUps.SpeedBoost;

import java.util.Random;


public class PlayScreen implements Screen
{
    // Reference to our game, used to set screens
    private Khartoosha game;

    private TextureAtlas atlas;
    private Character character;

    public float delta = Gdx.graphics.getDeltaTime();

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    // Box2d variables
    private World box2dWorld;
    private Box2DDebugRenderer box2dDebugRenderer;

    // Basic Screen Variables
    private OrthographicCamera gameCam;
    private Viewport viewport;

    //Powerups array that contains 1 of each type
    private PowerUp[] PUPs = new PowerUp[PowerUp.MAXPUPS];

    private void initMap()
    {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/testmap.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Khartoosha.PPM);

        box2dWorld = new World(new Vector2(0, -10), true);
        box2dDebugRenderer = new Box2DDebugRenderer();
        box2dDebugRenderer.setDrawBodies(true); //hides physics body

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Khartoosha.PPM, (rect.getY() + rect.getHeight() / 2) / Khartoosha.PPM);

            body = box2dWorld.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Khartoosha.PPM, rect.getHeight() / 2 / Khartoosha.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }


    }


    public PlayScreen(Khartoosha game, int charNum, int mapNum)
    {
        atlas = new TextureAtlas("Characters.pack");

        // Reference to our game, used to set screens
        this.game = game;

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(Khartoosha.Gwidth / Khartoosha.PPM, Khartoosha.Gheight / Khartoosha.PPM, gameCam);

        // Create our Box2D world, setting no gravity in X, -1 gravity in Y, and allow bodies to sleep
        box2dWorld = new World(new Vector2(0, Khartoosha.GRAVITY), true);

        // Allows for debug lines of our box2d world.
        box2dDebugRenderer = new Box2DDebugRenderer();

        // Initialize map
        initMap();
        gameCam.position.set(viewport.getWorldWidth() / 2 / Khartoosha.PPM, viewport.getWorldHeight() / 2 / Khartoosha.PPM, 0);


        character = new Character(box2dWorld, this,  charNum);

        // Power Ups
        PUPs[0] = new SpeedBoost(box2dWorld);
        PUPs[1] = new SpeedBoost(box2dWorld);

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

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            character.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            character.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            character.moveRight();
        }

        //Debug
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            System.out.println("PUP Location: "+PUPs[0].pupBody.getPosition().x + "  " + PUPs[0].pupBody.getPosition().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            System.out.println("Character Location: "+character.physicsBody.getPosition().x + "  " + character.physicsBody.getPosition().y);
        }
    }

    public void update()
    {
        // Update physics world
        box2dWorld.step(1/60F, 6, 2);
        handleInput();
        character.update(delta);
        // Update camera position wrt character position
        gameCam.update();
        gameCam.position.x = character.physicsBody.getPosition().x;
        gameCam.position.y = character.physicsBody.getPosition().y;

        //Power Ups
        //TODO: comment handlePups to disable pups functionality
        //handllePups();
        // Render map
        mapRenderer.setView(gameCam);
    }



    @Override
    public void dispose()
    {
        map.dispose();
        character.dispose();
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.render();

        box2dDebugRenderer.render(box2dWorld, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        character.draw(game.batch);

        //TODO: uncomment when textures are ready
        /*for (PowerUp pup:PUPs)
        {
            if (pup.getSpawnState())
                pup.draw(game.batch);
        }*/

        game.batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
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
    public TextureAtlas getAtlas(){return atlas;}
}
