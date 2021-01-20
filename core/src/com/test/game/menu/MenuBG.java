package com.test.game.menu;

import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;

public class MenuBG {
    public static int bgOffset = 0;
    public static Texture bg;

    public MenuBG() {
        bg = new Texture("menu/menu_bg_darker1.png");
    }

    protected void displayBG(){
        bgOffset+=2;
        if(bgOffset % 900 == 0){
            bgOffset = 0;
        }
        Khartoosha.batch.draw(bg, -bgOffset,0, 900, Khartoosha.Gheight);
        Khartoosha.batch.draw(bg, -bgOffset + 900,0, 900,Khartoosha.Gheight);
        Khartoosha.batch.draw(bg, -bgOffset + 900 + 900 ,0, 900,Khartoosha.Gheight);
    }
}
