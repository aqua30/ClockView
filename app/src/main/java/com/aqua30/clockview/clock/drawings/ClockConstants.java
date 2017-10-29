package com.aqua30.clockview.clock.drawings;

/**
 * Created by HawkSafety(saurabh@hawksafety.com) on 26-10-2017.
 */

public class ClockConstants {

    /* the arc default starting angle is 0 degrees on x-axis. Without having an offset angle the counting
    *  starts from the x-axis and clock seems to be leading by 90 degrees.
    *  To match it with real clock structure, we need to shift this angle by 90 backwards.
    *  Hence, taking an offset value of 90. */
    public static final int offsetIntervalAngle = 90;
    /* It is the maximum interval we want in the clock. For now we're taking it as 12. */
    public static final int intervalsCount = 12;

    public static final int maxDegrees = 360;

    public static final int minDegrees = 0;
    /* minimum rotation angle for a clock */
    public static final int minRotationAngle = maxDegrees / 60; /* 60 = minutes in an hour, seconds in a minute */
    /* It depends on the @intervalsCount. This is the angle by which every ticking of clock pointer is defined.
     * e.g. if @intervalsCount = 12 then each tick would require to move by 30 degrees to go to next interval. */
    public static final int intervalAngle = maxDegrees / intervalsCount;
    /* default center circle radius */
    public static final int centerRadius = 5;
    /* minimum ticking interval for clock when in auto mode */
    public static final int ClockTickAutoInterval = 200;   /* 1 second in milliseconds */
    /* time for ticker moving animation */
    public static final int ClockTickAnimationTime = 100;   /* in milliseconds */
}
