package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

public class Weapon extends Sprite
{
    Texture texture=new Texture("2.png");
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

    private Sound SHOOT_SOUND;
    private Sound RELOAD_SOUND;

    private Character character;
    private boolean bulletFlipped;
    public int type;

    // delta time for fireRate
    private float keyPressTimer = 0;
    private float shootingTimer=3;

    private float yPos;
    private boolean stopFalling=false;

    // Weapon Definitions Constants

        // Pistol
        public static final int PISTOL_AMMO = 7;
        public static final int PISTOL_FORCE = 500;
        public static final float PISTOL_SPEED = 0.25f;
        public static final float PISTOL_RATE = 0.4f;
        public static final int PISTOL_TYPE = 0;
        private final Texture PISTOL_TEXTURE = new Texture("1.png");
        private static final Sound PISTOL_SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/shoot.ogg"));
        private static final Sound PISTOL_RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/reload.ogg"));

        //MachineGun
        public static final int MG_AMMO = 30;
        public static final int MG_FORCE = 550;
        public static final float MG_SPEED = 0.25f;
        public static final float MG_RATE = 0.10f;
        public static final int MG_TYPE = 1;
        private final Texture MG_TEXTURE = new Texture("rifle.png");
        private static final Sound MG_SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/shoot.ogg"));
        private static final Sound MG_RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/reload.ogg"));

    //Sniper
        public static final int Sniper_AMMO = 5;
        public static final int Sniper_FORCE = 700;
        public static final float Sniper_SPEED = 0.25f;
        public static final float Sniper_RATE = 1f;
        public static final int Sniper_TYPE = 2;
        private final Texture Sniper_TEXTURE = new Texture("sniper.png");
        private static final Sound SNIPER_SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/shoot.ogg"));
        private static final Sound SNIPER_RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/reload.ogg"));




    private Texture shootingTexture=new Texture("2.png");

    public Weapon(World world, PlayScreen screen, Character character)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));
        this.bulletSpeed = bulletSpeed;
        this.world=world;
        this.position=character.getBodyPosition();
        position.y+=4f;
        this.screen=screen;
        bullets = new ArrayList<Bullets>();
        this.ammo=ammo;
        this.force = force;
        this.character=character;
        this.fireRate = fireRate;
        this.type = type;

        setTexture(texture);
        defineGunPhysics();
        character.currentWeapon = this;

        textureRegion = new TextureRegion(getTexture(),0,0,193,130); //define region of certain texture in png
        setBounds(0,0,58/ Khartoosha.PPM,29/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);

        switchWeapon();
    }

    public void update(float deltaTime)
    {
        if (faceRight)
        {
            setPosition(position.x+0.7f, yPos);
            if (stopFalling)
            {
                yPos=character.getBodyPosition().y+0.3f;
            }
            if (yPos<character.getBodyPosition().y+0.3)
                stopFalling=true;
            if (!stopFalling)
                yPos-=0.098f;
        }
        else if (!faceRight)
        {
            setPosition(position.x-0.7f, yPos);
            if (stopFalling)
            {
                yPos=character.getBodyPosition().y+0.3f;
            }
            if (yPos<character.getBodyPosition().y+0.3)
                stopFalling=true;
            if (!stopFalling)
                yPos-=0.098f;
        }
        ArrayList<Bullets> bulletsToBeRemoved = new ArrayList<Bullets>();
        for(Bullets bullet:bullets )
        {
            bullet.update(Gdx.graphics.getDeltaTime());
            if (bullet.remove)
                bulletsToBeRemoved.add(bullet);

        }
        bullets.removeAll(bulletsToBeRemoved);



    }
    public void render(SpriteBatch sb)
    {
        keyPressTimer += Gdx.graphics.getDeltaTime();
        shootingTimer += Gdx.graphics.getDeltaTime();
        if (shootingTimer < 0.4f)
            setTexture(shootingTexture);
        else if (shootingTimer > 0.4f)
            setTexture(texture);

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

    public void switchWeapon()
    {
        yPos=position.y+4f;
        stopFalling=false;
        if (type==PISTOL_TYPE)
        {
            texture=PISTOL_TEXTURE;
            ammo = PISTOL_AMMO;
            force = PISTOL_FORCE;
            fireRate = PISTOL_RATE;
            bulletSpeed=PISTOL_SPEED;
            SHOOT_SOUND = PISTOL_SHOOT_SOUND;
            RELOAD_SOUND = PISTOL_RELOAD_SOUND;
        }
        else if (type==MG_TYPE)
        {
            texture=MG_TEXTURE;
            ammo = MG_AMMO;
            force = MG_FORCE;
            fireRate=MG_RATE;
            bulletSpeed=MG_SPEED;
            SHOOT_SOUND = MG_SHOOT_SOUND;
            RELOAD_SOUND = MG_RELOAD_SOUND;
        }
        else if (type == Sniper_TYPE)
        {
            texture=Sniper_TEXTURE;
            ammo = Sniper_AMMO;
            force = Sniper_FORCE;
            fireRate=Sniper_RATE;
            bulletSpeed=Sniper_SPEED;
            SHOOT_SOUND = SNIPER_SHOOT_SOUND;
            RELOAD_SOUND = SNIPER_RELOAD_SOUND;
        }
        RELOAD_SOUND.play();
    }
    public void refillAmmo()
    {
        if (type==0)
        {
            ammo=PISTOL_AMMO;
        }
        else if (type == 1)
        {
            ammo=MG_AMMO;

        }
        else
        {
            ammo=Sniper_AMMO;
        }
    }


    public void shoot()
    {
        if (ammo > 0 && keyPressTimer > fireRate)
        {
            SHOOT_SOUND.play();
            keyPressTimer = 0;
            shootingTimer = 0;
            ammo--;
            if (ammo < 1)
            {
                if(type==0)
                    refillAmmo();
                else
                {
                    type--;
                    switchWeapon();
                }
            }
            if (faceRight) {
                bulletFlipped=false;
                bullets.add(new Bullets(world,screen,new Vector2(position.x+0.4f,position.y+0.4f), bulletSpeed, force) );

            }
            else
            {
                bulletFlipped=true;
                bullets.add(new Bullets(world,screen,new Vector2(position.x-0.8f,position.y+0.4f),-bulletSpeed,  -force));
            }

        }

    }

}
