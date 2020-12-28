package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;

public class Armor extends PowerUp {

    private final int spawnRate = 9970, maxRate = 10000;
    private final float MAX_TIME = 10;


    public Armor(World world) {
        super(world);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Khartoosha.PPM);

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

        // activate armor
        player.isArmored = true;

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
        attachedChar.isArmored = false;
        attachedChar = null;
        currentPups--;

    }
}
