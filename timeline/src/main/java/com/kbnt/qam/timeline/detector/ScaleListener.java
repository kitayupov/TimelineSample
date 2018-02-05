package com.kbnt.qam.timeline.detector;

import android.view.ScaleGestureDetector;
import android.view.View;

import com.kbnt.qam.timeline.helpers.IntervalHelper;

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private View view;
    private IntervalHelper interval;

    ScaleListener(View view, IntervalHelper interval) {
        this.view = view;
        this.interval = interval;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        interval.scale(detector);
        view.invalidate();
        return true;
    }
}