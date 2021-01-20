package com.test.game.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.sprites.Character;

public class Bomb extends Sprite {

    public World world;
    public Body bombBody;
    private float timer;
    private float MAX_TIME = 2;
    private boolean isThrown;
    private boolean isExploded;
    private int isChar1;
    Bullet[] bullets = new Bullet[2];

    public Bomb(World world) {
        super();
        this.world = world;
        initBomb();
    }

    private void initBomb() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(Khartoosha.Gwidth / Khartoosha.PPM + (200 / Khartoosha.PPM),
                Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM));

        bdef.type = BodyDef.BodyType.StaticBody;
        bombBody = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        bombBody.createFixture(fdef).setUserData(this);
    }

    public void KA(Character c)
    {
        if (c.getCHARACTER_ID() == 1)
            isChar1 = 1;
        else
            isChar1 = -1;
        bombBody.setType(BodyDef.BodyType.StaticBody);
        bombBody.setTransform(c.getBodyPosition().x , c.getBodyPosition().y, 0);
        bombBody.setType(BodyDef.BodyType.DynamicBody);
        bombBody.applyForceToCenter(new Vector2(100 * isChar1,2), true);
        isThrown = true;

    }

    public void BOOM()
    {
        Vector2 pos = new Vector2(bombBody.getWorldCenter());

        bullets[0] = new Bullet(world,new Vector2( pos.x+0.4f, pos.y + 0.1f), 0.125f, 1000);
        bullets[1] = new Bullet(world,new Vector2( pos.x-0.4f, pos.y + 0.1f), -0.125f, -1000);
        bombBody.setTransform(Khartoosha.Gwidth / Khartoosha.PPM + (200 / Khartoosha.PPM),
            Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM), 0);
        bombBody.setType(BodyDef.BodyType.StaticBody);
        isExploded = true;
    }

    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();

        if (isThrown)
            timer += delta;
        if (timer > MAX_TIME)
        {
            BOOM();
            isThrown = false;
            isExploded = true;
            timer = 0;
        }

        if (isExploded)
        {
            int cnt = 0;
            for(Bullet b:bullets)
            {

                if (b != null)
                {
                    cnt++;
                    b.update(delta);
                    if (b.remove)
                        b.remove();

                }
            }
            if (cnt == 0)
                isExploded = false;
        }

    }
}
