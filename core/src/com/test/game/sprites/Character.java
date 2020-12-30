package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;


public class Character extends Sprite
{

    // Physics world
    public World world;
    public Body physicsBody;
    public TextureRegion idle,jumping;
    private AnimationManager animationManager;
    public Animation runAnimation;

    public boolean isGoingDown;

    private final float DEFAULT_SPEED = 2;
    private float speedCap = DEFAULT_SPEED;
    private float speedScale = 0.4f;
    private float jumpScale = 4;
    public int ALLOWED_JUMPS = 2;

    // character input keys will be determined based on the value of this variable
    private static int NUMBER_OF_CHARACTERS;

    // Array of input key codes contains either WASD or arrows
    public final int[] CHARACTER_CONTROLS;


    /*
    @param x starting x-coordinate on pack
    @param y starting y-coordinate on pack
    @param width width of each frame
    @param height height of each frame
    @param i index of character to choose
    */
    private void loadCharacter(int charNum)
    {

        this.idle = new TextureRegion(getTexture(),0,(charNum-1)* 151, 120, 151);
        this.jumping =  new TextureRegion(getTexture(),(4 * 120),(charNum-1)* 151, 120, 151);

        setBounds(0,0, 120 /Khartoosha.PPM, 151 /Khartoosha.PPM);
        setRegion(idle);
    }

    public Character(World world, PlayScreen screen, int charNum, boolean player1)
    {
        super(screen.getAtlas().findRegion("mandoSprite")); //for some reason it doesnt make a difference which string is passed
        this.world = world;
        defineCharacterPhysics();
        NUMBER_OF_CHARACTERS++;
        CHARACTER_CONTROLS = new int[4];

        if (NUMBER_OF_CHARACTERS == 1)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.W;
            CHARACTER_CONTROLS[1] = Input.Keys.A;
            CHARACTER_CONTROLS[2] = Input.Keys.S;
            CHARACTER_CONTROLS[3] = Input.Keys.D;
        }
        else if (NUMBER_OF_CHARACTERS == 2)
        {
            CHARACTER_CONTROLS[0] = Input.Keys.UP;
            CHARACTER_CONTROLS[1] = Input.Keys.LEFT;
            CHARACTER_CONTROLS[2] = Input.Keys.DOWN;
            CHARACTER_CONTROLS[3] = Input.Keys.RIGHT;
        }

        loadCharacter(charNum); //select character based on menu selection
        animationManager = new AnimationManager(player1,getTexture(),this);
        runAnimation = animationManager.runAnimation(charNum);
        animationManager.clearFrames();
    }

    public void defineCharacterPhysics()
    {
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(200 / Khartoosha.PPM, 200 / Khartoosha.PPM);
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        physicsBody = world.createBody(bodyDefinition);

        FixtureDef fixtureDefinition = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / Khartoosha.PPM, 40 / Khartoosha.PPM);
        fixtureDefinition.shape = shape;
        physicsBody.createFixture(fixtureDefinition).setUserData(this);

    }

    public void update(float delta){
        // Update position of texture
        setPosition(physicsBody.getPosition().x-getWidth()/5, physicsBody.getPosition().y-getHeight()/5);
        if (physicsBody.getPosition().y<-1000/Khartoosha.PPM) //if body falls, reset position
                physicsBody.setTransform(new Vector2(200 / Khartoosha.PPM, 2000 / Khartoosha.PPM ),physicsBody.getAngle());

        // TODO: uncomment to unpause animation
        setRegion(animationManager.getFrame(delta));

    }

    public Vector2 getBodyPosition(){return this.physicsBody.getPosition();}



    
    private void jump()
    {
        this.physicsBody.setLinearVelocity(new Vector2(0, 0));
        this.physicsBody.applyLinearImpulse(new Vector2(0, jumpScale), this.physicsBody.getWorldCenter(), true);
        update(Gdx.graphics.getDeltaTime());
    }


    private void moveRight()
    {
        if (this.physicsBody.getLinearVelocity().x <= speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(speedScale, 0), this.physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
        }
    }

    private void moveLeft()
    {
        if (this.physicsBody.getLinearVelocity().x >= -speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(-speedScale, 0), this.physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
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
    }

    public void dispose()
    {

    }

    // Speed boost pup
    public void setSpeedCap(float speedCap)
    {
        this.speedCap = speedCap;
    }

    public void resetSpeedCap() {
        speedCap = DEFAULT_SPEED;
    }




}
