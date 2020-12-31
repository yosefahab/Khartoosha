package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
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
    private int KEY;
    Texture texture=new Texture("gun.png");
    Vector2 position;
    ArrayList<Bullets> bullets;
    World world;
    PlayScreen screen;
    public float bulletSpeed;
    BodyDef weaponBody= new BodyDef();
    Body physicsBodyWeapon;
    private TextureRegion textureRegion;
    public boolean faceRight=true;
    private int ammo;
    private int force;
    private float fireRate = 1;
    private Character character;
    private boolean bulletFlipped;
    private int type;

    // delta time for fireRate
    private float keyPressTimer = 0;

    // Weapon Definitions Constants

        // Pistol
        public static final int PISTOL_AMMO = 1000;
        public static final int PISTOL_FORCE = 100;
        public static final float PISTOL_SPEED = 0.25f;
        public static final float PISTOL_RATE = 0.5f;
        public static final int PISTOL_TYPE = 0;


    //MachineGun
        public static final int MG_AMMO = 30;
        public static final int MG_FORCE = 170;
        public static final float MG_SPEED = 0.25f;
        public static final float MG_RATE = 0.25f;
        public static final int MG_TYPE = 1;

        //Sniper
        public static final int Sniper_AMMO = 5;
        public static final int Sniper_FORCE = 300;
        public static final float Sniper_SPEED = 0.25f;
        public static final float Sniper_RATE = 2f;
        public static final int Sniper_TYPE = 2;



    public Weapon(World world, PlayScreen screen, Character character, float bulletSpeed, int ammo, int force, float fireRate, int type,  int KEY)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));
        this.bulletSpeed = bulletSpeed;
        this.world=world;
        this.position=character.getBodyPosition();
        this.screen=screen;
        bullets = new ArrayList<Bullets>();
        this.ammo=ammo;
        this.force = force;
        this.KEY=KEY;
        this.character=character;
        this.fireRate = fireRate;
        this.type = type;

        if (type == PISTOL_TYPE)
            texture = new Texture("gun.png");
        else
            texture = new Texture("rifle.png");

        setTexture(texture);
        defineGunPhysics();
        character.currentWeapon = this;

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
        keyPressTimer += Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(KEY) & ammo > 0 && keyPressTimer > fireRate)
        {
            keyPressTimer = 0;
            character.setPosition(character.getBodyPosition().x-200,character.getBodyPosition().y);
            ammo--;
            if (faceRight) {
                bulletFlipped=false;
                bullets.add(new Bullets(world,screen,new Vector2(position.x+0.4f,position.y+0.5f), bulletSpeed, force) );

            }
            else
            {
                bulletFlipped=true;
                bullets.add(new Bullets(world,screen,new Vector2(position.x-0.8f,position.y+0.5f),-bulletSpeed,  -force));
            }

        }
        for(Bullets bullet:bullets)
        {
            if (bulletFlipped)
            {
                bullet.setFlip(true,false);
            }
            bullet.draw(sb);


        }

    }

    public void defineGunPhysics()
    {
        weaponBody.position.set(position);
        weaponBody.type = BodyDef.BodyType.StaticBody;
        physicsBodyWeapon= world.createBody(weaponBody);

    }

    public int getAmmo() {
        return ammo;
    }

    public void refillAmmo()
    {
        switch (type)
        {
            case PISTOL_TYPE: ammo = PISTOL_AMMO;
            break;
            case MG_TYPE: ammo = MG_AMMO;
            break;
            case Sniper_TYPE: ammo = Sniper_AMMO;
            break;
        }
    }
}
