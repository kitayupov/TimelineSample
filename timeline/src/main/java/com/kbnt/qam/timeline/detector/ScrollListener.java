package com.kbnt.qam.timeline.detector;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.kbnt.qam.timeline.helpers.IntervalHelper;

public class ScrollListener extends GestureDetector.SimpleOnGestureListener {

    private View view;
    private IntervalHelper interval;

    ScrollListener(View view, IntervalHelper interval) {
        this.view = view;
        this.interval = interval;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        interval.scroll(distanceX);
        view.invalidate();
        return super.onScroll(e1, e2, distanceX, distanceY);
    }
}
