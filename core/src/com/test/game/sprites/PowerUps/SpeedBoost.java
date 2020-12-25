package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;

public class SpeedBoost  extends PowerUp
{
    public final int type = 0;
    public Character attachedChar;

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9970, maxRate = 10000;

    private final float speedBoost = 10;
    private final float MAX_TIME = 10;

    //detects contacts
    private boolean isContacted = false;


    public SpeedBoost(World world)
    {
        super(world);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);


    }


    @Override
    public void spawn() {

        // if not spawned and not active spawn it
        if (!isSpawned() && !isActive() && rand.nextInt(maxRate) > spawnRate)
        {
            pupBody.setType(BodyDef.BodyType.DynamicBody);
            setSpawned(true);
            currentPups++;
        }
    }



    @Override
    public void effect(Character player) {
        //activate
        setActive(true);
        // reset pup Position
        pupBody.setTransform(rand.nextInt((int)Khartoosha.Gwidth - 100) / Khartoosha.PPM + 2,
                Khartoosha.Gheight / Khartoosha.PPM + 3,0);
        pupBody.setType(BodyDef.BodyType.StaticBody);

        // increase player speed
        player.setSpeedCap(speedBoost);

    }


    @Override
    public void update()
    {
        if (isContacted)
        {
            effect(attachedChar);
            isContacted = false;
        }
        if (isActive())
        {
            active_time += Gdx.graphics.getDeltaTime();
            if (active_time > MAX_TIME)
            {
                reset();
            }

        }

    }

    @Override
    public void reset() {
        active_time = 0;
        setSpawned(false);
        setActive(false);
        attachedChar.resetSpeedCap();
        attachedChar = null;
        currentPups--;
    }


    public boolean isContacted() {
        return isContacted;
    }

    public void setContacted(boolean contacted) {
        isContacted = contacted;
    }


}
