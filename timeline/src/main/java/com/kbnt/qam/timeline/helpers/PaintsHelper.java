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

    public final int edgePrimaryHeight;
    public final int edgeSecondaryHeight;
    public final int primarySerifHeight;
    public final int secondarySerifHeight;

    public final int primaryTextMargin;
    private final int secondaryTextMargin;

    final int channelsMarginTop;
    final int channelHeight;
    final int channelMargin;

    public PaintsHelper(Context context, AttributeSet attrs) {
        final AttributesHolder attributes = new AttributesHolder(context, attrs);
        baseline = attributes.getBaselinePaint();
        edgeSerif = attributes.getEdgeSerifPaint();
        primarySerif = attributes.getPrimarySerifPaint();
        secondarySerif = attributes.getSecondarySerifPaint();
        primaryText = attributes.getPrimaryTextPaint();
        secondaryText = attributes.getSecondaryTextPaint();
        primaryTextMargin = attributes.getPrimaryTextMargin();
        secondaryTextMargin = attributes.getSecondaryTextMargin();
        edgePrimaryHeight = attributes.getEdgePrimaryHeight(getPrimaryTextHeight());
        edgeSecondaryHeight = attributes.getEdgeSecondaryHeight(getSecondaryTextHeight());
        primarySerifHeight = attributes.getPrimarySerifHeight(getPrimaryTextHeight());
        secondarySerifHeight = attributes.getSecondarySerifHeight(getSecondaryTextHeight());
        trackPaint = attributes.getTrackPaint();
        cursorPaint = attributes.getCursorPaint();
        channelsMarginTop = attributes.getChannelsMarginTop();
        channelHeight = attributes.getChannelHeight();
        channelMargin = attributes.getChannelMargin();
    }

    public int getTotalHeight() {
        return getTopHeight() + getBottomHeight();
    }

    private int getTopHeight() {
        return Math.max(getPrimaryTextHeight(), Math.max(primarySerifHeight, edgePrimaryHeight));
    }

    int getBottomHeight() {
        return Math.max(getSecondaryTextHeight(), Math.max(secondarySerifHeight, edgeSecondaryHeight));
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
