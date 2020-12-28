package com.test.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private final float mapWIDTH = 992;
    private final float mapHEIGHT = 672;

    // Camera bound points
    private float minX, minY, maxX, maxY;



    public Camera()
    {
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(worldWidth/ Khartoosha.PPM, worldHight  / Khartoosha.PPM, gameCam);

        //debug
        //viewport = new FitViewport((worldWidth + 300)/ Khartoosha.PPM, (worldHight + 300) / Khartoosha.PPM, gameCam);
    }

    public void initCam()
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

        boundCamera(charactersAVGX, charactersAVGY, camX, camY);
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

        else if (characterX > maxX)
            camX = maxX;
        else
            camX = characterX;

        if (characterY < minY)
            camY = minY;
        else if (characterY > maxY)
            camY = maxY;
        else
            camY = characterY;

        gameCam.position.x = camX;
        gameCam.position.y = camY;
    }


}
