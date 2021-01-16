package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import com.test.game.Weapons.Weapon;
import com.test.game.screens.PlayScreen;


import java.util.Random;


public class Character extends Sprite
{

    // Physics world
    public World world;
    public Body physicsBody;

    public TextureRegion idle,jumping;
    private AnimationManager animationManager;
    public Animation<?> runAnimation;
    private PlayScreen screen;

    public boolean isGoingDown;

    private final float DEFAULT_SPEED = 2;
    public float speedCap = DEFAULT_SPEED;
    private float speedScale = 0.4f;
    private float jumpScale = 4;
    public int ALLOWED_JUMPS = 2;

    private final int  MAX_LIVES = 5;
    public int current_lives;
    // indicator to be used to upgrade weapon for the opponent
    public boolean lostLife = false;

    // character input keys will be determined based on the value of this variable
    private static int NUMBER_OF_CHARACTERS;

    // Array of input key codes contains either WASD or arrows
    public final int[] CHARACTER_CONTROLS;

    public boolean isArmored = false;

    private final int TextureNumber;

    private final int CHARACTER_ID;


    // attaching weapon to character
    public Weapon currentWeapon;
    // Hit timer (logic explained in start Hit timer function)
    private final int MAX_HIT_TIMER = 4;
    public float hitTimer = 0;
    private boolean isTimerStarted = false;

    private Character enemy;


    /*
    @param x starting x-coordinate on pack
    @param y starting y-coordinate on pack
    @param width width of each frame
    @param height height of each frame
    @param i index of character to choose
    */
    private void loadCharacter(int TextureNumber)
    {

        this.idle = new TextureRegion(getTexture(),0,(TextureNumber-1)* 471, 374, 471);
        this.jumping =  new TextureRegion(getTexture(),(4 * 374),(TextureNumber-1)* 471, 374, 471);

        setBounds(0,0, 120 /Khartoosha.PPM, 150 /Khartoosha.PPM);
        setRegion(idle);
    }

    public Character(World world, PlayScreen screen, int TextureNumber, boolean player1)
    {
        super(screen.getAtlas().findRegion("mandoSprite")); //for some reason it doesnt make a difference which string is passed
        this.world = world;
        this.screen = screen;
        this.TextureNumber = TextureNumber;


        NUMBER_OF_CHARACTERS++;
        CHARACTER_CONTROLS = new int[5];
        CHARACTER_ID = NUMBER_OF_CHARACTERS;
        defineCharacterPhysics();

        if (NUMBER_OF_CHARACTERS == 1)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.W;
            CHARACTER_CONTROLS[1] = Input.Keys.A;
            CHARACTER_CONTROLS[2] = Input.Keys.S;
            CHARACTER_CONTROLS[3] = Input.Keys.D;
            CHARACTER_CONTROLS[4] = Input.Keys.CONTROL_LEFT;
        }
        else if (NUMBER_OF_CHARACTERS == 2)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.UP;
            CHARACTER_CONTROLS[1] = Input.Keys.LEFT;
            CHARACTER_CONTROLS[2] = Input.Keys.DOWN;
            CHARACTER_CONTROLS[3] = Input.Keys.RIGHT;
            CHARACTER_CONTROLS[4] = Input.Keys.SPACE;
        }

        loadCharacter(TextureNumber); //select character based on menu selection
        animationManager = new AnimationManager(player1,getTexture(),this);
        runAnimation = animationManager.runAnimation(TextureNumber);
        animationManager.clearFrames();

        current_lives = MAX_LIVES;

        currentWeapon = new Weapon(world,screen,this);
    }

    public void defineCharacterPhysics()
    {
        BodyDef bodyDefinition = new BodyDef();
        if (CHARACTER_ID == 1)
        {
            bodyDefinition.position.set(200 / Khartoosha.PPM, 200 / Khartoosha.PPM);
        }
        else
        {
            bodyDefinition.position.set(650 / Khartoosha.PPM, 200/ Khartoosha.PPM);
        }

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        physicsBody = world.createBody(bodyDefinition);

        FixtureDef fixtureDefinition = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / Khartoosha.PPM, 40 / Khartoosha.PPM);
        fixtureDefinition.shape = shape;
        fixtureDefinition.friction = 3;
        physicsBody.createFixture(fixtureDefinition).setUserData(this);

    }

    public void update(float delta)
    {
        handleInput();
        currentWeapon.update(delta);
        setPosition((float) (physicsBody.getPosition().x- (2.5)*getWidth()/5), physicsBody.getPosition().y-getHeight()/3);

        //if body falls, reset position and decrease lives
        if (physicsBody.getPosition().y < -800/Khartoosha.PPM)
        {
            Random rand = new Random();
            float spawnX = rand.nextInt((int)Khartoosha.Gwidth - 100) / Khartoosha.PPM + (150 / Khartoosha.PPM);
            physicsBody.setLinearVelocity(new Vector2(0,0));
            physicsBody.setTransform(new Vector2(spawnX, 2000 / Khartoosha.PPM ),physicsBody.getAngle());
            current_lives--;


            //don't upgrade opponent on self kill
            if (isTimerStarted && hitTimer < MAX_HIT_TIMER)
            {
                lostLife = true;
                isTimerStarted = false;
                hitTimer = 0;
            }

            // degrade weapon on death
            if (currentWeapon.type > 0)
                currentWeapon.type--;
            currentWeapon.switchWeapon();

            currentWeapon.update(delta);
        }


        if (current_lives == 0)
        {
            System.out.println("Player " + TextureNumber + " lost");
            current_lives = MAX_LIVES;
            //TODO: reset game
        }

        if (current_lives > MAX_LIVES)
            current_lives = MAX_LIVES;

        setRegion(animationManager.getFrame(delta));


        if (isTimerStarted)
            hitTimer += Gdx.graphics.getDeltaTime();

        //if exceeded 10 seconds reset and close timer to prevent overflowing
        if (hitTimer > MAX_HIT_TIMER)
        {
            hitTimer = 0;
            isTimerStarted = false;
        }


        if (enemy != null && enemy.lostLife)
        {
            if (currentWeapon.type < 2)
            {
                currentWeapon.type++;
                currentWeapon.switchWeapon();
            }
            else currentWeapon.refillAmmo();
            enemy.lostLife = false;
        }


        if (isFlipX())
        {
            currentWeapon.setFlip(true, false);
            currentWeapon.faceRight = false;
        }
        else if (!isFlipX())
        {
            currentWeapon.setFlip(false, false);
            currentWeapon.faceRight = true;
        }


    }

    public Vector2 getBodyPosition(){return this.physicsBody.getPosition();}


    private void jump()
    {
        this.physicsBody.setLinearVelocity(new Vector2(0, 0));
        this.physicsBody.applyLinearImpulse(new Vector2(0, jumpScale), this.physicsBody.getWorldCenter(), true);
    }

    public void moveRight()
    {
        if (this.physicsBody.getLinearVelocity().x <= speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(speedScale, 0), this.physicsBody.getWorldCenter(), true);
        }
    }

    public void moveLeft()
    {
        if (this.physicsBody.getLinearVelocity().x >= -speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(-speedScale, 0), this.physicsBody.getWorldCenter(), true);
        }

    }

    private void moveDown()
    {
        this.physicsBody.setAwake(true);
        isGoingDown = true;
    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(CHARACTER_CONTROLS[0]) && ALLOWED_JUMPS != 0)
        {
            jump();
            ALLOWED_JUMPS--;
        }
        if (Gdx.input.isKeyPressed(CHARACTER_CONTROLS[1]))
        {
            moveLeft();
        }
        if (Gdx.input.isKeyJustPressed(CHARACTER_CONTROLS[2]))
        {
            moveDown();
        }
        if (Gdx.input.isKeyPressed(CHARACTER_CONTROLS[3]))
        {
            moveRight();
        }

        if (Gdx.input.isKeyPressed(CHARACTER_CONTROLS[4]))
        {
            currentWeapon.shoot();
        }

    }

    public void dispose()
    {

    }

    // Speed boost pup
    public void setSpeedCap(float speedCap)
    {
        this.speedCap *= speedCap;
    }

    public void resetSpeedCap() {
        speedCap = DEFAULT_SPEED;
    }

    public void setEnemy(Character enemy)
    {
        this.enemy = enemy;
    }




    /**
     * When a player hits an opponent a hit timer is started
     * if the opponent dies before this timer reaches a
     * certain threshold it's counted as a kill towards the player
     * and his weapon is upgraded, this prevents self kills
     * from upgrading the weapon's opponent
     */
    public void startHitTimer()
    {
        //System.out.println("Hit timer started");
        hitTimer = 0;
        isTimerStarted = true;
    }



    public void render()
    {
        draw(Khartoosha.batch);
        currentWeapon.draw(Khartoosha.batch);
        currentWeapon.render(Khartoosha.batch);

    }

}
