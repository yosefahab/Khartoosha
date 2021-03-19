package com.test.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Weapons.Bullet;
import com.test.game.screens.play_screen.Character;
import com.test.game.screens.play_screen.Powerups.Armor;
import com.test.game.screens.play_screen.Powerups.PowerUp;

public class WorldContactListener implements com.badlogic.gdx.physics.box2d.ContactListener
{


    @Override
    public void beginContact(Contact contact)
    {
        Object o1 = contact.getFixtureA().getUserData();
        Object o2 = contact.getFixtureB().getUserData();

        Object[] o;
        Object platform = new Object();
        if (o1 instanceof  Object[])
        {
            o = (Object[])o1;
            o1 = o[0];
            platform = o[1];
        }

        if (o1 instanceof Body && o2 instanceof Character )
            beginContactCHARACTERxPlatform(platform, o2);


        if ((o1 instanceof Character && o2 instanceof PowerUp))
            pupCollision((Character) o1, (PowerUp) o2);
        else if ((o2 instanceof Character && o1 instanceof PowerUp))
            pupCollision((Character) o2, (PowerUp) o1);



        if (o1 instanceof Bullet && o2 instanceof Character)
        {
            Bullet bullet = (Bullet) o1;
            bullet.isContacted = true;
            Character character = (Character) o2;

            Vector2 hitForce =  bullet.force;

            character.takeDamage();

            if (character.isArmored)
            {
                hitForce.x *= Armor.ARMOR_VALUE;
                hitForce.y *= Armor.ARMOR_VALUE;
            }

            character.physicsBody.applyForce(hitForce, character.physicsBody.getWorldCenter(), true);
            character.startHitTimer();
        }
        else if (o2 instanceof Bullet && o1 instanceof Character)
        {
            Bullet bullet = (Bullet) o2;
            Character character = (Character) o1;
            bullet.isContacted = true;
            Vector2 hitForce =  bullet.force;

            character.takeDamage();
            if (character.isArmored)
            {
                hitForce.x *= Armor.ARMOR_VALUE;
                hitForce.y *= Armor.ARMOR_VALUE;
            }

            character.physicsBody.applyForce(hitForce, character.physicsBody.getWorldCenter(), true);
            character.startHitTimer();

        }

    }

    @Override
    public void endContact(Contact contact)
    {

        Object o1 = contact.getFixtureA().getUserData();
        Object o2 = contact.getFixtureB().getUserData();

        Object[] o;
        if (o1 instanceof  Object[])
        {
            o = (Object[])o1;
            o1 = o[0];
        }
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

        Object[] o;
        if (o1 instanceof  Object[])
        {
            o = (Object[])o1;
            o1 = o[0];
        }
        if (o2 instanceof Character && o1 instanceof Body)
            preSolveCHARACTERxPlatform(o2, contact);


        if (o1 instanceof Character && o2 instanceof Character)
            contact.setEnabled(false);


        // Powerups skips platforms
        if ((o1 instanceof Body && o2 instanceof PowerUp)) {
            if (((PowerUp) o2).platforms_To_Skip > 0)
            {
                ((PowerUp) o2).platforms_To_Skip--;
                contact.setEnabled(false);
            }

        }
        if ((o2 instanceof Body && o1 instanceof PowerUp)) {
            if (((PowerUp) o1).platforms_To_Skip > 0)
            {
                ((PowerUp) o1).platforms_To_Skip--;
                contact.setEnabled(false);
            }
        }

        if (o1 instanceof PowerUp && o2 instanceof Bullet)
        {
            contact.setEnabled(false);
        }


        // Disable  PUP collision before spawning
        if ((o1 instanceof Character && o2 instanceof PowerUp)) {
            if (!((PowerUp) o2).isSpawned())
                contact.setEnabled(false);
        }
        if ((o2 instanceof Character && o1 instanceof PowerUp)) {
            if (!((PowerUp) o1).isSpawned())
                contact.setEnabled(false);
        }

        //Disable  Bullet collision with platforms (fixed bullet stuck in platform)
        if ((o1 instanceof Bullet && o2 instanceof Body)) {
            contact.setEnabled(false);
        }
        if ((o2 instanceof Bullet && o1 instanceof Body)) {
            contact.setEnabled(false);
        }



    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }


    private void pupCollision(Character character, PowerUp p)
    {
        if (p.isSpawned())
        {
            p.setContacted(true);
            p.attachedChar = character;
            SoundsManager.powerUp();
            //System.out.println("Pup char contact 1");
        }

    }


    private void beginContactCHARACTERxPlatform (Object platform, Object o2)
    {
        // Reset ALLOWED_JUMPS on contact with ground
        MapObject mapObject = (MapObject) platform;
        Character character = (Character) o2;

        character.ALLOWED_JUMPS = 2;
        if (character.isAI)
            character.getAi().canDrop = (boolean) mapObject.getProperties().get("droppable");
    }


    private void preSolveCHARACTERxPlatform (Object o2, Contact contact)
    {

        // Jumping or going down --> No contact
        if (((Character) o2).physicsBody.getLinearVelocity().y > 0 || ((Character) o2).isGoingDown)
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
