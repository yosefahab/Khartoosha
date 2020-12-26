package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;

public class Bullets extends Sprite {

    BodyDef bulletBody= new BodyDef();
    Body physicsBodyBullet;
    private TextureRegion textureRegion;
    World world;
    Vector2 position;
    Texture bul=new Texture("bullet.png");
    public boolean remove=false;
    private float speed;



    // for collision detection
    public boolean isContacted = false;
    public int force;

    public Bullets(World world, PlayScreen screen, Vector2 position,float speed, int force)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));

        this.speed=speed;
        this.world=world;
        this.position=position;
        this.force = force;

        setTexture(bul);

        defineBulletPhysics();

        textureRegion = new TextureRegion(getTexture(),0,0,40,20); //define region of certain texture in png
        setBounds(0,0,40/Khartoosha.PPM,20/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);



    }

    public void defineBulletPhysics()
    {

        bulletBody.position.set(position);
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
    public void update(float delta){
        sped += this.speed;


        setPosition(position.x+sped, position.y); //update position of texture

        //attach physics body for collision
        physicsBodyBullet.setTransform(position.x+sped,position.y,0);

        if (physicsBodyBullet.getPosition().x > Gdx.graphics.getWidth() ) // add if in the negative side l8r
        {
            remove=true;
        }

        //remove bullet on contact
        if (isContacted)
        {
            remove = true;
            physicsBodyBullet.destroyFixture(physicsBodyBullet.getFixtureList().first());
            isContacted = false;
        }

    }


}
