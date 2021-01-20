package com.test.game.Weapons;

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
import com.test.game.soundEffects;
import com.test.game.sprites.Character;
import java.util.ArrayList;


public class Weapon extends Sprite
{
    Vector2 position;
    ArrayList<Bullets> bullets;
    World box2dWorld;
    PlayScreen screen;
    BodyDef weaponBody= new BodyDef();
    Body physicsBodyWeapon;
    public boolean faceRight=true;


    private Character character;
    private boolean bulletFlipped;
    public int type = 0;

    // delta time for fireRate
    private float keyPressTimer = 0;
    private float shootingTimer = 3;

    private float yPos;
    private boolean stopFalling=false;

    public final static int MAX_TYPE = 2;
    private  int CURRENT_AMMO;
    protected int MAX_AMMO;
    protected int FORCE;
    protected float BULLET_SPEED;
    protected float FIRING_RATE;
    protected Texture TEXTURE_IDLE;
    protected Texture TEXTURE_SHOOTING;
    public boolean isShoting=false;


    public Weapon(World box2dWorld, PlayScreen screen, Character character)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));
        this.box2dWorld = box2dWorld;
        this.position = character.getBodyPosition();
        position.y += 4f;
        this.screen = screen;
        bullets = new ArrayList<>();
        this.character = character;

        switchWeapon();
        setTexture(TEXTURE_IDLE);
        defineGunPhysics();
        character.currentWeapon = this;

        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 193, 130); //define region of certain texture in png
        setBounds(0,0,58/ Khartoosha.PPM,29/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);
    }

    public void update(float deltaTime)
    {
        if (faceRight)
        {
            setPosition(position.x+0.5f, yPos);
        }
        else {
            setPosition(position.x - 1f, yPos);

        }
        if (stopFalling)
        {
            yPos=character.getBodyPosition().y+0.3f;
        }
        if (yPos<character.getBodyPosition().y+0.3)
            stopFalling=true;
        if (!stopFalling)
            yPos-=0.098f;
        ArrayList<Bullets> bulletsToBeRemoved = new ArrayList<>();
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
            setTexture(TEXTURE_SHOOTING);

        else if (shootingTimer > 0.4f)
            setTexture(TEXTURE_IDLE);

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
        physicsBodyWeapon= box2dWorld.createBody(weaponBody);

    }

    public int getAmmo() {
        return MAX_AMMO;
    }

    public void switchWeapon()
    {
        yPos = position.y+4f;
        stopFalling = false;

        switch (type)
        {
            case 0: WeaponManager.initPistol(this);
            soundEffects.pistolReload();
            break;
            case 1: WeaponManager.initMG(this);
            soundEffects.mgReload();
            break;
            case 2: WeaponManager.initSniper(this);
            soundEffects.sniperReload();
            break;
        }
        CURRENT_AMMO = MAX_AMMO;

    }
    public void refillAmmo()
    {
        CURRENT_AMMO = MAX_AMMO;
    }


    public void shoot()
    {
        //isShoting=true;
        if (CURRENT_AMMO > 0 && keyPressTimer > FIRING_RATE)
        {
            switch (type)
            {
                case 0:
                    soundEffects.pistolFire();
                    break;
                case 1:
                    soundEffects.mgFire();
                    break;
                case 2:
                    soundEffects.sniperFire();
                    break;
            }
            keyPressTimer = 0;
            shootingTimer = 0;
            CURRENT_AMMO--;

            if (CURRENT_AMMO < 1)
            {
                if(type == 0)
                    refillAmmo();
                else
                {
                    type--;
                    switchWeapon();
                }
            }

            if (faceRight) {
                bulletFlipped = false;
                bullets.add(new Bullets(box2dWorld,new Vector2(position.x+0.4f,position.y+0.4f), BULLET_SPEED, FORCE) );

            }
            else
            {
                bulletFlipped = true;
                bullets.add(new Bullets(box2dWorld,new Vector2(position.x-0.8f,position.y+0.4f),-BULLET_SPEED,  -FORCE));
            }

        }

    }

}
