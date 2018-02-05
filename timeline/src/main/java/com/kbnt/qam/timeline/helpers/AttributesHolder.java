package com.kbnt.qam.timeline.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.kbnt.qam.timeline.R;

class AttributesHolder {

    private static final float DEFAULT_STROKE = 4.0F;
    private static final float DEFAULT_TEXT_SIZE = 18.0F;
    private static final int DEFAULT_SERIF_HEIGHT = 20;

    private final TypedArray array;

    AttributesHolder(Context context, AttributeSet attrs) {
        array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimelineView, 0, 0);
    }

    private int getColor(int colorAttribute, int defaultColor) {
        return array.getColor(colorAttribute, defaultColor);
    }

    private float getStroke(int strokeAttribute, float defaultStroke) {
        return array.getFloat(strokeAttribute, defaultStroke);
    }


    private float getTextSize(int strokeAttribute, float defaultTextSize) {
        return array.getDimension(strokeAttribute, defaultTextSize);
    }

    private int getHeight(int timelineView_edgeHeight, int defaultValue) {
        return array.getInt(timelineView_edgeHeight, defaultValue);
    }

    Paint getBaselinePaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_baselineColor, Color.RED));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_baselineStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getEdgeSerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_edgeSerifColor, Color.YELLOW));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_edgeSerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainSerifColor, Color.GREEN));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_mainSerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getSecondarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_secondarySerifColor, Color.LTGRAY));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_secondarySerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainTextColor, Color.GREEN));
        paint.setTextSize(getTextSize(R.styleable.TimelineView_mainTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }

    Paint getSecondaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainTextColor, Color.LTGRAY));
        paint.setTextSize(getTextSize(R.styleable.TimelineView_mainTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }

    int getEdgeHeight() {
        return getHeight(R.styleable.TimelineView_edgeHeight, DEFAULT_SERIF_HEIGHT);
    }

    int getPrimarySerifHeight() {
        return getHeight(R.styleable.TimelineView_primarySerifHeight, DEFAULT_SERIF_HEIGHT);
    }

    int getSecondarySerifHeight() {
        return getHeight(R.styleable.TimelineView_secondarySerifHeight, DEFAULT_SERIF_HEIGHT);
    }
}
