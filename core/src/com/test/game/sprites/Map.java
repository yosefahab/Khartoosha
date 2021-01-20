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

public class Map
{
    // Reference to box2D world
    private World box2dWorld;

    // The map itself
    private TiledMap map;

    // Array of fixtures, used to identify platforms in Contact Listener
    public Array<Fixture> fixtures;

    public OrthogonalTiledMapRenderer mapRenderer;

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
                createRectanglePlatform(rect, platformIndex);
            }
            platformIndex++;

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

    private void createRectanglePlatform(Rectangle rect, int platformIndex)
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
        fixtures.get(platformIndex).setUserData(body);
    }

}
