package com.test.game.Weapons;

import com.badlogic.gdx.graphics.Texture;

public class WeaponManager
{
    protected static float SHOTGUN_RANGE = 0.75F;
    protected static void initPistol(Weapon weapon)
    {
        weapon.MAX_AMMO = 7;
        weapon.FORCE = 500;
        weapon.BULLET_SPEED = 0.125f;
        weapon.FIRING_RATE = 0.4f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/pistol/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/pistol/shooting.png");
    }

    protected static void initMG(Weapon weapon)
    {
        weapon.MAX_AMMO = 30;
        weapon.FORCE = 550;
        weapon.BULLET_SPEED = 0.15f;
        weapon.FIRING_RATE = 0.10f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/mg/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/mg/shooting.png");

    }

    protected static void initSniper(Weapon weapon)
    {
        weapon.MAX_AMMO = 5;
        weapon.FORCE = 800;
        weapon.BULLET_SPEED = 0.20f;
        weapon.FIRING_RATE = 1f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/sniper/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/sniper/shooting.png");

    }

    protected static void initShotgun(Weapon weapon)
    {
        weapon.MAX_AMMO = 10;
        weapon.FORCE = 1000;
        weapon.BULLET_SPEED = 0.1F;
        weapon.FIRING_RATE = 1;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/shotgun/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/shotgun/shooting.png");

    }





}
