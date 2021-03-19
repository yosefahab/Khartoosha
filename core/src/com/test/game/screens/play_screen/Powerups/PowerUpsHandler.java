package com.test.game.screens.play_screen.Powerups;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.PlayScreen;

import java.util.Random;

public class PowerUpsHandler
{

    private Random generator;
    private PowerUp[] PUPs;
    private World box2dWorld;
    private PlayScreen playScreen;


    public PowerUpsHandler(PowerUp[] PUPs, World box2dWorld, PlayScreen playScreen)
    {
        generator = new Random();
        this.PUPs = PUPs;
        this.box2dWorld = box2dWorld;
        this.playScreen = playScreen;
    }


    public void init()
    {
        PUPs[0] = new SpeedBoost(box2dWorld,playScreen);
        PUPs[1] = new UpgradeWeapon(box2dWorld,playScreen);
        PUPs[2] = new Armor(box2dWorld,playScreen);
        PUPs[3] = new ExtraLife(box2dWorld,playScreen);
        PUPs[4] = new RefillAmmo(box2dWorld,playScreen);


    }

    public void update()
    {
        // Spawn pups if there's space
        if (PowerUp.currentPups < PowerUp.MAXPUPS)
        {
            // choose random powerup type to spawn
            int indx = generator.nextInt(PowerUp.MAXPUPS) ;

            PUPs[indx].spawn();

        }

        for (PowerUp pup:PUPs)
        {
            if (pup.isSpawned() || pup.isActive())
                pup.update();

        }

    }


    public void render()
    {
        for (PowerUp pup:PUPs)
        {
            if (pup.isSpawned())
                pup.draw(Khartoosha.batch);
        }

    }

    public PowerUp[] getPUPs() {
        return PUPs;
    }

    public Array<PowerUp> getSpawnedPups()
    {
        Array<PowerUp> spawnedPups = new Array<>();
        for(PowerUp pup:PUPs)
        {
            if (pup.isSpawned() && !pup.isActive())
                spawnedPups.add(pup);
        }

        return spawnedPups;
    }
}
