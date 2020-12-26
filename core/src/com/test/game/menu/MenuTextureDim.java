package com.test.game.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class MenuTextureDim {
    private final int WIDTH;
    private final int HEIGHT;
    private final int Y;
    private final int X;
    private Texture active;
    private Texture inActive;
    
    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public Texture getActive() {
        return active;
    }

    public Texture getInActive() {
        return inActive;
    }

    public MenuTextureDim(int WIDTH, int HEIGHT, int y, int x, String textureName) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        Y = y;
        X = x;
        this.active = new Texture("menu/menu_" + textureName + "_active.png");
        this.inActive = new Texture("menu/menu_" + textureName + "_inactive.png");
        /*
        *********************VERY IMPORTANT********************
        * Naming of new Texture pictures must follow the following
        * each class or screen has an array that includes all the textures name, each textureNames[] set differently
         in different classes
        * name the pic as:
        * " menu_(here put the name and remove parentheses)_active.png " for the active pic
        * and " menu_char_(here put the number of the char and remove parentheses)_inactive.png " for the inactive pic
         */
    }

    public void dispose(){
        this.active.dispose();
        this.inActive.dispose();
    }
}
