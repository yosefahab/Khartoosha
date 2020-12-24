package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;

import java.util.Random;

public abstract class PowerUp extends Sprite {
    public World world;
    public Body pupBody;
    protected Random rand = new Random(); // Random generator

    public static final int MAXPUPS = 2; // max allowed spawned pups
    public static int currentPups = 0; //number of spawned pups at any moment


    protected float active_time = 0;
    private boolean isSpawned = false;
    private boolean isActive = false;


    public PowerUp(World world)
    {
        this.world = world;
        initPup();
    }

    private void initPup()
    {
        BodyDef bdef = new BodyDef();
        //initial position is set randomly in game world
        bdef.position.set(rand.nextInt((int)Khartoosha.Gwidth) / Khartoosha.PPM, Khartoosha.Gheight / Khartoosha.PPM + 1);
        bdef.type = BodyDef.BodyType.StaticBody;
        pupBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;
        pupBody.createFixture(fdef).setUserData("powerup");

    }

    /**
     *Returns true if spawned
     */
    public boolean isSpawned(){return isSpawned;}

    public void setSpawned(boolean spawned) {
        isSpawned = spawned;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     Function handles the spawn of any pup by checking conditions
     */
    public abstract void spawn();

    /**
       Applies effect of the powerup  to the game
     */
    public abstract void effect();

    /**
     * Update timers of pup
     */
    public abstract void update();

    /**
        Reset effects of the powerup
     */
    public void reset() {

        // TODO:add to effect function
        pupBody.setTransform(rand.nextInt(10), 5,0);
        pupBody.setType(BodyDef.BodyType.StaticBody);

        active_time = 0;
        setSpawned(false);

        currentPups--;

    }



}
