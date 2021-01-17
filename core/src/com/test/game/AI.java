package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.test.game.sprites.Character;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class AI {


    private final int IDLE_STATE = 0;
    private final int ATTACK_STATE = 1;
    private final int CHASE_STATE = 2;

    private int currentState = 0;


    // controls how fast the AI adapts to new changes
    private final float SWITCH_INTERVAL;
    private float switch_timer = 0;

    private boolean atPeace = true;
    private final float GAME_START_PEACE = 1f;
    private float gameStartTimer;

    private Character character, enemy;

    public AI(Character character, float SWITCH_INTERVAL)
    {
        this.character = character;
        this.SWITCH_INTERVAL = SWITCH_INTERVAL;
        this.enemy = character.getEnemy();
    }

    public void update(float delta)
    {
        if (atPeace)
        {
            gameStartTimer += delta;
            if (gameStartTimer > GAME_START_PEACE)
                atPeace = false;
        }
        else
        {

            switch_timer += delta;

            //change state when a certain time threshold passed
            if (switch_timer > SWITCH_INTERVAL)
            {
                //reset timer
                switch_timer = 0;
                //update state
                currentState = stateManager();


            }


            switch (currentState){
                case IDLE_STATE: break;
                case ATTACK_STATE:
                    attack(); // ATTACK
                    break;
                case CHASE_STATE:
                    chase();
                    break;

            }
        }

    }

    private int stateManager()
    {
        // character in shooting range
        // TODO: add weapon range consideration

        if (inShootingRange())
            return ATTACK_STATE;



        //the following will probably be changed

        // if the character is away a certain distance and not in shooting range chase him
        float distanceX = abs(character.getBodyPosition().x - enemy.getBodyPosition().x);
        float distanceY = abs(character.getBodyPosition().y - enemy.getBodyPosition().y);

        Random rand = new Random();
        // changes the distance that triggers the chase state
        float y_distance_epsilon = rand.nextInt(100) / Khartoosha.PPM;
        float x_distance_epsilon = rand.nextInt(200) / Khartoosha.PPM;




        if (distanceX > x_distance_epsilon || distanceY > y_distance_epsilon )
            return CHASE_STATE;


        return IDLE_STATE;
    }

    private void attack()
    {
        character.currentWeapon.shoot();
    }
    private void chase()
    {
        float y_distance_epsilon = 50 / Khartoosha.PPM;
        float x_distance_epsilon = 400 / Khartoosha.PPM;

        // enemy in higher platform
        if (enemy.getBodyPosition().y - character.getBodyPosition().y > y_distance_epsilon)
        {
            character.jump();
        }
        // enemy in lower platform
        else if (character.getBodyPosition().y - enemy.getBodyPosition().y > y_distance_epsilon
                && character.getBodyPosition().y > 300 / Khartoosha.PPM )//prevent falling down from last platform
        {
            character.moveDown();
        }

        //enemy far right
        if (enemy.getBodyPosition().x - character.getBodyPosition().x > x_distance_epsilon)
        {
            character.moveRight();
        }
        //enemy far left
        else if (character.getBodyPosition().x - enemy.getBodyPosition().x > x_distance_epsilon)
        {
            character.moveLeft();
        }

    }

    private boolean inShootingRange()
    {
        Vector2 enemyLocation = new Vector2(enemy.physicsBody.getWorldCenter().x ,enemy.physicsBody.getWorldCenter().y);
        Vector2 weaponLocation = new Vector2(character.getBodyPosition().x, character.getBodyPosition().y + 0.3f);

        // if enemy body infront of the weapon
        if (weaponLocation.y <= (enemyLocation.y + (enemy.SHAPE_HEIGHT / (2 * Khartoosha.PPM)) + 0.5) &&
                (weaponLocation.y >= (enemyLocation.y - (enemy.SHAPE_HEIGHT / (2 * Khartoosha.PPM)) ) ))
        {
            //make sure character is facing opponent
            if (enemyLocation.x > character.getBodyPosition().x && !character.currentWeapon.faceRight)
                character.moveRight();
            if (enemyLocation.x < character.getBodyPosition().x && character.currentWeapon.faceRight)
                character.moveLeft();
            return true;
        }



        return  false;
    }
}
