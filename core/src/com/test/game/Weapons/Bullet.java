package com.test.game.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;

public class Bullet extends Sprite
{

    BodyDef bulletBodyDef = new BodyDef();
    Body bulletPhysicsBody;
    World box2dWorld;
    Vector2 initialPosition;
    Texture bulletTexture = new Texture("vfx/weapons/pistol/bullet.png");
    public boolean remove = false;
    protected Vector2 velocity;
    public Vector2 force;

    // for collision detection
    public boolean isContacted = false;

    public Bullet(World world, Vector2 position, Vector2 velocity, Vector2 force)
    {
        this.velocity = velocity;
        this.box2dWorld = world;
        this.initialPosition = position;
        this.force = force;
        defineBulletPhysics();
        setTexture(bulletTexture);
        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 544, 122); //define region of certain texture in png
        setRegion(textureRegion);
        setBounds(0, 0, 120 / Khartoosha.PPM, 30 / Khartoosha.PPM); //set size rendered texture

    }

    // Shotgun constructor, only difference is that there is no bullet texture


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
    public void update(float delta)
    {

        addedDistanceX += velocity.x;
        addedDistanceY += velocity.y;

        setPosition(initialPosition.x + addedDistanceX, initialPosition.y + addedDistanceY); //update position of texture

        // attach physics body for collision
        bulletPhysicsBody.setTransform(initialPosition.x + addedDistanceX, initialPosition.y + addedDistanceY, 0);


        //remove bullet on contact
        if (isContacted || isOutOfMap())
        {
            remove();
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


    public void remove()
    {
        if (!bulletPhysicsBody.getFixtureList().isEmpty())
            bulletPhysicsBody.destroyFixture(bulletPhysicsBody.getFixtureList().first());
        remove = true;
        isContacted = false;
        bulletTexture.dispose();
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
