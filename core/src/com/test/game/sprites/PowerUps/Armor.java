package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;

/**
 * Reduce hit force of bullets fired at character for a certain amount of time by a certain factor
 */
public class Armor extends PowerUp {


    public static final float ARMOR_VALUE = 0.5f; // the value that scales the hit force

    private TextureRegion powerupTexture;

    public Armor(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("armorPowerup"), 9985, 10000, 10);
        this.powerupTexture = new TextureRegion(getTexture(),2*435,0, 435, 418);
        setBounds(0,0, 35 /Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);
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
    public void reset() {
        active_time = 0;
        setSpawned(false);
        setActive(false);
        if (attachedChar != null)
            attachedChar.isArmored = false;
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);

    }
}
