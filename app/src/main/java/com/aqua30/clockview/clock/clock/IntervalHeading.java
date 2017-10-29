package com.aqua30.clockview.clock.clock;

/**
 * Created by Saurabh 2017.
 */

public class IntervalHeading {

    /* xy location for the text */
    Coordinates coordinates;
    /* text to be displayed*/
    String heading;

    public IntervalHeading() {
        coordinates = new Coordinates();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates.setCood_X(x);
        this.coordinates.setCood_Y(y);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}