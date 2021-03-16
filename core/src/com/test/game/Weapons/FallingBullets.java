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
    World box2dWorld;
    Vector2 initialPosition;
    public boolean removeFromArray = false;
    FallingBullets(World world, Vector2 position, Texture texture)
    {
        this.box2dWorld = world;
        initialPosition=position;
        setTexture(texture);
        TextureRegion textureRegion = new TextureRegion(getTexture(), 0, 0, 544, 122); //define region of certain texture in png
        setRegion(textureRegion);
        setBounds(position.x, position.y, 120 / Khartoosha.PPM, 30 / Khartoosha.PPM); //set size rendered texture
    }
    private float fallingDistance;
    public void update()
    {
        final float GRAVITY=-0.098f;
        fallingDistance+=GRAVITY;
        setPosition(initialPosition.x,initialPosition.y+fallingDistance);
        if (-getY() > Khartoosha.Gheight/Khartoosha.PPM)
        {
            removeFromArray=true;
            System.out.println("ANA BARA TMAM?");
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
    /*protected boolean isOutOfRange(float range, boolean includeVerticalRange)
    {
        Vector2 currentPosition = getCurrentPosition();
        Vector2 initialPosition = getInitialPosition();

        float distanceX = Math.abs(currentPosition.x - initialPosition.x);
        float distanceY = Math.abs(currentPosition.y - initialPosition.y);

        if (!includeVerticalRange)
            return distanceX >= range;

        return distanceX >= range || distanceY >= range;
    }*/
}
