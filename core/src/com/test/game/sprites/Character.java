package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
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

    private TextureRegion texturereg;


    public Character(World world, PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("bruceSprite"));
        this.world = world;
        defineCharacterPhysics();

        texturereg = new TextureRegion(getTexture(),0,0,200,200);
        setBounds(0,0,200/Khartoosha.PPM,200/Khartoosha.PPM);
        setRegion(texturereg);
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
        setPosition(physicsBody.getPosition().x-getWidth()/2, physicsBody.getPosition().y-getHeight()/2);
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
