package com.test.game.screens.play_screen.Powerups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.Weapons.Weapon;
import com.test.game.screens.play_screen.PlayScreen;
import com.test.game.screens.play_screen.Character;


/**
 *   Upgrades Character's weapon
 *   if the character has the max weapon it's ammo is refilled instead
 */
public class UpgradeWeapon extends PowerUp {

    private TextureRegion powerupTexture;
    public UpgradeWeapon(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("weaponPowerup"), 9992, 10000, 0);

        this.powerupTexture = new TextureRegion(getTexture(),4*434,0, 434, 418);
        setBounds(0,0, 55 / Khartoosha.PPM, 45 /Khartoosha.PPM);
        setRegion(powerupTexture);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / Khartoosha.PPM,10 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);
    }



    @Override
    public void effect(Character c) {

        // if the character has the max weapon type refill ammo instead
        if (attachedChar.weapon.type == Weapon.MAX_TYPE)
            attachedChar.weapon.refillAmmo();
        else
        {
            attachedChar.weapon.type++;
            attachedChar.weapon.switchWeapon();

        }
        resetPupPosition();

        reset();
    }


}
