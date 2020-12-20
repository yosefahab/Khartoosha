package com.test.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Khartoosha;

public abstract class State {
    protected OrthographicCamera gameCam;
    protected GameStateManager gsm;
    protected float aspectRatio = Khartoosha.Gwidth/Khartoosha.Gheight;
    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false,400,240);
    }
    public abstract void handleInput();
    public abstract void update();
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

    public void resize(int width, int height){
        aspectRatio = (float) width / height;
        gameCam.setToOrtho(false,width,height);
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = aspectRatio * gameCam.viewportWidth;
    }

}

