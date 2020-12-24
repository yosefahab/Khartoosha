package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class SpeedBoost  extends PowerUp
{

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9970, maxRate = 10000;


    private final float MAX_TIME = 3;



    public SpeedBoost(World world)
    {
        super(world);
    }


    @Override
    public void update()
    {
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
    public void spawn() {

        //
        if (!isSpawned() && rand.nextInt(maxRate) > spawnRate)
        {
            pupBody.setType(BodyDef.BodyType.DynamicBody);
            setSpawned(true);
            currentPups++;
        }
    }

    @Override
    public void effect() {

    }




}
