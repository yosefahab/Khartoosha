package com.test.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class MenuCharDim {
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

    int WIDTH;
     int HEIGHT;
     int Y;
     int X;
     Texture active;
     Texture inActive;


    public MenuCharDim(int WIDTH, int HEIGHT, int y, int x, Texture active, Texture inActive) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        Y = y;
        X = x;
        this.active = active;
        this.inActive = inActive;
    }


}
