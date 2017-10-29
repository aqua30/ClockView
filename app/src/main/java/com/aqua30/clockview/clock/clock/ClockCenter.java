package com.aqua30.clockview.clock.clock;

/**
 * Created by Saurabh 2017.
 */

public class ClockCenter {

    /* xy coordinates for center of the clock */
    Coordinates coordinates;
    /* default radius of center circle */
    int mRadius;

    public ClockCenter(int mRadius) {
        this.mRadius = mRadius;
    }

    public ClockCenter() {}

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }
}
