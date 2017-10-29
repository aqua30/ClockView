package com.aqua30.clockview.clock.drawings;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.aqua30.clockview.R;
import com.aqua30.clockview.clock.clock.ClockCenter;
import com.aqua30.clockview.clock.clock.ClockCover;
import com.aqua30.clockview.clock.clock.Coordinates;
import com.aqua30.clockview.clock.clock.IntervalHeading;
import com.aqua30.clockview.clock.clock.IntervalLine;
import com.aqua30.clockview.clock.clock.MinutesTicker;
import com.aqua30.clockview.clock.clock.SecondsTicker;
import com.aqua30.clockview.clock.controller.ClockGeometry;
import com.aqua30.clockview.clock.controller.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;

import static com.aqua30.clockview.clock.drawings.ClockConstants.ClockTickAnimationTime;
import static com.aqua30.clockview.clock.drawings.ClockConstants.centerRadius;
import static com.aqua30.clockview.clock.drawings.ClockConstants.intervalAngle;
import static com.aqua30.clockview.clock.drawings.ClockConstants.intervalsCount;
import static com.aqua30.clockview.clock.drawings.ClockConstants.maxDegrees;
import static com.aqua30.clockview.clock.drawings.ClockConstants.minDegrees;
import static com.aqua30.clockview.clock.drawings.ClockConstants.minRotationAngle;
import static com.aqua30.clockview.clock.drawings.ClockConstants.offsetIntervalAngle;

/**
 * Created by Saurabh 2017.
 */

public class ClockView extends View implements ValueAnimator.AnimatorUpdateListener, View.OnTouchListener {

    @BindColor(R.color.black)int black;
    @BindColor(R.color.colorPrimary)int blue;
    @BindColor(R.color.red)int red;

    private SecondsTicker secondsTicker;
    private MinutesTicker minutesTicker;
    private ClockCenter clockCenter;
    private ClockCover clockCover;
    private List<Interval> intervals;

    private BasePaint secondsTickerPaint;
    private BasePaint minutesTickerPaint;
    private BasePaint intervalHeadingPaint;
    private BasePaint intervalPaint;
    private BasePaint clockCenterPaint;
    private BasePaint clockCoverPaint;

    private int mRadius = 0;
    private int finalWidth = 0;
    private int finalHeight = 0;
    private double secondsRotation, minutesRotation;
    private int toRotationAngle = -offsetIntervalAngle;
    private int fromRotationAngle;
    
    private int minutesCounter = 0, secondsCounter = 0;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        /* intializing paint objects */
        secondsTickerPaint = new BasePaint.Builder(black)
                .setAntiAlias(true)
                .setStrokeCap(Paint.Cap.ROUND)
                .setStrokeWidth(4)
                .build();
        minutesTickerPaint = new BasePaint.Builder(red)
                .setAntiAlias(true)
                .setStrokeCap(Paint.Cap.ROUND)
                .setStrokeWidth(8)
                .build();
        clockCenterPaint = new BasePaint.Builder(blue)
                .setAntiAlias(true)
                .setStyle(Paint.Style.FILL)
                .setStrokeWidth(8)
                .build();
        clockCoverPaint = new BasePaint.Builder(blue)
                .setAntiAlias(true)
                .setStrokeCap(Paint.Cap.ROUND)
                .setStyle(Paint.Style.STROKE)
                .setStrokeWidth(8)
                .build();
        intervalHeadingPaint = new BasePaint.Builder(red)
                .setAntiAlias(true)
                .build();
        intervalPaint = new BasePaint.Builder(blue)
                .setAntiAlias(true)
                .setStyle(Paint.Style.FILL)
                .setStrokeWidth(8)
                .setStrokeCap(Paint.Cap.ROUND)
                .build();

        clockCenter = new ClockCenter(centerRadius);
        clockCover = new ClockCover();
        secondsTicker = new SecondsTicker();
        minutesTicker = new MinutesTicker();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Util.Log("onMeasure");
        finalWidth = getMeasuredWidth() / 3;
        finalHeight = getMeasuredWidth() / 3;
        mRadius = (finalWidth / 2);
        setMeasuredDimension(finalWidth, finalHeight );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* drawing the cover circle for clock */
        canvas.drawPath(clockCover.getPath(), clockCoverPaint.getPaint());
        /* center point of clock */
        canvas.drawCircle(clockCenter.getCoordinates().getCood_X(),
                clockCenter.getCoordinates().getCood_Y(),
                clockCenter.getRadius(),
                clockCenterPaint.getPaint());
        /* finally drawing the interval lines */
        for (Interval interval : intervals) {
            canvas.drawLine(interval.getIntervalLine().getStartXY().getCood_X(),
                    interval.getIntervalLine().getStartXY().getCood_Y(),
                    interval.getIntervalLine().getEndXY().getCood_X(),
                    interval.getIntervalLine().getEndXY().getCood_Y(),
                    intervalPaint.getPaint());
            /* drawing the text with interval */
            canvas.drawText(interval.getIntervalHeading().getHeading(),
                    interval.getIntervalHeading().getCoordinates().getCood_X(),
                    interval.getIntervalHeading().getCoordinates().getCood_Y(),
                    intervalHeadingPaint.getPaint());
        }
        /* drawing the secondsTicker */
        canvas.drawLine(secondsTicker.getStartXY().getCood_X(),
                secondsTicker.getStartXY().getCood_Y(),
                secondsTicker.getEndXY().getCood_X(),
                secondsTicker.getEndXY().getCood_Y(),
                secondsTickerPaint.getPaint());
        /* drawing the minutesTicker */
        canvas.drawLine(minutesTicker.getStartXY().getCood_X(),
                minutesTicker.getStartXY().getCood_Y(),
                minutesTicker.getEndXY().getCood_X(),
                minutesTicker.getEndXY().getCood_Y(),
                minutesTickerPaint.getPaint());
    }

    public void tick(final int seconds){
        secondsCounter = seconds;
        toRotationAngle = secondsCounter * minRotationAngle;
        Util.Log(String.format("fromRotationAngle = %d, toRotationAngle = %d", fromRotationAngle, toRotationAngle));
        ValueAnimator animator = ValueAnimator.ofInt(fromRotationAngle, toRotationAngle);
        animator.setDuration(ClockTickAnimationTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(this);
        fromRotationAngle = (toRotationAngle == maxDegrees) ? minDegrees : toRotationAngle;
        animator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (clockCenter != null){
            final int value = (int) animation.getAnimatedValue();
            secondsRotation = ClockGeometry.getRadianAngle(value - 90);
            secondsTicker.setEndXY(
                    ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 50, secondsRotation),
                    ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 50, secondsRotation)
            );
            if (value == toRotationAngle) {
                /* formula is for 1 tick of seconds, 0.1 degrees of movement for minuteTicker */
                minutesRotation = ClockGeometry.getRadianAngle((int) (((minutesCounter * 6) + (0.1f * secondsCounter)) - 90));
                minutesTicker.setEndXY(
                        ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 70, minutesRotation),
                        ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 70, minutesRotation)
                );
                if (value == maxDegrees) {
                    if (minutesCounter < 60) {  /* with every tick, check if it is one rotation */
                        ++minutesCounter;   /* if not then increment minutes */
                    } else {
                        minutesCounter = 0; /* one rotation completed for minutes, now hour will */
                    }
                }
            }
            invalidate();
        }
    }

    /*
     * this method should be called once the view gets drawn on
     * the screen to initialize the base parameters for the clock
     * */
    public void initialize() {
        clockCenter.setCoordinates(new Coordinates(finalWidth / 2, finalHeight / 2));
        clockCover.setRect(16, 16, finalWidth - 16, finalHeight - 16);
        clockCover.setPath();
        secondsTicker.setStartXY(clockCenter.getCoordinates().getCood_X(), clockCenter.getCoordinates().getCood_Y());
        minutesTicker.setStartXY(clockCenter.getCoordinates().getCood_X(), clockCenter.getCoordinates().getCood_Y());
        secondsRotation = ClockGeometry.getRadianAngle(-offsetIntervalAngle);
        secondsTicker.setEndXY(
                ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 50, secondsRotation),
                ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 50, secondsRotation)
        );
        minutesTicker.setEndXY(
                ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 70, secondsRotation),
                ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 70, secondsRotation)
        );
        intervalHeadingPaint.setTextSize((int) (0.08f * finalWidth));
        /* if intervals are not available then populate */
        if (intervals == null || intervals.size() == 0) {
            intervals = new ArrayList<>();
         /* drawing the intervals with a span of 5 minutes */
            for (int i = 0; i < intervalsCount; i++) {
                final IntervalLine line = new IntervalLine();
                final IntervalHeading heading = new IntervalHeading();
                secondsRotation = ClockGeometry.getRadianAngle(-offsetIntervalAngle + intervalAngle + (i * intervalAngle));

                line.setStartXY(
                        ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 16, secondsRotation),
                        ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 16, secondsRotation)
                );
                line.setEndXY(
                        ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 26, secondsRotation),
                        ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 26, secondsRotation)
                );

                heading.setHeading(String.valueOf(i + 1));
                heading.setCoordinates(
                        ClockGeometry.calculateX(clockCenter.getCoordinates().getCood_X(), mRadius - 50, secondsRotation)
                                - (int) (intervalHeadingPaint.getPaint().measureText(heading.getHeading()) / 2),
                        ClockGeometry.calculateY(clockCenter.getCoordinates().getCood_Y(), mRadius - 50, secondsRotation)
                                - (int) ((intervalHeadingPaint.getPaint().descent()
                                + intervalHeadingPaint.getPaint().ascent()) / 2)
                );
                intervals.add(new Interval(line, heading));
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Util.Log(String.format("touch_X: %d, touch_Y: %d", getX(), getY()));
                Util.Log(String.format("pivot_X: %d, pivot_Y: %d", getPivotX(), getPivotY()));
                break;
        }
        return true;
    }
}