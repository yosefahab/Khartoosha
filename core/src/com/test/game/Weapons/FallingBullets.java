package com.test.game.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;

public class FallingBullets extends Sprite {
    private World box2dWorld;
    private Vector2 initialPosition;
    public boolean removeFromArray = false;
    private int direction;
    FallingBullets(World world, Vector2 position, int direction)
    {
        this.box2dWorld = world;
        initialPosition=position;
        this.direction=direction;
        setTexture(new Texture("vfx/bulletTrail.png"));
        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 258, 734); //define region of certain texture in png
        setRegion(textureRegion);
        setBounds(position.x, position.y, 5 / Khartoosha.PPM, 15 / Khartoosha.PPM); //set size rendered texture
    }
    private float fallingDistance;
    private float windBlow;
    public void update()
    {
        final float GRAVITY=-0.098f;
        final float wind=0.05f;
        windBlow+=wind;
        fallingDistance+=GRAVITY;
        setPosition(initialPosition.x+(windBlow*direction),initialPosition.y-0.5f+fallingDistance);
        if (-getY() > Khartoosha.Gheight/Khartoosha.PPM)
        {
            removeFromArray=true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q))
        {
            System.out.println(getY());
            System.out.println(Khartoosha.Gheight/Khartoosha.PPM);
        }
    }
    public void dispose()
    {

    }
}
