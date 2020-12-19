package com.test.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    protected OrthographicCamera gameCam;
    protected GameStateManager gsm;
    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false,400,240);
    }
    public abstract void handleInput();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
