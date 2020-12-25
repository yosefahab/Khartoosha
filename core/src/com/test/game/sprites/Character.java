package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;


public class Character extends Sprite
{

    // Physics world
    public World world;
    public Body physicsBody;//physicsBody2;
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
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Khartoosha.PPM);
        fixtureDefinition.shape = shape;
        physicsBody.createFixture(fixtureDefinition).setUserData(this);


//        BodyDef body2Definition = new BodyDef();
//        body2Definition.position.set(500/ Khartoosha.PPM, 200/ Khartoosha.PPM);
//        body2Definition.type = BodyDef.BodyType.DynamicBody;
//        physicsBody2 = world.createBody(body2Definition);
//        FixtureDef fixtureDefinition2 = new FixtureDef();
//        CircleShape shape2 = new CircleShape();
//        shape2.setRadius(25/ Khartoosha.PPM);
//        fixtureDefinition2.shape = shape2;
//        physicsBody2.createFixture(fixtureDefinition2).setUserData(this);



    }

    public void update(float delta){
        //update position of texture
        setPosition(physicsBody.getPosition().x-getWidth()/5, physicsBody.getPosition().y-getHeight()/5);
        if (physicsBody.getPosition().y<-1000/Khartoosha.PPM) //if body falls, reset position
                physicsBody.setTransform(new Vector2(200 / Khartoosha.PPM, 2000 / Khartoosha.PPM ),physicsBody.getAngle());

        setRegion(animationManager.getFrame(delta));

    }

    public Vector2 getBodyPosition(Body physicsBody){return physicsBody.getPosition();}

    public void setBodyPosition(Body physicsBody,Vector2 position)
    {
        physicsBody.setTransform(position.x / Khartoosha.PPM, position.y / Khartoosha.PPM , physicsBody.getAngle());
    }
    
    public void jump(Body physicsBody)
    {
        physicsBody.applyLinearImpulse(new Vector2(0, jumpScale), this.physicsBody.getWorldCenter(), true);
        update(Gdx.graphics.getDeltaTime());
    }


    public void moveRight(Body physicsBody)
    {
        if (physicsBody.getLinearVelocity().x <= speedCap)
        {
            physicsBody.applyLinearImpulse(new Vector2(speedScale, 0), physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
        }
    }

    public void moveLeft(Body physicsBody)
    {
        if (physicsBody.getLinearVelocity().x >= -speedCap)
        {
            physicsBody.applyLinearImpulse(new Vector2(-speedScale, 0), physicsBody.getWorldCenter(), true);
            update(Gdx.graphics.getDeltaTime());
        }

    }

    public void moveDown(Body physicsBody)
    {
        physicsBody.setAwake(true);
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
