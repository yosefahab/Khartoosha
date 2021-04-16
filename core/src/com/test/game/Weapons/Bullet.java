package com.test.game.Weapons;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.Map;

public class Bullet extends Sprite
{

    private BodyDef bulletBodyDef = new BodyDef();
    private Body bulletPhysicsBody;
    private World box2dWorld;
    private Vector2 initialPosition;
    public boolean removeFromArray = false;
    private Vector2 velocity;
    public Vector2 force;
    public boolean isContacted = false;
    private int weaponType;

    protected PointLight pointLight;
    protected static Color pointLight_color;
    protected static int pointLight_radius = 5;
    public static boolean isLight;
    protected boolean isBomb;

    public Bullet(World world, Vector2 position, Vector2 velocity, Vector2 force, Texture bulletTexture, int weaponType)
    {
        this.weaponType = weaponType;
        this.velocity = velocity;
        this.box2dWorld = world;
        this.initialPosition = position;
        this.force = force;
        defineBulletPhysics();
        setTexture(bulletTexture);
        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 544, 122); //define region of certain texture in png
        setRegion(textureRegion);
        setBounds(position.x, position.y, 120 / Khartoosha.PPM, 30 / Khartoosha.PPM); //set size rendered texture

        if (isLight)
        {
            pointLight = new PointLight(Map.rayHandler, pointLight_radius);
            pointLight.setColor(pointLight_color);
            pointLight.setDistance(2);
        }
    }



    public void defineBulletPhysics()
    {

        bulletBodyDef.position.set(initialPosition);
        bulletBodyDef.type = BodyDef.BodyType.KinematicBody;
        bulletPhysicsBody = box2dWorld.createBody(bulletBodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        // user data for collision detection
        bulletPhysicsBody.createFixture(fdef).setUserData(this);

    }

    private float addedDistanceX = 0;
    private float addedDistanceY = 0;

    public void update()
    {

        addedDistanceX += velocity.x;
        addedDistanceY += velocity.y;

        setPosition(initialPosition.x + addedDistanceX, initialPosition.y + addedDistanceY - 0.3f); //update position of texture

        // attach physics body for collision
        bulletPhysicsBody.setTransform(initialPosition.x + addedDistanceX, initialPosition.y + addedDistanceY - 0.2f, 0);
        //remove bullet on contact
        if
        (
                isContacted ||
                        isOutOfMap() ||
                        (isOutOfRange(WeaponManager.SHOTGUN_RANGE, false) && weaponType == 3) ||
                        (isOutOfRange(WeaponManager.BOMB_RANGE, true) && weaponType == 4)
        )
        {
            dispose();
        }
        if (isLight)
        {
            float x = bulletPhysicsBody.getPosition().x + 0.6f;
            float y = bulletPhysicsBody.getPosition().y;
            pointLight.setPosition(x, y);
        }

    }

    public Vector2 getInitialPosition()
    {
        return initialPosition;
    }

    public Vector2 getCurrentPosition()
    {
        return bulletPhysicsBody.getPosition();
    }


    // calling this WILL NOT remove the Bullet instance from memory,
    // it only disposes of disposable attributes
    public void dispose()
    {

        if (!bulletPhysicsBody.getFixtureList().isEmpty())
        {
            bulletPhysicsBody.destroyFixture(bulletPhysicsBody.getFixtureList().first());
        }
        if (pointLight != null && isBomb)
        {
            pointLight.remove();
            pointLight = null;
        }
        removeFromArray = true;
        isContacted = false;
    }

    private boolean isOutOfMap()
    {
        return getCurrentPosition().x > (Gdx.graphics.getWidth() + 300) / Khartoosha.PPM
                ||
                getCurrentPosition().x < -300 / Khartoosha.PPM;
    }

    protected boolean isOutOfRange(float range, boolean includeVerticalRange)
    {
        Vector2 currentPosition = getCurrentPosition();
        Vector2 initialPosition = getInitialPosition();

        float distanceX = Math.abs(currentPosition.x - initialPosition.x);
        float distanceY = Math.abs(currentPosition.y - initialPosition.y);

        if (!includeVerticalRange)
            return distanceX >= range;

        return distanceX >= range || distanceY >= range;
    }

}
