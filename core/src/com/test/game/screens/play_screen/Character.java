package com.test.game.screens.play_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import com.test.game.Weapons.Bomb;
import com.test.game.Weapons.Weapon;
import com.test.game.SoundsManager;

import java.util.Random;


public class Character extends Sprite
{
    // Physics world
    private final World world;
    public Body physicsBody;

    public final int SHAPE_WIDTH = 15;
    public final int SHAPE_HEIGHT = 40;

    protected TextureRegion idle,jumping;
    private final AnimationManager animationManager;
    protected Animation<?> runAnimation;

    public boolean isGoingDown;

    private final float DEFAULT_SPEED = 2;
    private float speedCap = DEFAULT_SPEED;
    private final float speedScale = 0.4f;
    public int ALLOWED_JUMPS = 2;

    public static final int  MAX_LIVES = 5;
    public static int current_lives;
    // indicator to be used to upgrade weapon for the opponent
    public boolean lostLife = false;

    // character input keys will be determined based on the value of this variable
    public static int NUMBER_OF_CHARACTERS;

    // Array of input key codes contains either WASD or arrows
    private final int[] CHARACTER_CONTROLS;

    public boolean isArmored = false;

    private final int CHARACTER_ID;

    // attaching weapon to character
    public Weapon weapon;
    public float hitTimer = 0;
    private boolean isTimerStarted = false;
    private final Vector2 spawnLocation;

    private Character enemy;
    public boolean isAI;
    private AI ai;
    private Bomb bomb;

    private int current_char=0;
    public boolean dead=false;
    /*
    @param x starting x-coordinate on pack
    @param y starting y-coordinate on pack
    @param width width of each frame
    @param height height of each frame
    @param i index of character to choose
    */
    private void loadCharacter(int TextureNumber)
    {
        current_char=TextureNumber;

        this.idle = new TextureRegion(getTexture(),0,(TextureNumber-1)* 637, 513, 637);
        this.jumping =  new TextureRegion(getTexture(),(7 * 513),(TextureNumber-1)* 637, 513, 637);
        setBounds(physicsBody.getPosition().x,physicsBody.getPosition().y, 120 /Khartoosha.PPM, 100 /Khartoosha.PPM);
        setRegion(idle);
    }
    public int current_char()
    {
        return current_char;
    }

    public Character(World world, PlayScreen screen, int TextureNumber, boolean player1, boolean isAI, Vector2 spawnLocation)
    {
        super(screen.getAtlas().findRegion("johnnySprite")); //for some reason it doesnt make a difference which string is passed
        this.world = world;
        this.isAI = isAI;
        this.bomb = new Bomb(world);
        this.spawnLocation = spawnLocation;


        NUMBER_OF_CHARACTERS++;
        CHARACTER_CONTROLS = new int[6];
        CHARACTER_ID = NUMBER_OF_CHARACTERS;
        defineCharacterPhysics();

        if (CHARACTER_ID == 1)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.W;
            CHARACTER_CONTROLS[1] = Input.Keys.A;
            CHARACTER_CONTROLS[2] = Input.Keys.S;
            CHARACTER_CONTROLS[3] = Input.Keys.D;
            CHARACTER_CONTROLS[4] = Input.Keys.CONTROL_LEFT;
            CHARACTER_CONTROLS[5] = Input.Keys.Z;
        }
        else if (CHARACTER_ID == 2)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.UP;
            CHARACTER_CONTROLS[1] = Input.Keys.LEFT;
            CHARACTER_CONTROLS[2] = Input.Keys.DOWN;
            CHARACTER_CONTROLS[3] = Input.Keys.RIGHT;
            CHARACTER_CONTROLS[4] = Input.Keys.SHIFT_RIGHT;
            CHARACTER_CONTROLS[5] = Input.Keys.PERIOD;
        }

        loadCharacter(TextureNumber); //select character based on menu selection
        animationManager = new AnimationManager(player1,getTexture(),this);
        runAnimation = animationManager.runAnimation(TextureNumber);

        current_lives = MAX_LIVES;

        weapon = new Weapon(world,this);
    }

    private void defineCharacterPhysics()
    {
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(spawnLocation);

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        physicsBody = world.createBody(bodyDefinition);

        FixtureDef fixtureDefinition = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SHAPE_WIDTH / Khartoosha.PPM, SHAPE_HEIGHT / Khartoosha.PPM);
        fixtureDefinition.shape = shape;
        fixtureDefinition.friction = 3;
        physicsBody.createFixture(fixtureDefinition).setUserData(this);

    }

    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();

        handleInput();
        weapon.update(delta);
        //Control texture position here
        setPosition(physicsBody.getPosition().x - getWidth()/2 , physicsBody.getPosition().y-getHeight()/2);

        //if body falls, reset position and decrease lives
        // Hit timer (logic explained in start Hit timer function)
        int MAX_HIT_TIMER = 4;
        dead=false;
        if (physicsBody.getPosition().y < -800/Khartoosha.PPM)
        {
            Random rand = new Random();
            float spawnX = rand.nextInt((int)Khartoosha.Gwidth - 100) / Khartoosha.PPM + (150 / Khartoosha.PPM);
            physicsBody.setLinearVelocity(new Vector2(0,0));
            physicsBody.setTransform(new Vector2(spawnX, 1100 / Khartoosha.PPM ),physicsBody.getAngle());
            current_lives--;
            takeDamage();
            dead=true;


            //don't upgrade opponent on self kill
            if (isTimerStarted && hitTimer < MAX_HIT_TIMER)
            {
                lostLife = true;
                isTimerStarted = false;
                hitTimer = 0;
            }

            // degrade weapon on death
            if (weapon.type > 0)
                weapon.type--;
            weapon.switchWeapon();

            weapon.update(delta);
        }


        if (current_lives == 0)
        {
            System.out.println("Player " + CHARACTER_ID + " lost");
            PlayScreen.winner = (CHARACTER_ID-1)%2;
            current_lives = MAX_LIVES;
            PlayScreen.gameOver = true;
            SoundsManager.gameOver();
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

        if (enemy.lostLife)
        {
            if (weapon.type < Weapon.MAX_TYPE)
            {
                weapon.type++;
                weapon.switchWeapon();
            }
            else weapon.refillAmmo();
            enemy.lostLife = false;
        }


        if (isFlipX())
        {
            weapon.setFlip(true, false);
            weapon.faceRight = false;
        }
        else if (!isFlipX())
        {
            weapon.setFlip(false, false);
            weapon.faceRight = true;
        }

        if (isAI)
            ai.update(delta);


        bomb.update();


    }

    public Vector2 getBodyPosition(){return this.physicsBody.getPosition();}



    public void takeDamage(){
        if (CHARACTER_ID==1)
            SoundsManager.player1Grunt();
        else
            SoundsManager.player2Grunt();
    }
    public void jump()
    {
        if (ALLOWED_JUMPS != 0)
        {
            ALLOWED_JUMPS--;
            this.physicsBody.setLinearVelocity(new Vector2(0, 0));
            float jumpScale = 4;
            this.physicsBody.applyLinearImpulse(new Vector2(0, jumpScale), this.physicsBody.getWorldCenter(), true);
        }
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

    public void moveDown()
    {
        this.physicsBody.setAwake(true);
        isGoingDown = true;
    }

    private void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(CHARACTER_CONTROLS[0]))
        {
            jump();
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
            weapon.shoot();
        }

        /*
        if (Gdx.input.isKeyJustPressed(CHARACTER_CONTROLS[5]))
        {
            bomb.KA(this);
        }
        */
    }

    public void dispose()
    {
        NUMBER_OF_CHARACTERS--;
        getTexture().dispose();
        jumping.getTexture().dispose();
        idle.getTexture().dispose();
    }
    // Speed boost pup
    public void setSpeedCap(float speedCap)
    {
        // prevent stacking of speed boosts more than twice
        if (this.speedCap <  DEFAULT_SPEED * 2)
            this.speedCap *= speedCap;
    }
    public float getSpeedCap(){ return this.speedCap; }

    public void resetSpeedCap() {
        speedCap = DEFAULT_SPEED;
    }

    public void setEnemy(Character enemy)
    {
        this.enemy = enemy;
        // if wondering about placement here check com+ent above the function
        if (isAI)
            initAI();
    }
    public Character getEnemy() {return this.enemy;}

    public int getMAX_LIVES() {return MAX_LIVES;}

    /**
     * When a player hits an opponent a hit timer is started
     * if the opponent dies before this timer reaches a
     * certain threshold it's counted as a kill towards the player
     * and his weapon is upgraded, this prevents self kills
     * from upgrading the weapon's opponent
     */
    public void startHitTimer() {
        hitTimer = 0;
        isTimerStarted = true;
    }

    public void render() {
        draw(Khartoosha.batch);
        weapon.draw(Khartoosha.batch);
        weapon.render(Khartoosha.batch);
    }

    // Had to be done separately not in the constructor because the AI needs the character
    // to have an enemy assigned and since the set enemy is done separately this had to be
    // separated too
    private void initAI()
    {
        ai = new AI(this);
    }

    public int getCHARACTER_ID() {
        return CHARACTER_ID;
    }

    public AI getAi(){return ai;}


}
