package com.aqua30.clockview.clock.drawings;

import android.graphics.Paint;

/**
 * Created by Saurabh 2017.
 */

public class BasePaint {

    private Paint mPaint;
    /* paint properties which we might need for drawing */
    private int color;
    private Paint.Cap strokeCap;
    private Paint.Style style;
    private int strokeWidth;
    private boolean antiAlias;
    private int textSize;

    /* using builder pattern to make optional attributes accessible from single point */
    public BasePaint(Builder builder) {
        this.mPaint = builder.mPaint;
        this.color = builder.color;
        this.strokeCap = builder.strokeCap;
        this.style = builder.style;
        this.strokeWidth = builder.strokeWidth;
        this.antiAlias = builder.antiAlias;
    }

    public static class Builder {

        Paint mPaint;
        /* paint properties which we might need for drawing */
        int color;
        Paint.Cap strokeCap;
        Paint.Style style;
        int strokeWidth;
        boolean antiAlias;

        public Builder(int color) {
            mPaint = new Paint();
            mPaint.setColor(color);
        }

        public Builder setStrokeCap(Paint.Cap strokeCap) {
            mPaint.setStrokeCap(strokeCap);
            return this;
        }

        public Builder setStyle(Paint.Style style) {
            mPaint.setStyle(style);
            return this;
        }

        public Builder setStrokeWidth(int strokeWidth) {
            if (strokeWidth == 0){
                strokeWidth = 8;
            }
            mPaint.setStrokeWidth(strokeWidth);
            return this;
        }

        public Builder setAntiAlias(boolean antiAlias) {
            mPaint.setAntiAlias(antiAlias);
            return this;
        }

        public BasePaint build(){
            return new BasePaint(this);
        }
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint.Cap getStrokeCap() {
        return strokeCap;
    }

    public void setStrokeCap(Paint.Cap strokeCap) {
        this.strokeCap = strokeCap;
    }

    public Paint.Style getStyle() {
        return style;
    }

    public void setStyle(Paint.Style style) {
        this.style = style;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        mPaint.setTextSize(textSize);
    }
}
