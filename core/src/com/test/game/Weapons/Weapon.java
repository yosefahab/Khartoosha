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
import com.test.game.soundsManager;
import com.test.game.sprites.Character;

import java.util.ArrayList;


public class Weapon extends Sprite
{
    private Vector2 position;
    public ArrayList<Bullet> bullets;
    private World box2dWorld;
    private PlayScreen screen;
    private BodyDef weaponBody = new BodyDef();
    private Body physicsBodyWeapon;
    public boolean faceRight = true;


    private Character character;
    private boolean bulletFlipped;
    public int type = 0;

    // delta time for fireRate
    private float keyPressTimer = 0;
    private float shootingTimer = 3;

    private float yPos;
    protected float extraPos=0;
    private boolean stopFalling = false;


    public final static int MAX_TYPE = 3;
    private int CURRENT_AMMO;
    protected int MAX_AMMO;
    protected Vector2 FORCE;
    protected Vector2 BULLET_VELOCITY;
    protected float FIRING_RATE;
    public Texture TEXTURE_IDLE;
    public Texture BULLET_TEXTURE;
    protected Texture TEXTURE_SHOOTING;
    public boolean canShoot = false;


    public Weapon(World box2dWorld, PlayScreen screen, Character character)
    {
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

        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 1102, 217); //define region of certain texture in png
        setBounds(0,0,160/ Khartoosha.PPM,28/Khartoosha.PPM); //set size rendered texture
        setRegion(textureRegion);
    }

    public void update(float deltaTime)
    {
        if (faceRight)
        {
            setPosition(position.x + 0.4f + extraPos, yPos);
        } else
        {
            setPosition(position.x - 2f - extraPos, yPos);

        }
        if (stopFalling)
        {
            yPos = character.getBodyPosition().y + 0.4f;
            canShoot=true;
        }
        if (yPos < character.getBodyPosition().y + 0.3)
            stopFalling = true;
        if (!stopFalling)
            yPos -= 0.098f;
        ArrayList<Bullet> bulletsToBeRemoved = new ArrayList<>();

        for (Bullet bullet : bullets)
        {
            bullet.update(Gdx.graphics.getDeltaTime());

            if (type == 3 && bullet.isOutOfRange(WeaponManager.SHOTGUN_RANGE, false))
                bullet.remove();

            if (bullet.remove)
            {
                bulletsToBeRemoved.add(bullet);
            }
        }
        bullets.removeAll(bulletsToBeRemoved);


    }

    public void render(SpriteBatch sb)
    {
        keyPressTimer += Gdx.graphics.getDeltaTime();
        shootingTimer += Gdx.graphics.getDeltaTime();


        if (shootingTimer < 0.4f)
        {
            setTexture(TEXTURE_SHOOTING);
        }

        else if (shootingTimer > 0.4f)
        {

            setTexture(TEXTURE_IDLE);
        }

        for (Bullet bullet : bullets)
        {
            if (bulletFlipped)
            {
                bullet.setFlip(true, false);
            }
            if (type != 3)
                bullet.draw(sb);
        }

    }

    public void defineGunPhysics()
    {
        weaponBody.position.set(position);
        weaponBody.type = BodyDef.BodyType.StaticBody;
        physicsBodyWeapon = box2dWorld.createBody(weaponBody);

    }

    public int getAmmo()
    {
        return MAX_AMMO;
    }

    public void switchWeapon()
    {
        yPos = position.y + 4f;
        stopFalling = false;
        canShoot=false;
        switch (type)
        {
            case 0:
                WeaponManager.initPistol(this);
                soundsManager.pistolReload();
                break;
            case 1:
                WeaponManager.initMG(this);
                soundsManager.mgReload();
                break;
            case 2:
                WeaponManager.initSniper(this);
                soundsManager.sniperReload();
                break;
            case 3:
                WeaponManager.initShotgun(this);
        }
        CURRENT_AMMO = MAX_AMMO;

    }

    public void refillAmmo()
    {
        CURRENT_AMMO = MAX_AMMO;
    }


    public void shoot()
    {
        if (CURRENT_AMMO > 0 && keyPressTimer > FIRING_RATE && canShoot)
        {
            switch (type)
            {
                case 0:
                    soundsManager.pistolFire();
                    break;
                case 1:
                    soundsManager.mgFire();
                    break;
                case 2:
                    soundsManager.sniperFire();
                    break;
                case 3:
                    soundsManager.shotgunFire();
            }
            keyPressTimer = 0;
            shootingTimer = 0;
            CURRENT_AMMO--;

            if (CURRENT_AMMO < 1)
            {
                if (type == 0)
                    refillAmmo();
                else
                {
                    type--;
                    switchWeapon();
                }
            }

            if (faceRight)
            {
                bulletFlipped = false;
                bullets.add(new Bullet(box2dWorld, new Vector2(position.x + 0.4f, position.y + 0.4f), BULLET_VELOCITY, FORCE,BULLET_TEXTURE));

            } else
            {
                bulletFlipped = true;
                Vector2 NEGATIVE_VELOCITY = new Vector2(BULLET_VELOCITY.x * -1f, 0);
                Vector2 NEGATIVE_FORCE = new Vector2(FORCE.x * -1f, 0);
                bullets.add(new Bullet(box2dWorld, new Vector2(position.x - 0.8f, position.y + 0.4f), NEGATIVE_VELOCITY, NEGATIVE_FORCE,BULLET_TEXTURE));
            }

        }

    }


}
