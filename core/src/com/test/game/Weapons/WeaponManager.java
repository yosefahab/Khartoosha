package com.test.game.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WeaponManager
{
    protected static void initPistol(Weapon weapon)
    {
        weapon.MAX_AMMO = 7;
        weapon.FORCE = 500;
        weapon.BULLET_SPEED = 0.125f;
        weapon.FIREING_RATE = 0.4f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/pistol/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/pistol/shooting.png");
        weapon.SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/shoot.ogg"));
        weapon.RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/reload.ogg"));
    }

    protected static void initMG(Weapon weapon)
    {
        weapon.MAX_AMMO = 30;
        weapon.FORCE = 550;
        weapon.BULLET_SPEED = 0.15f;
        weapon.FIREING_RATE = 0.10f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/mg/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/mg/shooting.png");
        weapon.SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/shoot.ogg"));
        weapon.RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/reload.ogg"));

    }

    protected static void initSniper(Weapon weapon)
    {
        weapon.MAX_AMMO = 5;
        weapon.FORCE = 800;
        weapon.BULLET_SPEED = 0.20f;
        weapon.FIREING_RATE = 1f;
        weapon.TEXTURE_IDLE = new Texture("vfx/weapons/sniper/idle.png");
        weapon.TEXTURE_SHOOTING = new Texture("vfx/weapons/sniper/shooting.png");
        weapon.SHOOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/shoot.ogg"));
        weapon.RELOAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/reload.ogg"));

    }





}
