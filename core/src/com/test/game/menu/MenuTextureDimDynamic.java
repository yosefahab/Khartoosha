package com.test.game.menu;

import com.badlogic.gdx.graphics.Texture;


public class MenuTextureDimDynamic extends MenuTextureDim { //texture that is either active or inactive
    private Texture active;
    private Texture inActive;

    public MenuTextureDimDynamic(int WIDTH, int HEIGHT, int y, int x, String textureName) {
        super(WIDTH, HEIGHT, y, x);
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

    public MenuTextureDimDynamic(int WIDTH, int HEIGHT, int y, int x, String textureName, boolean activeWhenHover) {
        super(WIDTH, HEIGHT, y, x);
        if (!activeWhenHover) {
            this.active = new Texture("menu/menu_" + textureName + ".png");
            this.inActive = this.active;
        } else{
            this.active = new Texture("menu/menu_" + textureName + "_active.png");
            this.inActive = new Texture("menu/menu_" + textureName + "_inactive.png");
        }
    }

    public Texture getActive() {
        return active;
    }

    public Texture getInActive() {
        return inActive;
    }

    public void dispose(){
        this.active.dispose();
        this.inActive.dispose();
    }

}
