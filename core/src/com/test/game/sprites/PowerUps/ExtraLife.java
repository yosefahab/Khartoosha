package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;

import java.security.Key;

public class ExtraLife extends PowerUp {

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9975, maxRate = 10000;

    private TextureRegion powerupTexture;

    public ExtraLife(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("armorPowerup"));

        this.powerupTexture = new TextureRegion(getTexture(),0,0, 435, 418);
        //setOrigin(1.0f* getTexture().getWidth() / 2,1.0f * getTexture().getHeight() / 2);
        setBounds(0,0, 35 / Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);
        //setOrigin(1.0f* getTexture().getWidth() / 2,1.0f * getTexture().getHeight() / 2);


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
    public void effect(Character c) {
        c.current_lives++;

        resetPupPosition();

        reset();
    }

    @Override
    public void update() {

        if (pupBody.getPosition().y < - 2)
            resetPupPosition();
        setPosition(pupBody.getPosition().x-getWidth()/5, pupBody.getPosition().y-getHeight()/3);

        if (isContacted)
        {
            effect(attachedChar);
            isContacted = false;
        }

        ////////// DEBUG
        if (Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT))
            setPosition(getX() - 0.1f, pupBody.getPosition().y-getHeight()/3);

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))
            setPosition(getX() + 0.1f, pupBody.getPosition().y-getHeight()/3);

        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT))
            setPosition(pupBody.getPosition().x, pupBody.getPosition().y-getHeight()/3);
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            System.out.println("Physics Body : " + pupBody.getWorldCenter() + " " + pupBody.getPosition().y +
                    "\n Texture : " + getX() + " " + getY());
    }

    @Override
    public void reset() {
        setSpawned(false);
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);

    }

}
