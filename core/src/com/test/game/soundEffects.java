package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class soundEffects {
    private static Sound player1Grunt, player2Grunt, click, gameOver,powerUp;
    private static Sound sniper,pistol,mg,sniperR,pistolR,mgR;

    private static float soundLevel =1;

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
        player1Grunt.play(soundLevel);
    }

    public static void player2Grunt() {
        player2Grunt.play(soundLevel);
    }

    public static void click() {
        click.play(soundLevel);
    }

    public static void powerUp() {
        powerUp.play(soundLevel);
    }

    public static void gameOver() {
        gameOver.play(1);
    }

    public static void pistolReload() {
        pistolR.play(soundLevel);
    }
    public static void sniperReload() {
       sniperR.play(soundLevel);
    }
    public static void mgReload() {
        mgR.play(soundLevel);
    }

    public static void pistolFire() {
        pistol.play(soundLevel);
    }
    public static void sniperFire() {
        sniper.play(soundLevel);
    }
    public static void mgFire() {
        mg.play(soundLevel);
    }

    public void dispose(){}
}
