package com.test.game.sprites;

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

import java.util.ArrayList;

public class Map
{
    // Reference to box2D world
    private World box2dWorld;

    // The map itself
    private TiledMap map;

    // Array of fixtures, used to identify platforms in Contact Listener
    public Array<Fixture> fixtures;

    public OrthogonalTiledMapRenderer mapRenderer;
    private Array<Rectangle> jumpPoints = new Array<>();
    private Array<Integer> jumpDirections = new Array<>();
    private Array<Vector2> spawnPoints = new Array<>();


    int ID;

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

        if (mapID == 1)
            box2dWorld.setGravity(new Vector2(0, -5));


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
                //System.out.println(rect.getX()+ "  " + rect.getY());
                jumpPoints.add(rect);
                jumpDirections.add((Integer)object.getProperties().get("jumpDirection"));
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

    }

    public void render()
    {
        mapRenderer.render();
    }

    public void dispose()
    {
        mapRenderer.dispose();
        map.dispose();
        box2dWorld.dispose();
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


    public Array<Rectangle> getJumpPoints() {
        return jumpPoints;
    }

    public Array<Integer> getJumpDirections() {
        return jumpDirections;
    }

    public Array<Vector2> getSpawnPoints() {
        return spawnPoints;
    }

}
