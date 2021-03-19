package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.screens.menu_screens.MainMenuScreen;

public class Khartoosha extends Game
{
	public static final float Gwidth = 1000, Gheight = 680;
	public static final String title = "Khartoosha";


	// Pixel per meter, used for scaling objects wrt Box2D default scaling
	public static final float PPM = 100;


	public static SpriteBatch batch;

	@Override
	public void create ()
	{

		batch = new SpriteBatch();
		SoundsManager.init();
		SoundsManager.playMenuMusic();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	@Override
	public void dispose ()
	{
		SoundsManager.dispose();
		batch.dispose();
		super.dispose();
	}
}
