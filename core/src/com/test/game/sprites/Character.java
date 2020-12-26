package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
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
    public TextureRegion idle;
    private AnimationManager animationManager;
    public Animation runAnimation, jumpAnimation;

    public boolean isGoingDown;

    private final float DEFAULT_SPEED = 2;
    private float speedCap = DEFAULT_SPEED;
    private float speedScale = 0.4f;

    private float jumpScale = 4;
    /*
    @param x starting x-coordinate on pack
    @param y starting y-coordinate on pack
    @param width width of each frame
    @param height height of each frame
    @param i index of character to choose
    */
    private void loadCharacter(int i, int width,int height)
    {

        this.idle = new TextureRegion(getTexture(),0,(i-1)*height ,width,height);
        setBounds(0,0,width/Khartoosha.PPM, height/Khartoosha.PPM);
        setRegion(idle);
    }

    public Character(World world, PlayScreen screen, int charNum)
    {
        super(screen.getAtlas().findRegion("mandoSprite")); //for some reason it doesnt make a difference which string is passed

        this.world = world;
        defineCharacterPhysics();

        loadCharacter(charNum,95,130); //select character based on menu selection

        animationManager = new AnimationManager(true,getTexture(),this);
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
        //setRegion(animationManager.getFrame(delta));

    }

    public Vector2 getBodyPosition(){return this.physicsBody.getPosition();}

    public void setBodyPosition(Vector2 position)
    {
        this.physicsBody.setTransform(position.x / Khartoosha.PPM, position.y / Khartoosha.PPM , this.physicsBody.getAngle());
    }
    
    public void jump()
    {
        this.physicsBody.applyLinearImpulse(new Vector2(0, jumpScale), this.physicsBody.getWorldCenter(), true);
        update(Gdx.graphics.getDeltaTime());
    }


    public void moveRight()
    {
        if (this.physicsBody.getLinearVelocity().x <= speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(speedScale, 0), this.physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
        }
    }

    public void moveLeft()
    {
        if (this.physicsBody.getLinearVelocity().x >= -speedCap)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(-speedScale, 0), this.physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
        }

    }

    public void moveDown()
    {
        this.physicsBody.setAwake(true);
        isGoingDown = true;
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
