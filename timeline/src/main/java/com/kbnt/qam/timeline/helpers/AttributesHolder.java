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
    private static final float DEFAULT_SERIF_HEIGHT = 20.0F;
    private static final float DEFAULT_TEXT_MARGIN = 8.0F;

    private static final float DEFAULT_CHANNELS_MARGIN_TOP = 15.0F;
    private static final float DEFAULT_CHANNEL_HEIGHT = 20.0F;
    private static final float DEFAULT_CHANNEL_MARGIN = 10.0F;

    private final TypedArray array;

    AttributesHolder(Context context, AttributeSet attrs) {
        array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimelineView, 0, 0);
    }

    private int getColor(int colorAttribute, int defaultColor) {
        return array.getColor(colorAttribute, defaultColor);
    }

    private float getStroke(int strokeAttribute, float defaultStroke) {
        return array.getDimension(strokeAttribute, defaultStroke);
    }


    private float getTextSize(int strokeAttribute, float defaultTextSize) {
        return array.getDimension(strokeAttribute, defaultTextSize);
    }

    private int getHeight(int heightAttribute, float defaultValue) {
        return (int) array.getDimension(heightAttribute, defaultValue);
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
        paint.setColor(getColor(R.styleable.TimelineView_primarySerifColor, Color.GREEN));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_primarySerifStroke, DEFAULT_STROKE));
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
        paint.setColor(getColor(R.styleable.TimelineView_primaryTextColor, Color.GREEN));
        paint.setTextSize(getTextSize(R.styleable.TimelineView_primaryTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }

    Paint getSecondaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_secondaryTextColor, Color.LTGRAY));
        paint.setTextSize(getTextSize(R.styleable.TimelineView_secondaryTextSize, DEFAULT_TEXT_SIZE));
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

    Paint getTrackPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_trackColor, Color.YELLOW));
        return paint;
    }

    Paint getCursorPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_cursorColor, Color.RED));
        paint.setStrokeWidth(getStroke(R.styleable.TimelineView_cursorWidth, DEFAULT_STROKE));
        return paint;
    }

    int getPrimaryTextMargin() {
        return getHeight(R.styleable.TimelineView_primaryTextMargin, DEFAULT_TEXT_MARGIN);
    }

    int getSecondaryTextMargin() {
        return getHeight(R.styleable.TimelineView_secondaryTextMargin, DEFAULT_TEXT_MARGIN);
    }

    int getChannelsMarginTop() {
        return getHeight(R.styleable.TimelineView_channelsMarginTop, DEFAULT_CHANNELS_MARGIN_TOP);
    }

    int getChannelHeight() {
        return getHeight(R.styleable.TimelineView_channelHeight, DEFAULT_CHANNEL_HEIGHT);
    }

    int getChannelMargin() {
        return getHeight(R.styleable.TimelineView_channelMargin, DEFAULT_CHANNEL_MARGIN);
    }
}
