package com.test.game.screens.play_screen;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.Powerups.PowerUp;

import java.util.Random;

import static java.lang.Math.abs;

public class AI {


    private final int IDLE_STATE = 0;
    private final int ATTACK_STATE = 1;
    private final int CHASE_STATE = 2;

    private final int POST_JUMP_RIGHT = 1;
    private final int POST_JUMP_LEFT = 2;

    private int currentState = 0;


    // controls how fast the AI adapts to new changes
    private final float SWITCH_INTERVAL;
    private float switch_timer = 0;

    private boolean atPeace = true;
    private final float GAME_START_PEACE = 1f;
    private float gameStartTimer;
    private Array<Rectangle> jumpPoints;
    private Array<Integer> jumpDirections;
    private boolean jumped = false;
    private Character character, enemy;
    private Object[] jumpPair;
    public boolean canDrop;
    public PowerUp[] PUPs;

    public AI(Character character, float SWITCH_INTERVAL)
    {
        this.character = character;
        this.SWITCH_INTERVAL = 0.5f;
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
                //System.out.println("Current State : " + currentState);


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


        //when getting shot move towards character
        if (character.hitTimer <= 1 && character.hitTimer > 0)
        {
            chase();
        }

    }

    private int stateManager()
    {
        // character in shooting range
        // TODO: add weapon range consideration
        jumped = false;
        if (inShootingRange())
            return ATTACK_STATE;

        //the following will probably be changed

        // if the character is away a certain distance and not in shooting range chase him
        float distanceX = abs(character.getBodyPosition().x - enemy.getBodyPosition().x);
        float distanceY = character.getBodyPosition().y - enemy.getBodyPosition().y;

        Random rand = new Random();
        // changes the distance that triggers the chase state
        float y_distance_epsilon = rand.nextInt(100) / Khartoosha.PPM;
        float x_distance_epsilon = rand.nextInt(200) / Khartoosha.PPM;




        if ( abs(distanceY) > y_distance_epsilon || distanceX > x_distance_epsilon )
            return CHASE_STATE;



        return IDLE_STATE;
    }


    private void attack()
    {
        character.weapon.shoot();
    }


    private void chase()
    {
        Random rand = new Random();
        float y_distance_epsilon = rand.nextInt(50)  / Khartoosha.PPM;
        float x_distance_epsilon = (rand.nextInt(200) + 200) / Khartoosha.PPM;


        // enemy in higher platform
        if (enemy.getBodyPosition().y - character.getBodyPosition().y > y_distance_epsilon)
        {
            if (!jumped)
                jumpPair =  find_closest_jump();
            seek_jump((Rectangle)jumpPair[0], (int)jumpPair[1]);
            return;
        }

        // enemy in lower platform
        else if (character.getBodyPosition().y - enemy.getBodyPosition().y > y_distance_epsilon )//prevent falling down from last platform
        {
            if (canDrop)
            {
                character.moveDown();
                canDrop = false;
            }
            else //if can't drop to his level apporach target
                x_distance_epsilon -= 200 / Khartoosha.PPM;

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
            if (enemyLocation.x > character.getBodyPosition().x && !character.weapon.faceRight)
                character.moveRight();
            if (enemyLocation.x < character.getBodyPosition().x && character.weapon.faceRight)
                character.moveLeft();
            return true;
        }



        return  false;
    }

    public void setJumpPoints(Array<Rectangle> jumpPoints) {
        this.jumpPoints = jumpPoints;
    }
    public void setJumpDirections(Array<Integer> jumpDirections) {
        this.jumpDirections = jumpDirections;
    }


    /**
     * The function takes the closest jump point and starts navigating towards it
     * when it arrives it jumps and activates a boolean to trigger movement in a certain direction
     * while in air
     *
     * @param point contains the closest jump point on the same platform
     * @param idx index of the point (to check the jump direction
     */
    private void seek_jump(Rectangle point, int idx)
    {
        Vector2 jump_position = new Vector2(point.getX() / Khartoosha.PPM, point.getY() / Khartoosha.PPM);
        Vector2 char_position = new Vector2(character.getBodyPosition());

        // if in air
        if (jumped)
        {
            if (jumpDirections.get(idx) == POST_JUMP_LEFT)
                character.moveLeft();
            else if (jumpDirections.get(idx) == POST_JUMP_RIGHT)
                character.moveRight();
            else
                character.jump();

            return;
        }

        // navigating to point
        // on jump
        if (abs(jump_position.x - char_position.x) < 0.01)
        {
            //System.out.println(idx);
            character.jump();
            jumped = true;

        }
        // jump point  to the right of char
        else if (jump_position.x > char_position.x)
            character.moveRight();
        // jump point to left
        else
            character.moveLeft();

    }

    /**
     *  Uses linear search to find the closest point on the same level as the character
     * @return Object pair, object[0] :point, object[1]: indx
     */
    private Object[] find_closest_jump()
    {
        Object[] rect_indx_pair = new Object[2];
        Rectangle closestPoint = jumpPoints.get(0);
        Vector2 char_position = new Vector2(character.getBodyPosition().x,
                character.getBodyPosition().y - (character.SHAPE_HEIGHT * 1.0f / Khartoosha.PPM) / 2);
        Vector2 point_position;

        int indx = 0, pointIndx = 0;
        for (Rectangle point:jumpPoints)
        {
            point_position = new Vector2(point.getX() / Khartoosha.PPM, point.getY() / Khartoosha.PPM);

            if (abs(point_position.y - char_position.y) < 0.25 &&
                    (abs(char_position.x - point_position.x) < abs(char_position.x - closestPoint.getX() / Khartoosha.PPM))  )
            {
                closestPoint = point;
                pointIndx = indx;
            }
            indx++;
        }

        rect_indx_pair[0] = closestPoint;
        rect_indx_pair[1] =  pointIndx;
        return rect_indx_pair;
    }

}
