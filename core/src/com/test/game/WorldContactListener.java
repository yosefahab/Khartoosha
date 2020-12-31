package com.test.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.sprites.Bullets;
import com.test.game.sprites.Character;
import com.test.game.sprites.PowerUps.Armor;
import com.test.game.sprites.PowerUps.PowerUp;
import com.test.game.sprites.PowerUps.SpeedBoost;

public class WorldContactListener implements com.badlogic.gdx.physics.box2d.ContactListener
{


    @Override
    public void beginContact(Contact contact)
    {
        Object o1 = contact.getFixtureA().getUserData();
        Object o2 = contact.getFixtureB().getUserData();

        if (o2 instanceof Character && o1 instanceof Body)
            beginContactCHARACTERxPlatform(o1, o2);


        if ((o1 instanceof Character && o2 instanceof PowerUp))
        {
            pupCollision((Character) o1, (PowerUp) o2);
        }
        else if ((o2 instanceof Character && o1 instanceof SpeedBoost))
        {
            pupCollision((Character) o2, (PowerUp) o1);
        }


        if (o1 instanceof Bullets && o2 instanceof Character)
        {
            Bullets bullet = (Bullets) o1;
            bullet.isContacted = true;
            Character character = (Character) o2;
            if (!character.isArmored)
                character.physicsBody.applyForce(new Vector2(bullet.force,0), character.physicsBody.getWorldCenter(), true);

        }
        else if (o2 instanceof Bullets && o1 instanceof Character)
        {
            Bullets bullet = (Bullets) o2;
            bullet.isContacted = true;

            Character character = (Character) o1;

            if (!character.isArmored)
                character.physicsBody.applyForce(new Vector2(bullet.force,0), character.physicsBody.getWorldCenter(), true);


        }

    }

    @Override
    public void endContact(Contact contact)
    {

        Object o1 = contact.getFixtureA().getUserData();
        Object o2 = contact.getFixtureB().getUserData();

        if (o2 instanceof Character && o1 instanceof Body)
        {
            Character c = (Character) o2;
            c.isGoingDown = false;
        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
        Object o1 = contact.getFixtureA().getUserData();
        Object o2 = contact.getFixtureB().getUserData();

        if (o2 instanceof Character && o1 instanceof Body)
            preSolveCHARACTERxPlatform(o1, o2, contact);


        if (o1 instanceof Character && o2 instanceof Character)
            contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }


    private void pupCollision(Character character, PowerUp p)
    {
        if (p instanceof SpeedBoost)
        {
            SpeedBoost pup = (SpeedBoost) p;
            if (pup.isSpawned())
            {
                pup.setContacted(true);
                pup.attachedChar = character;
                //System.out.println("Pup char contact 1");
            }
        }else if (p instanceof Armor)
        {
            Armor pup = (Armor) p;
            if (pup.isSpawned())
            {
                pup.setContacted(true);
                pup.attachedChar = character;

            }
        }

    }


    private void beginContactCHARACTERxPlatform (Object o1, Object o2)
    {
        // Reset ALLOWED_JUMPS on contact with ground
        Character c = (Character) o2;
        ((Character) o2).ALLOWED_JUMPS = 2;
    }


    private void preSolveCHARACTERxPlatform (Object o1, Object o2, Contact contact)
    {

        Character c = (Character) o2;
        // Jumping or going down --> No contact
        if (c.physicsBody.getLinearVelocity().y > 0 || c.isGoingDown)
        {
            contact.setEnabled(false);
        }
        // Standing --> Contact
        else
        {
            contact.setEnabled(true);
        }

    }

}
