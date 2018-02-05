package com.kbnt.qam.timeline.helpers;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

public class PaintsHelper {

    public final Paint baseline;
    public final Paint edgeSerif;
    public final Paint primarySerif;
    public final Paint secondarySerif;
    public final Paint primaryText;
    public final Paint secondaryText;

    public PaintsHelper(Context context, AttributeSet attrs) {

        final AttributesHolder attributes = new AttributesHolder(context, attrs);

        baseline = attributes.getBaselinePaint();
        edgeSerif = attributes.getEdgeSerifPaint();
        primarySerif = attributes.getPrimarySerifPaint();
        secondarySerif = attributes.getSecondarySerifPaint();
        primaryText = attributes.getPrimaryTextPaint();
        secondaryText = attributes.getSecondaryTextPaint();
    }
}
