package com.test.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.test.game.Khartoosha;
import com.test.game.sprites.Camera;
import com.test.game.sprites.Character;

import java.util.Vector;

public class HUD {

    //The hearts stuff
    private Vector<Sprite> lives1 =new Vector<Sprite>(5);
    private Vector<Sprite> lives2 =new Vector<Sprite>(5);
    private Sprite s =new Sprite();
    private Texture tex = new Texture("Hud/Hearts.png") ;

    // current character stuff
    private Texture char1 = new Texture("Hud/johnnyHead.png") ;
    private Texture char2 = new Texture("Hud/bruceHead.png") ;
    private Texture char3 = new Texture("Hud/mandoHead.png") ;
    private Texture H1 = new Texture("Hud/Player1Hud.png") ;
    private Texture H2 = new Texture("Hud/Player2Hud.png") ;
    private Sprite num1 =new Sprite();
    private Sprite num2 =new Sprite();
    private Sprite num3 =new Sprite();
    private Sprite Hud1 =new Sprite();
    private Sprite Hud2 =new Sprite();
    private Sprite player[]=new Sprite[3];

    //current weapon stuff
    private Sprite player1_gun=new Sprite();
    private Sprite player2_gun=new Sprite();

    private Camera camera;
    private Character character1 , character2;
    private int size1=5;
    private int size2=5;

    public HUD(){
        s.setRegion(tex);
        s.setSize(25/ Khartoosha.PPM ,25/Khartoosha.PPM);
        for (int i=0 ; i<5 ; i++) {
            lives1.add(s);
            lives2.add(s);

        }
        num1.setRegion(char1);
        num2.setRegion(char2);
        num3.setRegion(char3);
        Hud1.setRegion(H1);
        Hud1.flip(true,false);
        Hud2.setRegion(H2);
        Hud1.setSize(95f/Khartoosha.PPM,75f/Khartoosha.PPM);
        Hud2.setSize(95f/Khartoosha.PPM,75f/Khartoosha.PPM);
        num1.setSize(55f/Khartoosha.PPM,55f/Khartoosha.PPM);
        num2.setSize(55f/Khartoosha.PPM,55f/Khartoosha.PPM);
        num3.setSize(40f/Khartoosha.PPM,47f/Khartoosha.PPM);
        player[0]=num1;
        player[1]=num2;
        player[2]=num3;


        player1_gun.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);
        player2_gun.setSize(50/Khartoosha.PPM,50/Khartoosha.PPM);

    }

    public void Lose_life(Character character1 , Character character2 ){

        if ( character1.dead==true){
            lives1.remove(lives1.size()-1);
             size1--;
        }
        if (character2.dead==true){
            lives2.remove(lives2.size()-1);
            size2--;
        }
        if (size1<character1.current_lives){
            lives1.add(s);
            size1=character1.current_lives;

        }

        if (size2<character2.current_lives){
            lives1.add(s);
            size2=character2.current_lives;

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

        player[character1.current_char-1].setPosition(.18f+camera.getCamPos().x-((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y+ ((Khartoosha.Gheight/Khartoosha.PPM)/2)-6.7f);
        player[character1.current_char-1].draw(Khartoosha.batch);
        player[character2.current_char-1].setPosition(-.7f+camera.getCamPos().x+((Khartoosha.Gwidth/Khartoosha.PPM)/2),camera.getCamPos().y- ((Khartoosha.Gheight/Khartoosha.PPM)/2)+.12f);
        player[character2.current_char-1].draw(Khartoosha.batch);

    }
    public void Gun_pos( Camera camera ,Character character1 , Character character2) {
        player1_gun.setRegion(character1.currentWeapon.TEXTURE_IDLE);
        player2_gun.setRegion(character2.currentWeapon.TEXTURE_IDLE);
        player1_gun.setPosition(.3f + camera.getCamPos().x - ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y + ((Khartoosha.Gheight / Khartoosha.PPM) / 2) - .6f);
        player1_gun.draw(Khartoosha.batch);
        player2_gun.setPosition(-0.7f + camera.getCamPos().x + ((Khartoosha.Gwidth / Khartoosha.PPM) / 2), camera.getCamPos().y - ((Khartoosha.Gheight / Khartoosha.PPM) / 2) + 6.2f);
        player2_gun.draw(Khartoosha.batch);
    }
}
