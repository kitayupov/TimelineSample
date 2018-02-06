package com.kbnt.qam.timeline.helpers;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class PaintsHelper {

    public final Paint baseline;
    public final Paint edgeSerif;
    public final Paint primarySerif;
    public final Paint secondarySerif;
    public final Paint primaryText;
    public final Paint secondaryText;
    public final Paint trackPaint;
    public final Paint cursorPaint;

    public final int edgeHeight;
    public final int primarySerifHeight;
    public final int secondarySerifHeight;

    public final int primaryTextMargin;
    private final int secondaryTextMargin;

    public PaintsHelper(Context context, AttributeSet attrs) {
        final AttributesHolder attributes = new AttributesHolder(context, attrs);
        baseline = attributes.getBaselinePaint();
        edgeSerif = attributes.getEdgeSerifPaint();
        primarySerif = attributes.getPrimarySerifPaint();
        secondarySerif = attributes.getSecondarySerifPaint();
        primaryText = attributes.getPrimaryTextPaint();
        secondaryText = attributes.getSecondaryTextPaint();
        edgeHeight = attributes.getEdgeHeight();
        primarySerifHeight = attributes.getPrimarySerifHeight();
        secondarySerifHeight = attributes.getSecondarySerifHeight();
        trackPaint = attributes.getTrackPaint();
        cursorPaint = attributes.getCursorPaint();
        primaryTextMargin = attributes.getPrimaryTextMargin();
        secondaryTextMargin = attributes.getSecondaryTextMargin();
    }

    public int getTotalHeight() {
        return getTopHeight() + getBottomHeight();
    }

    private int getTopHeight() {
        return Math.max(getPrimaryTextHeight(), Math.max(primarySerifHeight, edgeHeight));
    }

    int getBottomHeight() {
        return Math.max(getSecondaryTextHeight(), Math.max(secondarySerifHeight, edgeHeight));
    }

    private int getPrimaryTextHeight() {
        final Rect bounds = new Rect();
        primaryText.getTextBounds("1970", 0, 1, bounds);
        return bounds.height() + primaryTextMargin;
    }

    public int getSecondaryTextHeight() {
        final Rect bounds = new Rect();
        secondaryText.getTextBounds("1970", 0, 1, bounds);
        return bounds.height() + secondaryTextMargin;
    }
}
