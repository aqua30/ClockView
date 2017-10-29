package com.aqua30.clockview.clock.drawings;

import com.aqua30.clockview.clock.clock.IntervalHeading;
import com.aqua30.clockview.clock.clock.IntervalLine;

/**
 * Created by Saurabh 2017.
 */

public class Interval {

    IntervalLine intervalLine;
    IntervalHeading intervalHeading;

    public Interval(IntervalLine intervalLine, IntervalHeading intervalHeading) {
        this.intervalLine = intervalLine;
        this.intervalHeading = intervalHeading;
    }

    public IntervalLine getIntervalLine() {
        return intervalLine;
    }

    public IntervalHeading getIntervalHeading() {
        return intervalHeading;
    }
}
