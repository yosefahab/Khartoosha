package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
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

        /*              PowerUPs collision          */
        // if 1 object is pup and other is character
        if ( (o1.getClass() == Character.class && o2 instanceof PowerUp) )
        {
            pupCollision((Character) o1, (PowerUp) o2);
        }else if ( (o2.getClass() == Character.class && o1.getClass() == SpeedBoost.class)  )
        {
            pupCollision((Character) o2, (PowerUp) o1);
        }



        /*              Bullets collision          */
        if (o1 instanceof Bullets && o2 instanceof Character)
        {
            //System.out.println("bullet character");
            Bullets bullet = (Bullets) o1;
            bullet.isContacted = true;

            Character character = (Character) o2;
            if (!character.isArmored)
                character.physicsBody.applyForce(new Vector2(bullet.force,0), character.physicsBody.getWorldCenter(), true);

        }
        else if (o2 instanceof Bullets && o1 instanceof Character) {
            //System.out.println("bullet character");

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
        if (o1.getClass() == Character.class)
        {
            Character c = (Character) o1;
            c.isGoingDown = false;
        }

        if (o2.getClass() == Character.class)
        {
            Character c = (Character) o2;
            c.isGoingDown = false;
        }



    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        Object o1 = f1.getUserData();
        Object o2 = f2.getUserData();

        if (o2.getClass() == Character.class)
        {
            Character c = (Character) o2;

            if (o1.getClass() == Body.class)
            {
                if (c.physicsBody.getLinearVelocity().y > 0 || c.isGoingDown)
                {
                    contact.setEnabled(false);
                }
                else
                {
                    contact.setEnabled(true);
                }
                //System.out.println(contact.isEnabled());
                //System.out.println("y");
            }
        }








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
}
