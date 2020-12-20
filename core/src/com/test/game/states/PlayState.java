package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;


public class PlayState extends State {
    private Character Player1,Player2;
    private Texture background;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        Player1 = new Character(10,20,false);
        gameCam.setToOrtho(false, Khartoosha.Gwidth,Khartoosha.Gheight);
        background = new Texture("background.png");
    }
    public PlayState(GameStateManager gsm, String player1, String player2) {
        super(gsm);
        Player1 = new Character(10,20,false);
        Player2 = new Character(100,20,true);
        gameCam.setToOrtho(false, Khartoosha.Gwidth,Khartoosha.Gheight);
        background = new Texture("background.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            Player1.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Player1.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Player1.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
            Player1.fire();
        }
        if (Player2!=null){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            Player2.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Player2.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Player2.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Player2.fire();
        }
        }
    }

    @Override
    public void update() {
        gameCam.update();
        handleInput();
        Player1.update();
        if (Player2!= null){Player2.update();}
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Khartoosha.Gwidth, Khartoosha.Gheight);
        sb.draw(Player1.getTexture(),Player1.getPosition().x,Player1.getPosition().y,50,100);
        if (Player2!= null)
            sb.draw(Player2.getTexture(),Player2.getPosition().x,Player2.getPosition().y,50,100);
        sb.end();
    }
    @Override
    public void dispose(){
        background.dispose();
        Player1.dispose();
        if (Player2!=null)
            Player2.dispose();
    }
    @Override
    public void resize(int width, int height){
        System.out.println("Resized");
        aspectRatio = (float) width / height;
        gameCam.setToOrtho(false,width,height);
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = aspectRatio * width;
    }
}
