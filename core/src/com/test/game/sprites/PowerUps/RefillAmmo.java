package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;


/**
 * Refill current weapon's ammo
 */
public class RefillAmmo extends PowerUp {

    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9980, maxRate = 10000;

    private TextureRegion powerupTexture;


    public RefillAmmo(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("ammoPowerup"), 9990, 10000, 0);

        this.powerupTexture = new TextureRegion(getTexture(),3*434 ,0, 434, 418);
        setBounds(0,0, 55 / Khartoosha.PPM, 45 /Khartoosha.PPM);
        setRegion(powerupTexture);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);
    }



    @Override
    public void effect(Character c) {

        attachedChar.weapon.refillAmmo();
        resetPupPosition();

        reset();
    }


}
