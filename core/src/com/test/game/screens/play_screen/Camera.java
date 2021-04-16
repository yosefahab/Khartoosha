package com.test.game.screens.play_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.Khartoosha;

public class Camera
{
    public OrthographicCamera gameCam;
    public Viewport viewport;
    private final float worldWidth = Khartoosha.Gwidth;
    private final float worldHight = Khartoosha.Gheight;

    // Camera positioning should work correctly as long as these values are correct
    private final float mapWIDTH = 1120;
    private final float mapHEIGHT = 800;
    boolean bound = true;


    // Camera bound points
    private float minX, minY, maxX, maxY;



    public Camera()
    {
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(worldWidth/ Khartoosha.PPM, worldHight  / Khartoosha.PPM, gameCam);
    }

    public void init()
    {
        gameCam.position.set(worldWidth / 2 / Khartoosha.PPM, worldHight / 2 / Khartoosha.PPM, 0);
        minX = 0.5F * (worldWidth / Khartoosha.PPM);
        minY = 0.5F * (worldHight / Khartoosha.PPM);

        maxX = (mapWIDTH / Khartoosha.PPM) - minX;
        maxY = (mapHEIGHT / Khartoosha.PPM) - minY;
    }

    public void update(Character c1, Character c2)
    {

        float charactersAVGX = ((c1.physicsBody.getPosition().x + c2.physicsBody.getPosition().x)) / 2;
        float charactersAVGY = ((c1.physicsBody.getPosition().y + c2.physicsBody.getPosition().y)) / 2;
        float camX = gameCam.position.x;
        float camY = gameCam.position.y;
        // Cam Debug Mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0))
        {

            bound = !bound;
            System.out.println("Camera Bound " + bound);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) // Right
            gameCam.position.x += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) // Left
            gameCam.position.x -= 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) // Up
            gameCam.position.y += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) // Right
            gameCam.position.y -= 0.1f;

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_9)) // IN
        {
            viewport.setWorldHeight(viewport.getWorldHeight() - 0.1f);
            viewport.setWorldWidth(viewport.getWorldWidth() - (0.1f* Khartoosha.Gwidth / Khartoosha.Gheight)  );
            viewport.apply();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_7)) // OUT
        {
            viewport.setWorldHeight(viewport.getWorldHeight() + 0.1f);
            viewport.setWorldWidth(viewport.getWorldWidth() + (0.1f* Khartoosha.Gwidth / Khartoosha.Gheight)  );
            viewport.apply();
            System.out.println(viewport.getWorldWidth());
            System.out.println(viewport.getWorldHeight());

        }




        // reset changes
        if (bound)
        {
            boundCamera(charactersAVGX, charactersAVGY, camX, camY);
            viewport.setWorldWidth(worldWidth/ Khartoosha.PPM);
            viewport.setWorldHeight(worldHight/ Khartoosha.PPM);
            viewport.apply();
        }


        gameCam.update();

    }

    public void update(Character c1)
    {
        float characterX = c1.physicsBody.getPosition().x;
        float characterY = c1.physicsBody.getPosition().y;
        float camX = gameCam.position.x;
        float camY = gameCam.position.y;
        boundCamera(characterX, characterY, camX, camY);
        gameCam.update();
    }

    private void boundCamera(float characterX, float characterY, float camX, float camY)
    {
        if (characterX < minX)
            camX = minX;

        else camX = Math.min(characterX, maxX);

        if (characterY < minY)
            camY = minY;
        else camY = Math.min(characterY, maxY);

        gameCam.position.x = camX;
        gameCam.position.y = camY;
    }

    public Vector2 getCamPos() {return new Vector2(gameCam.position.x,gameCam.position.y);}
}