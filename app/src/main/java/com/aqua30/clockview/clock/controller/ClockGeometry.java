package com.aqua30.clockview.clock.controller;

/**
 * Created by Saurabh 2017.
 */

public class ClockGeometry {

    public static double getRadianAngle(int angle){
        return angle * (Math.PI / 180);
    }

    public static int calculateX(int coodX, int radius, double rotationRadian){
        return (int) (coodX + ((radius) * Math.cos(rotationRadian)));
    }

    public static int calculateY(int coodY, int radius, double rotationRadian){
        return (int) (coodY + ((radius) * Math.sin(rotationRadian)));
    }
}
