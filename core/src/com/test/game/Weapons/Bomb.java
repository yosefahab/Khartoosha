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
import com.badlogic.gdx.utils.Null;
import com.test.game.Khartoosha;
import com.test.game.Utils;
import com.test.game.sprites.Character;

public class Bomb extends Sprite
{

    public World world;
    public Body bombBody;
    private float timer;
    private float MAX_TIME = 2;
    private boolean isThrown;
    private boolean isExploded;
    private int isChar1;
    Bullet[] bullets = new Bullet[5];


    private static final float speed = 0.125f;
    // 0 --> Horizontal, 1 --> Vertical, 2 --> Diagonal
    private static final Vector2[] VELOCITY_VECTORS =
            {new Vector2(speed, 0), new Vector2(0, speed), new Vector2(speed, speed)};


    private static final int force = 1000;
    // 0 --> Horizontal, 1 --> Vertical, 2 --> Diagonal
    private static final Vector2[] FORCE_VECTORS =
            {new Vector2(force, 0), new Vector2(0, force), new Vector2(force, force)};






    public Bomb(World world)
    {
        super();
        this.world = world;
        initBomb();
    }

    private void initBomb()
    {
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
        bombBody.setTransform(c.getBodyPosition().x, c.getBodyPosition().y, 0);
        bombBody.setType(BodyDef.BodyType.DynamicBody);
        bombBody.applyForceToCenter(new Vector2(100 * isChar1, 2), true);
        isThrown = true;

    }

    public void BOOM()
    {
        Vector2 pos = new Vector2(bombBody.getWorldCenter());

        bullets[0] = new Bullet(world, pos, VELOCITY_VECTORS[0], FORCE_VECTORS[0], new Texture("bomb.png"));
        bullets[1] = new Bullet(world, pos, Utils.flipVectorX(VELOCITY_VECTORS[0]), Utils.flipVectorX(FORCE_VECTORS[0]),new Texture("bomb.png"));
        bullets[2] = new Bullet(world, pos, VELOCITY_VECTORS[1], FORCE_VECTORS[1],new Texture("bomb.png"));
        bullets[3] = new Bullet(world, pos, Utils.flipVectorX(VELOCITY_VECTORS[2]), Utils.flipVectorX(VELOCITY_VECTORS[2]),new Texture("bomb.png"));
        bullets[4] = new Bullet(world, pos, VELOCITY_VECTORS[2], FORCE_VECTORS[2],new Texture("bomb.png"));


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
            for (Bullet b : bullets)
            {

                if (b != null)
                {
                    cnt++;
                    b.update(delta);
                    if (b.isOutOfRange(WeaponManager.BOMB_RANGE, true))
                    {
                        b.remove();
                        // Prevents memory leaks
                        bullets[cnt - 1] = null;
                    }

                }
            }
            if (cnt == 0)
                isExploded = false;
        }

    }
}
