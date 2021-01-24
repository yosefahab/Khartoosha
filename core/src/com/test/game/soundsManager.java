package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class soundsManager
{
    public static final float DEFAULT_MUSIC_VOL = 0;
    public static final float DEFAULT_SOUND_VOL = 1f;
    private static Sound player1Grunt, player2Grunt, click, gameOver,powerUp;
    private static Sound sniperShoot, pistolShoot, mgShoot, shotgunShoot, sniperReload, pistolReload, mgReload;

    public static float soundVolume; // IMPORTANT: soundVolume and musicVolume MUST be used in any play() function to be able to control them from settings

    public static Music gameMusic;
    public static Music menuMusic;
    public static float musicVolume;

    public soundsManager() {

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

        soundVolume = DEFAULT_SOUND_VOL;
        musicVolume = DEFAULT_MUSIC_VOL;

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(musicVolume);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(musicVolume);

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
        gameOver.play(soundVolume);
    }

    public static void pistolReload()
    {
        pistolReload.play(soundVolume);
    }
    public static void sniperReload() {
        sniperReload.play(soundVolume);
    }
    public static void mgReload() {
        mgReload.play(soundVolume);
    }

    public static void pistolFire() {
        pistolShoot.play(soundVolume);
    }
    public static void sniperFire() {
        sniperShoot.play(soundVolume);
    }
    public static void mgFire() {
        mgShoot.play(soundVolume);
    }
    public static void playGameMusic(){gameMusic.play(); }
    public static void playMenuMusic(){menuMusic.play(); }
    public static void stopMenuMusic(){
        menuMusic.stop();
        menuMusic.dispose();
    }
    public static void stopGameMusic(){
        gameMusic.stop();
        gameMusic.dispose();
    }
    public static void shotgunFire() { shotgunShoot.play(soundVolume); }
    public void dispose()
    {
        sniperShoot.dispose();
        pistolShoot.dispose();
        mgShoot.dispose();
        sniperReload.dispose();
        pistolReload.dispose();
        mgReload.dispose();
        player1Grunt.dispose();
        player2Grunt.dispose();
        click.dispose();
        gameOver.dispose();
        powerUp.dispose();
        gameMusic.stop();
        gameMusic.dispose();
    }
}
