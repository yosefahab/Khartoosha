package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.screens.MainMenuScreen;
import com.test.game.screens.PlayScreen;

public class Khartoosha extends Game
{
	public static final float Gwidth = 1000, Gheight = 680;
	public static final String title = "Khartoosha";
	public static final int NUM_OF_CHARS = 3; //number of characters in the game


	// Pixel per meter, used for scaling objects wrt Box2D default scaling
	public static final float PPM = 100;

	// Gravity constant
	public static final float GRAVITY = -1;

	public static SpriteBatch batch;
	public static ShapeRenderer shapeRenderer;
	public soundsManager soundManager;

	@Override
	public void create ()
	{

		batch = new SpriteBatch();
		//musicVolume = DEFAULT_MUSIC_VOL;

		soundManager = new soundsManager();

		soundsManager.playMenuMusic();

		this.setScreen(new MainMenuScreen(this));
//		this.setScreen(new PlayScreen(this, 1, 1, 2));
//		this.setScreen(new PlayScreen(this, 1, 1));
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
