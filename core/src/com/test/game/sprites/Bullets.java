package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import javafx.scene.shape.Line;
import com.test.game.screens.PlayScreen;
import com.test.game.screens.PlayScreen;

public class Bullets extends Sprite {
    BodyDef bulletBody= new BodyDef();
    Body physicsBodyBullet;
    private TextureRegion textureRegion;
    World world;
    float x,y;
    Texture bul=new Texture("bullet.png");
    public boolean remove=false;
    private float speed;
    public Bullets(World world, PlayScreen screen, float x, float y,float speed)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));

        this.speed=speed;
        this.x=x;
        this.y=y;
        this.world=world;

        setTexture(bul);

        defineBulletPhysics();

        textureRegion = new TextureRegion(getTexture(),0,0,40,20); //define region of certain texture in png
        setBounds(0,0,40/Khartoosha.PPM,20/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);



    }

    public void defineBulletPhysics()
    {

        bulletBody.position.set(x,y);
        bulletBody.type = BodyDef.BodyType.KinematicBody;
        physicsBodyBullet= world.createBody(bulletBody);

    }
    private float sped=0;
    public void update(float delta){
        sped += speed;
        setPosition(x+sped, y); //update position of texture
        if (physicsBodyBullet.getPosition().x > Gdx.graphics.getWidth() ) // add if in the negative side l8r
        {
            remove=true;
        }

    }

}
