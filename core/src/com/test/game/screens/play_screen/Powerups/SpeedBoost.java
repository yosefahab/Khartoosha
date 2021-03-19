package com.test.game.screens.play_screen.Powerups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.PlayScreen;
import com.test.game.screens.play_screen.Character;

/**
 * Increases Character's speed by a certain factor
 */
public class SpeedBoost  extends PowerUp
{


    // the scale which the speed of character is multiplied by
    private final float speedBoost = 2.0f;


    private TextureRegion powerupTexture;




    public SpeedBoost(World world, PlayScreen screen)
    {

        super(world,screen.GetAtlas().findRegion("armorPowerup"), 9980, 10000, 10);

        this.powerupTexture = new TextureRegion(getTexture(),435,0, 435, 418);
        setBounds(0,0, 35 /Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);


    }


    @Override
    public void effect(Character player) {
        //activate
        setActive(true);
        // reset pup Position
        resetPupPosition();

        // increase player speed
        player.setSpeedCap(speedBoost);

    }


    @Override
    public void reset() {
        active_time = 0;
        setSpawned(false);
        setActive(false);
        if (attachedChar != null)
            attachedChar.resetSpeedCap();
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);
    }








}
