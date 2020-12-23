package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.screens.PlayScreen;

public class Khartoosha extends Game
{

	public static final float Gwidth= 800, Gheight = 480;
	public static final String title = "Khartoosha";

	// Pixel per meter, used for scaling objects wrt Box2D default scaling
	public static final float PPM = 100;

	// Gravity constant
	public static final float GRAVITY = -1;

	public SpriteBatch batch;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		/*
		* PLEASE DISPOSE OF SCREENS BEFORE CREATING NEW ONES
		* USE THIS.DISPOSE()
		*
		* */
		this.setScreen(new PlayScreen(this));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	@Override
	public void dispose ()
	{
		super.dispose();
	}
}
