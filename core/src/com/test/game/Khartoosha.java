package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.screens.MainMenuScreen;

public class Khartoosha extends Game
{

	public static final float Gwidth= 1000, Gheight = 680;
	public static final String title = "Khartoosha";
	public static final int NUM_OF_CHARS = 3; //number of characters in the game
	public static final float DEFAULT_MUSIC_VOL = 0.7f;

	// Pixel per meter, used for scaling objects wrt Box2D default scaling
	public static final float PPM = 100;

	// Gravity constant
	public static final float GRAVITY = -1;

	public static SpriteBatch batch;

	public soundEffects soundManager;
	public static Music menuMusic;
	public static float musicVolume = DEFAULT_MUSIC_VOL;
	@Override
	public void create ()
	{

		batch = new SpriteBatch();

		//musicVolume = DEFAULT_MUSIC_VOL;

		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(musicVolume);
		menuMusic.play();

		soundManager = new soundEffects();

		this.setScreen(new MainMenuScreen(this));
		//this.setScreen(new PlayScreen(this, 1, 1, 2));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	@Override
	public void dispose ()
	{
		batch.dispose();
		super.dispose();
		soundManager.dispose();
	}
}
