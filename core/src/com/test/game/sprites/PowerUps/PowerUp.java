package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;

import java.util.Random;

public abstract class PowerUp extends Sprite {
    public World world;
    public Body pupBody;
    protected Random rand = new Random(); // Random generator

    public static final int MAXPUPS = 5; // max allowed spawned pups
    public static int currentPups = 0; //number of spawned pups at any moment

    public Character attachedChar;

    public float active_time = 0;
    private boolean isSpawned = false;
    private boolean isActive = false;
    protected boolean isContacted = false;
    private boolean isOneShot = true; //Indicator for powerups that don't run for certain time (Extra life, upgrade weapon, etc..)
    public final int MAX_PLATFORMS = 6;
    public int platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    protected final int spawnRate, maxRate;

    protected final int MAX_TIME;


    public PowerUp(World world, TextureAtlas.AtlasRegion region, int spawnRate, int maxRate, int maxTime)
    {
        super(region);
        this.spawnRate = spawnRate;
        this.maxRate = maxRate;
        this.MAX_TIME = maxTime;
        if (MAX_TIME == 0)
            this.isOneShot = true;
        this.world = world;
        initPup();
    }

    private void initPup()
    {
        BodyDef bdef = new BodyDef();
        //initial position is set randomly in game world
        //bdef.position.set(rand.nextInt((int)Khartoosha.Gwidth - 150) / Khartoosha.PPM + (100 / Khartoosha.PPM) , Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM));
//        bdef.position.set(rand.nextInt((int)Khartoosha.Gwidth - 400) / Khartoosha.PPM +(400 / Khartoosha.PPM),
//                Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM));
        bdef.position.set(rand.nextInt((int)Khartoosha.Gwidth - 400) / Khartoosha.PPM +(200 / Khartoosha.PPM),
                Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM));

        bdef.type = BodyDef.BodyType.StaticBody;
        pupBody = world.createBody(bdef);
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

    public boolean isContacted() { return isContacted; }

    public void setContacted(boolean contacted) { isContacted = contacted; }

    /**
     Function handles the spawn of any pup by checking conditions
     */
    public void spawn() {

        // if not spawned and not active spawn it
        if (!isSpawned() && !isActive() && rand.nextInt(maxRate) > spawnRate)
        {
            pupBody.setType(BodyDef.BodyType.DynamicBody);
            setSpawned(true);
            currentPups++;
        }
    }

    /**
       Applies effect of the powerup  to the game
     */
    public abstract void effect(Character c);

    /**
     * Update timers of pup
     */
    public void update()
    {
        if (pupBody.getPosition().y < - 2)
            resetPupPosition();
        setPosition(pupBody.getPosition().x-getWidth()/5, pupBody.getPosition().y-getHeight()/3);

        if (isContacted)
        {
            effect(attachedChar);
            isContacted = false;
        }
        if (isActive() && !isOneShot)
        {
            active_time += Gdx.graphics.getDeltaTime();
            if (active_time > MAX_TIME)
            {
                reset();
            }

        }

    }

    /**
        Reset effects of the powerup
     */
    public void reset() {
        setSpawned(false);
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);

    }

    public void resetPupPosition()
    {
        pupBody.setTransform(rand.nextInt((int)Khartoosha.Gwidth - 400) / Khartoosha.PPM + (400 / Khartoosha.PPM),
                Khartoosha.Gheight / Khartoosha.PPM + 3,0);
        pupBody.setType(BodyDef.BodyType.StaticBody);
    }




}
