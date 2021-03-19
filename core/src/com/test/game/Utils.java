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

    /**
     *
     * @param p1 Vector2 of point
     * @param p2 Vector2 of point 2
     * @return Euclidean Distance between the two points
     */
    public static float getDistance(Vector2 p1, Vector2 p2)
    {
        return (float) Math.sqrt(( (p1.x - p2.x) * (p1.x - p2.x) ) + ((p1.y - p2.y) * (p1.y - p2.y)));
    }
}
