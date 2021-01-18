package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class soundEffects {
    public static final float DEFAULT_SOUND_VOL = 1f;
    private static Sound player1Grunt, player2Grunt, click, gameOver,powerUp;
    private static Sound sniper,pistol,mg,sniperR,pistolR,mgR;

    public static float soundVolume = DEFAULT_SOUND_VOL;

    public soundEffects() {

        player1Grunt = Gdx.audio.newSound(Gdx.files.internal("sfx/player/grunt1.ogg"));
        player2Grunt = Gdx.audio.newSound(Gdx.files.internal("sfx/player/grunt2.ogg"));
        click = Gdx.audio.newSound(Gdx.files.internal("sfx/menu/click.ogg"));
        powerUp = Gdx.audio.newSound(Gdx.files.internal("sfx/powerUp.ogg"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("sfx/menu/gameOver.ogg"));

        pistol = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/shoot.ogg"));
        sniper = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/shoot.ogg"));
        mg = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/shoot.ogg"));

        pistolR = Gdx.audio.newSound(Gdx.files.internal("sfx/pistol/reload.ogg"));
        sniperR = Gdx.audio.newSound(Gdx.files.internal("sfx/sniper/reload.ogg"));
        mgR = Gdx.audio.newSound(Gdx.files.internal("sfx/mg/reload.ogg"));


    }

    public static void player1Grunt() {
        player1Grunt.play(soundVolume);
    }

    public static void player2Grunt() {
        player2Grunt.play(soundVolume);
    }

    public static void click() {
        click.play(soundVolume);
    }

    public static void powerUp() {
        powerUp.play(soundVolume);
    }

    public static void gameOver() {
        gameOver.play(1);
    }

    public static void pistolReload() {
        pistolR.play(soundVolume);
    }
    public static void sniperReload() {
       sniperR.play(soundVolume);
    }
    public static void mgReload() {
        mgR.play(soundVolume);
    }

    public static void pistolFire() {
        pistol.play(soundVolume);
    }
    public static void sniperFire() {
        sniper.play(soundVolume);
    }
    public static void mgFire() {
        mg.play(soundVolume);
    }

    public void dispose(){
        sniper.dispose();
        pistol.dispose();
        mg.dispose();
        sniperR.dispose();
        pistolR.dispose();
        mgR.dispose();
        player1Grunt.dispose();
        player2Grunt.dispose();
        click.dispose();
        gameOver.dispose();
        powerUp.dispose();
    }
}
