package com.test.game.sprites;

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

    Animation animation;

    private TextureRegion textureRegion;
    /*
    @param x starting x-coordinate on pack
    @param y starting y-coordinate on pack
    @param width width of each frame
    @param height height of each frame
    @param i index of character to choose
    */
    private void loadCharacter(int i, int width,int height){

        this.textureRegion = new TextureRegion(getTexture(),0,(i-1)*height ,width,height);
        setBounds(0,0,width/Khartoosha.PPM, height/Khartoosha.PPM);
        setRegion(textureRegion);
    }

    public Character(World world, PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("mandoSprite")); //for some reason it doesnt make a difference which string is passed
        this.world = world;
        defineCharacterPhysics();

        loadCharacter(1,95,130); //select character based on menu selection
        animation = new Animation(textureRegion,5,.5f);
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
        physicsBody.createFixture(fixtureDefinition);
    }

    public void update(float delta){
        //update position of texture
        setPosition(physicsBody.getPosition().x-getWidth()/5, physicsBody.getPosition().y-getHeight()/5);
        if (physicsBody.getPosition().y<-1000/Khartoosha.PPM)
                physicsBody.setTransform(new Vector2(physicsBody.getPosition().x, 2000 / Khartoosha.PPM ),physicsBody.getAngle());
        animation.update(delta);
    }

    public Vector2 getBodyPosition(){return physicsBody.getPosition();}

    public void setBodyPosition(Vector2 position){
        physicsBody.setTransform(position.x / Khartoosha.PPM, position.y / Khartoosha.PPM , physicsBody.getAngle());
    }
    
    public void jump()
    {
        this.physicsBody.applyLinearImpulse(new Vector2(0, 4F), this.physicsBody.getWorldCenter(), true);
    }


    public void moveRight()
    {
        if (this.physicsBody.getLinearVelocity().x <= 2)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(0.1F, 0), this.physicsBody.getWorldCenter(), true);
        }
    }

    public void moveLeft()
    {
        if (this.physicsBody.getLinearVelocity().x >= -2)
        {
            this.physicsBody.applyLinearImpulse(new Vector2(-0.1F, 0), this.physicsBody.getWorldCenter(), true);
        }

    }

    public void dispose()
    {

    }

}
