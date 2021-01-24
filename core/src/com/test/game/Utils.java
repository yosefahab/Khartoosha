package com.test.game;

import com.badlogic.gdx.math.Vector2;

// This class should contain general helper functions
public  class Utils
{
    // There is no direct way in Vector2 to change a component's sign, this a clean way to do it
    public static Vector2 flipVectorX(Vector2 originalVector)
    {
        Vector2 reversedVector = new Vector2(originalVector);
        reversedVector.x *= -1;
        return reversedVector;
    }
}
