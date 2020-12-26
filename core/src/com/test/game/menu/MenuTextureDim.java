package com.test.game;

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

    public MenuTextureDim(int WIDTH, int HEIGHT, int y, int x, int textureNum) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        Y = y;
        X = x;
        this.active = new Texture("menu/menu_char"+String.valueOf(textureNum)+"_active.png");
        this.inActive = new Texture("menu/menu_char"+String.valueOf(textureNum)+"_inactive.png");
        /*
        *********************VERY IMPORTANT********************
        * Naming of new Character pictures must follow the following
        * name the pic as:
        * " menu_char_(here put the number of the char and remove parentheses)_active.png " for the active pic
        * and " menu_char_(here put the number of the char and remove parentheses)_inactive.png " for the inactive pic
         */
    }


}
