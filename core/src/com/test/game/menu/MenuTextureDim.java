package com.test.game.menu;

public class MenuTextureDim {
    protected float WIDTH;
    protected float HEIGHT;
    protected float Y;
    protected float X;

    public void setDim(float WIDTH, float HEIGHT)
    {
        setWIDTH(WIDTH);
        setHEIGHT(HEIGHT);
    }

    public void setPos(float Y, float X)
    {
        setY(Y);
        setX(X);
    }

    public void setWIDTH(float WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(float HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setY(float y) {
        Y = y;
    }

    public void setX(float x) {
        X = x;
    }

    public float getWIDTH() {
        return WIDTH;
    }

    public float getHEIGHT() {
        return HEIGHT;
    }

    public float getY() {
        return Y;
    }

    public float getX() {
        return X;
    }

    public MenuTextureDim(float WIDTH, float HEIGHT, float y, float x) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        Y = y;
        X = x;
    }

}
