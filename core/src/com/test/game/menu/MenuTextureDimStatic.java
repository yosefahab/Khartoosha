package com.test.game.menu;

import com.badlogic.gdx.graphics.Texture;

public class MenuTextureDimStatic extends MenuTextureDim { //texture that doesn't change when active
    Texture texture;

    public MenuTextureDimStatic(int WIDTH, int HEIGHT, int y, int x, String textureName) {
        super(WIDTH, HEIGHT, y, x);
        this.texture = new Texture("menu/menu_" + textureName + ".png");
        /*
        *********************VERY IMPORTANT********************
        * Naming of new Texture pictures must follow the following
        * each class or screen has an array that includes all the textures name, each textureNames[] set differently
         in different classes
        * name the pic as:
        * " menu_(here put the name and remove parentheses).png "
         */
    }
    public MenuTextureDimStatic(int WIDTH, int HEIGHT, int y, int x, Texture texture) {
        super(WIDTH, HEIGHT, y, x);
        this.texture = texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose(){
        this.texture.dispose();
    }
}
