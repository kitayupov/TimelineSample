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
    private static final float DEFAULT_TEXT_MARGIN = 8.0F;

    private static final float DEFAULT_CHANNELS_MARGIN_TOP = 8.0F;
    private static final float DEFAULT_CHANNEL_HEIGHT = 16.0F;
    private static final float DEFAULT_CHANNEL_MARGIN = 8.0F;

    private final TypedArray array;

    AttributesHolder(Context context, AttributeSet attrs) {
        array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Timeline, 0, R.style.TimelineWidget);
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
        paint.setColor(getColor(R.styleable.Timeline_baselineColor, Color.RED));
        paint.setStrokeWidth(getStroke(R.styleable.Timeline_baselineStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getEdgeSerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_edgeSerifColor, Color.YELLOW));
        paint.setStrokeWidth(getStroke(R.styleable.Timeline_edgeSerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_primarySerifColor, Color.GREEN));
        paint.setStrokeWidth(getStroke(R.styleable.Timeline_primarySerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getSecondarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_secondarySerifColor, Color.LTGRAY));
        paint.setStrokeWidth(getStroke(R.styleable.Timeline_secondarySerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_primaryTextColor, Color.GREEN));
        paint.setTextSize(getTextSize(R.styleable.Timeline_primaryTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }

    Paint getSecondaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_secondaryTextColor, Color.LTGRAY));
        paint.setTextSize(getTextSize(R.styleable.Timeline_secondaryTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }

    int getEdgePrimaryHeight(int primaryTextHeight) {
        return getHeight(R.styleable.Timeline_edgePrimaryHeight, primaryTextHeight);
    }

    int getEdgeSecondaryHeight(int secondaryTextHeight) {
        return getHeight(R.styleable.Timeline_edgeSecondaryHeight, secondaryTextHeight);
    }

    int getPrimarySerifHeight(int primaryTextHeight) {
        return getHeight(R.styleable.Timeline_primarySerifHeight, primaryTextHeight);
    }

    int getSecondarySerifHeight(int secondaryTextHeight) {
        return getHeight(R.styleable.Timeline_secondarySerifHeight, secondaryTextHeight);
    }

    Paint getTrackPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_trackColor, Color.YELLOW));
        return paint;
    }

    Paint getCursorPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.Timeline_cursorColor, Color.RED));
        paint.setStrokeWidth(getStroke(R.styleable.Timeline_cursorWidth, DEFAULT_STROKE));
        return paint;
    }

    int getPrimaryTextMargin() {
        return getHeight(R.styleable.Timeline_primaryTextMargin, DEFAULT_TEXT_MARGIN);
    }

    int getSecondaryTextMargin() {
        return getHeight(R.styleable.Timeline_secondaryTextMargin, DEFAULT_TEXT_MARGIN);
    }

    int getChannelsMarginTop() {
        return getHeight(R.styleable.Timeline_channelsMarginTop, DEFAULT_CHANNELS_MARGIN_TOP);
    }

    int getChannelHeight() {
        return getHeight(R.styleable.Timeline_channelHeight, DEFAULT_CHANNEL_HEIGHT);
    }

    int getChannelMargin() {
        return getHeight(R.styleable.Timeline_channelMargin, DEFAULT_CHANNEL_MARGIN);
    }
}
