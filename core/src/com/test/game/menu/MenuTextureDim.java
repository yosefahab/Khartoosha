package com.test.game.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class MenuTextureDim {
    protected int WIDTH;
    protected int HEIGHT;
    protected int Y;
    protected int X;

    public void setDim(int WIDTH,int HEIGHT,int Y,int X)
    {
        setWIDTH(WIDTH);
        setHEIGHT(HEIGHT);
        setY(Y);
        setX(X);
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setX(int x) {
        X = x;
    }

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

    public MenuTextureDim(int WIDTH, int HEIGHT, int y, int x) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        Y = y;
        X = x;
    }

}
