package com.test.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;

import java.util.Vector;

public class HUD {



    //The hearts stuff
    private final Vector<Sprite> lives1 = new Vector<>(5);
    private final Vector<Sprite> lives2 = new Vector<>(5);
    private final Sprite s =new Sprite();

    private final Sprite Hud1 =new Sprite();
    private final Sprite Hud2 =new Sprite();
    private final Sprite[] player =new Sprite[3];

    //current weapon stuff
    private final Sprite player1_gun=new Sprite();
    private final Sprite player2_gun=new Sprite();

    private int size1=5;
    private int size2=5;

    public HUD(){
        Texture tex = new Texture("Hud/Hearts.png");
        s.setRegion(tex);
        s.setSize(25/ Khartoosha.PPM ,25/Khartoosha.PPM);
        for (int i=0 ; i<5 ; i++) {
            lives1.add(s);
            lives2.add(s);
        }
        Sprite num1 = new Sprite();
        // current character stuff
        Texture char1 = new Texture("Hud/johnnyHead.png");
        num1.setRegion(char1);
        Sprite num2 = new Sprite();
        Texture char2 = new Texture("Hud/bruceHead.png");
        num2.setRegion(char2);
        Sprite num3 = new Sprite();
        Texture char3 = new Texture("Hud/mandoHead.png");
        num3.setRegion(char3);
        Texture h1 = new Texture("Hud/Player1Hud.png");
        Hud1.setRegion(h1);
        Hud1.flip(true,false);
        Texture h2 = new Texture("Hud/Player2Hud.png");
        Hud2.setRegion(h2);
        Hud1.setSize(95f/Khartoosha.PPM,75f/Khartoosha.PPM);
        Hud2.setSize(95f/Khartoosha.PPM,75f/Khartoosha.PPM);
        num1.setSize(55f/Khartoosha.PPM,55f/Khartoosha.PPM);
        num2.setSize(55f/Khartoosha.PPM,55f/Khartoosha.PPM);
        num3.setSize(40f/Khartoosha.PPM,47f/Khartoosha.PPM);
        player[0]= num1;
        player[1]= num2;
        player[2]= num3;


        player1_gun.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);
        player2_gun.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);

    }

    public void Lose_life(Character character1 , Character character2 ){

        if (character1.dead){
            lives1.remove(lives1.size()-1);
             size1--;
        }
        if (character2.dead){
            lives2.remove(lives2.size()-1);
            size2--;
        }
        if (size1< Character.current_lives){
            lives1.add(s);
            size1= Character.current_lives;

        }

        if (size2< Character.current_lives){
            lives1.add(s);
            size2= Character.current_lives;

        }
    }

    public void Hearts_pos(Camera camera){

        float l=0;
        for (int i=0 ; i<lives1.size(); i++) {
            l+=.3;
            lives1. elementAt(i).setPosition(-.2f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2)+l,camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-5.95f);
            lives1.elementAt(i).draw(Khartoosha.batch);
        }

        l=0;
        for (int i=0 ; i<lives2.size(); i++) {
            l+=.3;
            lives2.elementAt(i).setPosition(0.02f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2)-l,camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.85f);
            lives2.elementAt(i).draw(Khartoosha.batch);

        }
    }

    public void Hud_pos(Camera camera){

        Hud1.setPosition(.1f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-6.76f);
        Hud1.draw(Khartoosha.batch);
        Hud2.setPosition(-1.f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.04f);
        Hud2.draw(Khartoosha.batch);

    }

    public void char_pos(Camera camera , Character character1 , Character character2){

        player[character1.current_char-1].setPosition(.15f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-6.7f);
        player[character1.current_char-1].draw(Khartoosha.batch);
        player[character2.current_char-1].setPosition(-.7f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.12f);
        player[character2.current_char-1].setFlip(true,false);

        player[character2.current_char-1].draw(Khartoosha.batch);

    }
    public void Gun_pos( Camera camera ,Character character1 , Character character2) {
        player1_gun.setRegion(character1.currentWeapon.TEXTURE_IDLE);
        player2_gun.setRegion(character2.currentWeapon.TEXTURE_IDLE);
        player1_gun.setPosition(.3f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - .6f);
        player1_gun.draw(Khartoosha.batch);
        player2_gun.setPosition(-0.7f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6.2f);
        player2_gun.setFlip(true,false);
        player2_gun.draw(Khartoosha.batch);
    }
}
