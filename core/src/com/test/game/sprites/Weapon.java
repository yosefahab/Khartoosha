package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;

import java.util.ArrayList;

public class Weapon extends Sprite {
    Texture texture=new Texture("gun.png");
    Vector2 position;
    ArrayList<Bullets> bullets;
    World world;
    PlayScreen screen;
    public float speed;
    BodyDef weaponBody= new BodyDef();
    Body physicsBodyWeapon;
    private TextureRegion textureRegion;
    public boolean faceRight=true;
    private int ammo;
    private int force;
    public Weapon(World world,PlayScreen screen,Vector2 position,float speed, int ammo, int force)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));
        this.speed=speed;
        this.world=world;
        this.position=position;
        this.screen=screen;
        bullets = new ArrayList<Bullets>();
        this.ammo=ammo;
        this.force = force;
        setTexture(texture);

        defineGunPhysics();

        textureRegion = new TextureRegion(getTexture(),0,0,193,130); //define region of certain texture in png
        setBounds(0,0,40/ Khartoosha.PPM,20/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);

    }

    public void update(float deltaTime)
    {
        if (faceRight)
        {
            setPosition(position.x+0.5f, position.y+0.4f);
        }
        else
            setPosition(position.x-0.5f, position.y+0.4f);
        ArrayList<Bullets> bulletsToBeRemoved = new ArrayList<Bullets>();
        for(Bullets bullet:bullets )
        {
            bullet.update(deltaTime);
            if (bullet.remove)
                bulletsToBeRemoved.add(bullet);

        }
        bullets.removeAll(bulletsToBeRemoved);



    }
    public void render(SpriteBatch sb)
    {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) & ammo > 0)
        {
            ammo--;
            if (faceRight) {
                bullets.add(new Bullets(world,screen,new Vector2(position.x+0.4f,position.y+0.5f),speed, force) );
            }
            else
                bullets.add(new Bullets(world,screen,new Vector2(position.x-0.8f,position.y+0.5f),-speed,  force));

        }
        for(Bullets bullet:bullets)
        {
            bullet.draw(sb);
        }

    }

    public void defineGunPhysics()
    {
        weaponBody.position.set(position);
        weaponBody.type = BodyDef.BodyType.StaticBody;
        physicsBodyWeapon= world.createBody(weaponBody);

    }

}
