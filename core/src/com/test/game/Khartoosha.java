package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.states.GameStateManager;
import com.test.game.states.MenuState;

public class Khartoosha extends Game {

	public static final float Gwidth= 800, Gheight = 480;
	public static final String title = "Khartoosha";
	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;
	@Override
	public void create () {

		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("Castor.mp3" ));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		gsm.push(new MenuState(gsm));
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		music.dispose();
	}
}
