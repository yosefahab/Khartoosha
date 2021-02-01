package com.test.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;

import java.util.Vector;

public class Hud
{
    //The hearts stuff
    private final Vector<Sprite> lives1 = new Vector<>(Character.MAX_LIVES);
    private final Vector<Sprite> lives2 = new Vector<>(Character.MAX_LIVES);
    private final Sprite s = new Sprite();

    private final Texture player1Hud;
    private final Texture player2Hud;
    private int size1 = 5;
    private int size2 = 5;

    private final Texture[] heads;
    public Hud()
    {
        player1Hud = new Texture("Hud/Player1Hud.png");
        player2Hud = new Texture("Hud/Player2Hud.png");

        heads = new Texture[3];
        heads[0] = new Texture("Hud/johnnyHead.png");
        heads[1] = new Texture("Hud/johnnyHead.png");
        heads[2] = new Texture("Hud/johnnyHead.png");

        Texture heartTexture = new Texture("Hud/Hearts.png");
        s.setRegion(heartTexture);
        s.setSize(25 / Khartoosha.PPM, 25 / Khartoosha.PPM);
        for (int i = 0; i < Character.MAX_LIVES; i++)
        {
            lives1.add(s);
            lives2.add(s);
        }
    }

    public void Lose_life(Character character1, Character character2)
    {

        if (character1.dead) {
            if (!lives1.isEmpty()) {
                lives1.remove(lives1.size() - 1);
                size1--;
            }
        }
        if (character2.dead) {
            if (!lives2.isEmpty()) {
                lives2.remove(lives2.size() - 1);
                size2--;
            }
        }
        if (size1 < Character.current_lives)
        {
            lives1.add(s);
            size1 = Character.current_lives;

        }

        if (size2 < Character.current_lives)
        {
            lives1.add(s);
            size2 = Character.current_lives;

        }
    }

    public void Hearts_pos(Camera camera)
    {

        float l = 0;
        for (int i = 0; i < lives1.size(); i++)
        {
            l += .3;
            lives1.elementAt(i).setPosition(-.2f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2) + l, camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 5.95f);
            lives1.elementAt(i).draw(Khartoosha.batch);
        }

        l = 0;
        for (int i = 0; i < lives2.size(); i++)
        {
            l += .3;
            lives2.elementAt(i).setPosition(0.02f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2) - l, camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + .85f);
            lives2.elementAt(i).draw(Khartoosha.batch);

        }
    }

    public void Hud_pos(Camera camera)
    {
        Khartoosha.batch.draw(player1Hud,.9f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.76f,-80f / Khartoosha.PPM, 75f / Khartoosha.PPM);
        Khartoosha.batch.draw(player2Hud,-.9f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2),camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + .04f,80f / Khartoosha.PPM, 75f / Khartoosha.PPM);
    }

    public void char_pos(Camera camera, Character character1, Character character2)
    {
        Khartoosha.batch.draw(heads[character1.current_char()-1],.2f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.7f,50/Khartoosha.PPM,50/Khartoosha.PPM);
        Khartoosha.batch.draw(heads[character2.current_char()-1],9.8f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.7f,-50/Khartoosha.PPM,50/Khartoosha.PPM);
    }

    public void Gun_pos(Camera camera, Character character1, Character character2)
    {
        Khartoosha.batch.draw(character1.weapon.TEXTURE_IDLE,.3f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2),camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6f,130 / Khartoosha.PPM, 70 / Khartoosha.PPM);
        Khartoosha.batch.draw(character2.weapon.TEXTURE_IDLE,9.7f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6f,-130 / Khartoosha.PPM, 70 / Khartoosha.PPM);

    }
}
