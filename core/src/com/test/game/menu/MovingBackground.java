package com.test.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.Map;

public class MovingBackground
{
    private static int bgOffset = 0;
    public static Texture bg;

    public MovingBackground(Texture texture)
    {
        bg = texture;
    }

    public static void displayMenuBG()
    {
        bgOffset += 2;
        if (bgOffset % 900 == 0)
        {
            bgOffset = 0;
        }
        Khartoosha.batch.draw(bg, -bgOffset, 0, 900, Gdx.graphics.getHeight());
        Khartoosha.batch.draw(bg, -bgOffset + 900, 0, 900, Gdx.graphics.getHeight());
        Khartoosha.batch.draw(bg, -bgOffset + 900 + 900, 0, 900, Gdx.graphics.getHeight());
    }

    public void displayBG()
    {
        bgOffset += 2;
        if (bgOffset % 900 == 0)
        {
            bgOffset = 0;
        }
        Khartoosha.batch.draw(bg, -bgOffset, 0, 900, Khartoosha.Gheight);
        Khartoosha.batch.draw(bg, -bgOffset + 900, 0, 900, Khartoosha.Gheight);
        Khartoosha.batch.draw(bg, -bgOffset + 900 + 900, 0, 900, Khartoosha.Gheight);
    }

    public void displayBGmap()
    {
        bgOffset += 2;
        if (bgOffset % Map.mapHeight == 0)
        {
            bgOffset = 0;
        }

        Khartoosha.batch.draw(bg, 0, -bgOffset/ Khartoosha.PPM, Map.mapWidth/ Khartoosha.PPM, (Map.mapHeight/ Khartoosha.PPM) * 2);
        Khartoosha.batch.draw(bg, 0, (bgOffset - Map.mapHeight) / Khartoosha.PPM, Map.mapWidth/ Khartoosha.PPM, (Map.mapHeight/ Khartoosha.PPM) * 2);

    }

    public static Texture getBg() {
        return bg;
    }

    public static void setBg(Texture bg) {
        MovingBackground.bg = bg;
    }
    public static Texture initializeMenuBG() {
        return new Texture("menu/menu_bg_darker1.png");
    }
}
