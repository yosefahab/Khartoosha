package com.test.game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class WeaponManager
{
    protected static final float SHOTGUN_RANGE = 0.75F;
    protected static final float BOMB_RANGE = 1F;
    protected static void initPistol(Weapon weapon)
    {
        weapon.MAX_AMMO = 7;
        weapon.FORCE = new Vector2(600, 0);
        weapon.BULLET_VELOCITY = new Vector2(0.125f, 0);
        weapon.FIRING_RATE = 0.4f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/pistol/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/pistol/shooting.png");
        weapon.BULLET_TEXTURE = new Texture ("vfx/weapons/pistol/bullet.png");
        weapon.extraPos = 0;

    }

    protected static void initMG(Weapon weapon)
    {
        weapon.MAX_AMMO = 30;
        weapon.FORCE = new Vector2(650, 0);
        weapon.BULLET_VELOCITY.x = 0.15f;
        weapon.FIRING_RATE = 0.10f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/mg/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/mg/shooting.png");
        weapon.BULLET_TEXTURE = new Texture ("vfx/weapons/mg/bullet.png");
        weapon.extraPos=0;
    }

    protected static void initSniper(Weapon weapon)
    {
        weapon.MAX_AMMO = 5;
        weapon.FORCE = new Vector2(1000, 0);
        weapon.BULLET_VELOCITY = new Vector2(0.20f,0);
        weapon.FIRING_RATE = 1f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/sniper/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/sniper/shooting.png");
        weapon.BULLET_TEXTURE = new Texture ("vfx/weapons/sniper/bullet.png");
        weapon.extraPos=-0.4f;
    }

    protected static void initShotgun(Weapon weapon)
    {
        weapon.MAX_AMMO = 10;
        weapon.FORCE = new Vector2(1200, 0);
        weapon.BULLET_VELOCITY = new Vector2(0.1f, 0);
        weapon.FIRING_RATE = 1;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/shotgun/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/shotgun/shooting.png");
        weapon.extraPos=-0.3f;
    }

}
