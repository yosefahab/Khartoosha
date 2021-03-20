package com.test.game.screens.play_screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.test.game.Khartoosha;
import com.test.game.Weapons.Bullet;
import com.test.game.menu.MovingBackground;
import com.test.game.screens.play_screen.PlayScreen;

public class Map
{
    // Reference to box2D world
    private final World box2dWorld;

    // The map itself
    private TiledMap map;

    // Array of fixtures, used to identify platforms in Contact Listener
    public Array<Fixture> fixtures;

    private MovingBackground movingBackground;

    public OrthogonalTiledMapRenderer mapRenderer;
    private final Array<Rectangle> jumpPoints = new Array<>();
    private final Array<Integer> jumpDirections = new Array<>();
    private final Array<Vector2> spawnPoints = new Array<>();

    int ID;

    public static final int mapWidth = 1120;
    public static final int mapHeight = 800;

    private float imageLayerFlipTimer = 0;
    public static RayHandler rayHandler;

    public Map(World world)
    {
        this.box2dWorld = world;
    }

    public void loadMap(int mapID)
    {
        this.ID = mapID;
        TmxMapLoader mapLoader;
        String filename = "maps/" + Integer.toString(mapID) + ".tmx";
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(filename);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Khartoosha.PPM);
        fixtures = new Array<Fixture>();

        int platformIndex = 0;
        for (MapObject object : map.getLayers().get(2).getObjects())
        {
            if (object instanceof RectangleMapObject)
            {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                createRectanglePlatform(rect, platformIndex, object);
            }
            platformIndex++;
        }


        for (MapObject object : map.getLayers().get("navigation").getObjects().getByType(RectangleMapObject.class))
        {
            if (object != null)
            {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                jumpPoints.add(rect);
                jumpDirections.add((Integer) object.getProperties().get("jumpDirection"));
            }
        }


        // spawn point are orderd from 0 to numPlayers so the first element in array is the p1 spawn Location
        for (MapObject object : map.getLayers().get("spawnPoints").getObjects().getByType(RectangleMapObject.class))
        {
            if (object != null)
            {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                spawnPoints.add(new Vector2(rect.getX() / Khartoosha.PPM, rect.getY() / Khartoosha.PPM));
            }
        }

        initSpecialBehavior();

    }

    public void render()
    {
        mapRenderer.render();
        UpdateAndrenderSpecialBehavior();
    }


    public void dispose()
    {
        mapRenderer.dispose();
        map.dispose();
    }

    private void createRectanglePlatform(Rectangle rect, int platformIndex, Object object)
    {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / Khartoosha.PPM, (rect.getY() + rect.getHeight() / 2) / Khartoosha.PPM);
        body = box2dWorld.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2 / Khartoosha.PPM, rect.getHeight() / 2 / Khartoosha.PPM);
        fixtureDef.shape = shape;

        // Adding current platform body to the fixture array
        Fixture f = body.createFixture(fixtureDef);
        fixtures.insert(platformIndex, f);
        Object[] userData = new Object[2];
        userData[0] = body;
        userData[1] = object;
        fixtures.get(platformIndex).setUserData(userData);
    }


    public Array<Rectangle> getJumpPoints()
    {
        return jumpPoints;
    }

    public Array<Integer> getJumpDirections()
    {
        return jumpDirections;
    }

    public Array<Vector2> getSpawnPoints()
    {
        return spawnPoints;
    }

    private void initSpecialBehavior()
    {

        if (ID == 1)
        {
            box2dWorld.setGravity(new Vector2(0, -5));
        }

        if (ID == 2)
        {
            movingBackground = new MovingBackground(new Texture("maps/map_resources/map2_moving_background.png"));
        }
        if (ID == 3)
        {
            Bullet.isLight = true;
            rayHandler = new RayHandler(box2dWorld);

            // 0 is totally dark, 1 is totally lit up
            rayHandler.setAmbientLight(0.6f);
        }
    }

    private void UpdateAndrenderSpecialBehavior()
    {
        if (ID == 2)
        {
            Khartoosha.batch.begin();
            movingBackground.displayBGmap();
            Khartoosha.batch.end();
        }

        if (ID == 3)
        {
            imageLayerFlip();
            rayHandler.setCombinedMatrix(PlayScreen.camera.gameCam);
            rayHandler.updateAndRender();
        }
    }

    private void imageLayerFlip()
    {
        imageLayerFlipTimer += Gdx.graphics.getDeltaTime();
        if (imageLayerFlipTimer > 0.05f)
        {
            if (map.getLayers().get("1").isVisible())
                map.getLayers().get("1").setVisible(false);
            else
                map.getLayers().get("1").setVisible(true);

            imageLayerFlipTimer = 0;
        }
    }



}
