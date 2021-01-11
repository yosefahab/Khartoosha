package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;

public class RefillAmmo extends PowerUp {

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9980, maxRate = 10000;

    private TextureRegion powerupTexture;


    public RefillAmmo(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("armorPowerup"));

        this.powerupTexture = new TextureRegion(getTexture(),0,0, 100, 100);
        setBounds(0,0, 35 / Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Khartoosha.PPM);

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
    public void effect(Character c) {


        resetPupPosition();

        reset();
    }

    @Override
    public void update() {
        setPosition(pupBody.getPosition().x-getWidth()/5, pupBody.getPosition().y-getHeight()/3);
        if (isContacted)
        {
            effect(attachedChar);
            isContacted = false;
        }
    }

    @Override
    public void reset() {
        setSpawned(false);
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);

    }
}
