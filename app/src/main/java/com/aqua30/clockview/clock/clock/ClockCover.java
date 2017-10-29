package com.aqua30.clockview.clock.clock;

import android.graphics.Path;
import android.graphics.RectF;

import static com.aqua30.clockview.clock.drawings.ClockConstants.maxDegrees;
import static com.aqua30.clockview.clock.drawings.ClockConstants.minDegrees;

/**
 * Created by Saurabh 2017.
 */

public class ClockCover {

    /* the border of the circle */
    Path mPath;
    /* rectangle inside which we'll draw this circle */
    RectF rectF;

    public ClockCover() {
        mPath = new Path();
        rectF = new RectF();
    }

    public Path getPath() {
        return mPath;
    }

    public void setRect(int left, int top, int bottom, int right){
        rectF.set(left, top, bottom, right);
    }

    /* should be called after setRect() */
    public void setPath() {
        mPath.arcTo(rectF, minDegrees, maxDegrees - 1, true);
    }
}