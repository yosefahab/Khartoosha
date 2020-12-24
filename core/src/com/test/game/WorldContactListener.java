package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.sprites.Character;

public class WorldContactListener implements com.badlogic.gdx.physics.box2d.ContactListener
{



    @Override
    public void beginContact(Contact contact)
    {

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
                System.out.println(contact.isEnabled());
                System.out.println("y");
            }
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
