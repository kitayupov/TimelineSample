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

    private final TypedArray array;

    AttributesHolder(Context context, AttributeSet attrs) {
        array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimelineView, 0, 0);
    }

    private int getColor(int colorAttribute, int red) {
        return array.getColor(colorAttribute, red);
    }

    private float getDimension(int strokeAttribute, float defaultStroke) {
        return array.getDimension(strokeAttribute, defaultStroke);
    }

    Paint getBaselinePaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_baselineColor, Color.RED));
        paint.setStrokeWidth(getDimension(R.styleable.TimelineView_baselineStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getEdgeSerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_edgeSerifColor, Color.YELLOW));
        paint.setStrokeWidth(getDimension(R.styleable.TimelineView_edgeSerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainSerifColor, Color.GREEN));
        paint.setStrokeWidth(getDimension(R.styleable.TimelineView_mainSerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getSecondarySerifPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_secondarySerifColor, Color.LTGRAY));
        paint.setStrokeWidth(getDimension(R.styleable.TimelineView_secondarySerifStroke, DEFAULT_STROKE));
        return paint;
    }

    Paint getPrimaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainTextColor, Color.GREEN));
        paint.setTextSize(getDimension(R.styleable.TimelineView_mainTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }

    Paint getSecondaryTextPaint() {
        final Paint paint = new Paint();
        paint.setColor(getColor(R.styleable.TimelineView_mainTextColor, Color.LTGRAY));
        paint.setTextSize(getDimension(R.styleable.TimelineView_mainTextSize, DEFAULT_TEXT_SIZE));
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }
}
