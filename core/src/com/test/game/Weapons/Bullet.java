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

    BodyDef bulletBody= new BodyDef();
    Body physicsBodyBullet;
    World world;
    Vector2 Initial_Position;
    Texture Bullet_Texture =new Texture("vfx/weapons/bullet.png");
    public boolean remove=false;
    private float speed;

    // for collision detection
    public boolean isContacted = false;
    public int force;
    public Bullet (World world,Vector2 position,float speed, int force)
    {
        this.speed=speed;
        this.world=world;
        this.Initial_Position =position;
        this.force = force;
        defineBulletPhysics();
        setTexture(Bullet_Texture);
        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 220, 48); //define region of certain texture in png
        setRegion(textureRegion);
        setBounds(0,0,120/Khartoosha.PPM,30/Khartoosha.PPM); //set size rendered texture


    }

    // Shotgun constructor, only difference is that there is no bullet texture




    public void defineBulletPhysics()
    {

        bulletBody.position.set(Initial_Position);
        bulletBody.type = BodyDef.BodyType.KinematicBody;
        physicsBodyBullet= world.createBody(bulletBody);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        // user data for collision detection
        physicsBodyBullet.createFixture(fdef).setUserData(this);



    }
    private float sped=0;
    public void update(float delta)
    {

        sped += this.speed;
        setPosition(Initial_Position.x+sped, Initial_Position.y); //update position of texture

        //attach physics body for collision
        physicsBodyBullet.setTransform(Initial_Position.x+sped, Initial_Position.y,0);


        //remove bullet on contact
        if (isContacted || isOutOfMap())
        {
            remove();
        }

    }

    public Vector2 getInitialPosition()
    {
        return Initial_Position;
    }

    public Vector2 getCurrentPosition()
    {
        return physicsBodyBullet.getPosition();
    }


    public void remove()
    {
        if (!physicsBodyBullet.getFixtureList().isEmpty())
        physicsBodyBullet.destroyFixture(physicsBodyBullet.getFixtureList().first());
        remove = true;
        isContacted = false;
        Bullet_Texture.dispose();
    }

    private boolean isOutOfMap()
    {
        return getCurrentPosition().x > (Gdx.graphics.getWidth() + 300 )/ Khartoosha.PPM
                ||
               getCurrentPosition().x < -300 / Khartoosha.PPM;
    }

    protected boolean isOutOfRange(float range)
    {
        float current_x = getCurrentPosition().x;
        float initial_x = getInitialPosition().x;
        float distance = Math.abs(current_x - initial_x);

        return distance >= range;
    }

}
