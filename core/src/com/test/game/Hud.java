package com.test.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;

import java.util.Vector;

public class Hud
{
    //The hearts stuff
    private final Vector<Sprite> lives1 = new Vector<>(Character.MAX_LIVES);
    private final Vector<Sprite> lives2 = new Vector<>(Character.MAX_LIVES);
    private final Vector<Sprite> bullets = new Vector<>(30);
    private final Sprite s = new Sprite();

    public static Texture player1Hud;
    public static Texture player2Hud;
    private int size1 = 5;
    private int size2 = 5;

    private static Texture[] heads;
    private static Texture[] heads2;
    public Hud()
    {
        player1Hud = new Texture("Hud/Player1Hud.png");
        player2Hud = new Texture("Hud/Player2Hud.png");

        heads = new Texture[3];
        heads[0] = new Texture("Hud/johnnyHead.png");
        heads[1] = new Texture("Hud/bruceHead.png");
        heads[2] = new Texture("Hud/mandoHead.png");

        heads2 = new Texture[3];
        heads2[0] = new Texture("Hud/johnnyHead.png");
        heads2[1] = new Texture("Hud/bruceHead.png");
        heads2[2] = new Texture("Hud/mandoHead.png");

        s.setRegion(new Texture("Hud/Hearts.png"));
        s.setSize(25 / Khartoosha.PPM, 25 / Khartoosha.PPM);

        Sprite bulletSprite = new Sprite();
        bulletSprite.setRegion(new Texture ("Hud/bulletCount.png"));
        bulletSprite.setSize(25/Khartoosha.PPM, 15/Khartoosha.PPM);
        for (int i = 0; i < Character.MAX_LIVES; i++)
        {
            lives1.add(s);
            lives2.add(s);
        }
        for (int i=0; i<30; i++)
        {
            bullets.add(bulletSprite);
        }
    }

    public static void endGame(int winner) {
        float xPos = PlayScreen.camera.getCamPos().x;
        float yPos = PlayScreen.camera.getCamPos().y;

        if (winner==1){
        Khartoosha.batch.draw(player1Hud,xPos- (200/Khartoosha.PPM)/2,yPos-(200/Khartoosha.PPM)/2,200/Khartoosha.PPM,200/Khartoosha.PPM);
        Khartoosha.batch.draw(heads[PlayScreen.character1.current_char()-1],xPos-(100/Khartoosha.PPM)/2,yPos-(100/Khartoosha.PPM)/2,100/Khartoosha.PPM,100/Khartoosha.PPM);

        }

        else{
            Khartoosha.batch.draw(player2Hud,xPos- (200/Khartoosha.PPM)/2,yPos-(200/Khartoosha.PPM)/2,200/Khartoosha.PPM,200/Khartoosha.PPM);
            Khartoosha.batch.draw(heads[PlayScreen.character2.current_char()-1],xPos-(100/Khartoosha.PPM)/2,yPos-(100/Khartoosha.PPM)/2,100/Khartoosha.PPM,100/Khartoosha.PPM);

        }
        Khartoosha.batch.draw(new Texture("Hud/gameEnd.png"), xPos-(200/Khartoosha.PPM)/2,yPos-(200/Khartoosha.PPM)/2,200/Khartoosha.PPM,200/Khartoosha.PPM);
        Khartoosha.batch.draw(new Texture("Hud/text.png"), xPos-(500/Khartoosha.PPM)/2,yPos-(400/Khartoosha.PPM)/2,500/Khartoosha.PPM,100/Khartoosha.PPM);

    }

    public void Lose_life(Character character1, Character character2)
    {

        if (character1.dead && !lives1.isEmpty()) {
                lives1.remove(lives1.size() - 1);
                size1--;
        }
        if (character2.dead && !lives2.isEmpty()) {
                lives2.remove(lives2.size() - 1);
                size2--;
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

        float constant = 0;
        for (int i = 0; i < lives1.size(); i++)
        {
            constant += .3;
            lives1.elementAt(i).setPosition(-.2f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2) + constant, camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 5.95f);
            lives1.elementAt(i).draw(Khartoosha.batch);
        }
        constant = 0;
        for (int i = 0; i < lives2.size(); i++)
        {
            constant += .3;
            lives2.elementAt(i).setPosition(0.02f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2) - constant, camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + .85f);
            lives2.elementAt(i).draw(Khartoosha.batch);

        }
    }
    public void bullet_pos(Camera camera, Character character1, Character character2)
    {
        float constant=0;
        for (int i=0; i<character1.weapon.getAmmo(); i++)
        {
            constant+=.1f;
            bullets.elementAt(i).setFlip(false,false);
            bullets.elementAt(i).setPosition(camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2)+2f+constant);
            bullets.elementAt(i).draw(Khartoosha.batch);

        }
        constant=0;
        for (int i=0; i<character2.weapon.getAmmo(); i++)
        {
            constant+=.1f;
            bullets.elementAt(i).setFlip(true,false);
            bullets.elementAt(i).setPosition(-0.27f+camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2)+2f+constant);
            bullets.elementAt(i).draw(Khartoosha.batch);

        }

    }

    public void Hud_pos(Camera camera)
    {
        Khartoosha.batch.draw(player1Hud,.9f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.76f,-80f / Khartoosha.PPM, 75f / Khartoosha.PPM);
        Khartoosha.batch.draw(player2Hud,-.9f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2),camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + .04f,80f / Khartoosha.PPM, 75f / Khartoosha.PPM);
    }

    public void heads_pos(Camera camera, Character character1, Character character2)
    {
        Khartoosha.batch.draw(heads[character1.current_char()-1],.2f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.7f,50/Khartoosha.PPM,50/Khartoosha.PPM);
        Khartoosha.batch.draw(heads2[character2.current_char()-1],9.8f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - 6.7f,-50/Khartoosha.PPM,50/Khartoosha.PPM);
    }

    public void Gun_pos(Camera camera, Character character1, Character character2)
    {
        Khartoosha.batch.draw(character1.weapon.TEXTURE_IDLE,.3f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2),camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6f,130 / Khartoosha.PPM, 70 / Khartoosha.PPM);
        Khartoosha.batch.draw(character2.weapon.TEXTURE_IDLE,9.7f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6f,-130 / Khartoosha.PPM, 70 / Khartoosha.PPM);

    }
    public static void dispose() {
        player1Hud.dispose();
        player2Hud.dispose();
        for (Texture head : heads) {
            head.dispose();
        }

    }
}
