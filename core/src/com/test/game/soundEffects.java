package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class soundEffects
{
    private static Sound player1Grunt, player2Grunt, click, gameOver,powerUp;
    private static Sound sniperShoot, pistolShoot, mgShoot, shotgunShoot, sniperReload, pistolReload, mgReload;


    public soundEffects() {

        player1Grunt = Gdx.audio.newSound(Gdx.files.internal("sfx/player/grunt1.ogg"));
        player2Grunt = Gdx.audio.newSound(Gdx.files.internal("sfx/player/grunt2.ogg"));
        click = Gdx.audio.newSound(Gdx.files.internal("sfx/menu/click.ogg"));
        powerUp = Gdx.audio.newSound(Gdx.files.internal("sfx/powerUp.ogg"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("sfx/menu/gameOver.ogg"));

       pistolShoot = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/shoot.ogg"));
       sniperShoot = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/shoot.ogg"));
       mgShoot = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/shoot.ogg"));
       shotgunShoot = Gdx.audio.newSound(Gdx.files.internal("sfx/shotgun/shoot.ogg"));
        pistolReload = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/reload.ogg"));
        sniperReload = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/reload.ogg"));
        mgReload = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/reload.ogg"));


    }

    public static void player1Grunt() {
        player1Grunt.play();
    }

    public static void player2Grunt() {
        player2Grunt.play();
    }

    public static void click() {
        click.play();
    }

    public static void powerUp() {
        powerUp.play();
    }

    public static void gameOver() {
        gameOver.play();
    }

    public static void pistolReload() {
        pistolReload.play();
    }
    public static void sniperReload() {
       sniperReload.play();
    }
    public static void mgReload() {
        mgReload.play();
    }

    public static void pistolFire() {
        pistolShoot.play();
    }
    public static void sniperFire() {
        sniperShoot.play();
    }
    public static void mgFire() {
        mgShoot.play();
    }
    public static void shotgunFire()
    {
        shotgunShoot.play();
    }


    public void dispose(){}
}
