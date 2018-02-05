package com.kbnt.qam.timeline.helpers;

import android.view.ScaleGestureDetector;

import com.kbnt.qam.timeline.TimelineView;
import com.kbnt.qam.timeline.date.TimeInterval;

import org.joda.time.DateTime;

public class IntervalHelper {

    private static final int MAX_COUNT = 20;
    private static final float MIN_SCALE_FACTOR = 1.F;
    private static float MAX_SCALE_FACTOR;

    private float scaleFactor = MIN_SCALE_FACTOR;
    private long dateX = 0;

    private TimeInterval interval;
    private TimelineView view;

    public IntervalHelper(TimelineView view) {
        this.view = view;
        interval = TimeInterval.getInstance();
        interval.setMaxCount(MAX_COUNT);
        MAX_SCALE_FACTOR = interval.getMaxFactor();
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setInterval(long start, long stop) {
        interval.setInterval(start, stop);
        MAX_SCALE_FACTOR = interval.getMaxFactor();
    }

    public long getDuration() {
        return interval.getInterval();
    }

    public DateTime getStart() {
        return interval.getStart();
    }

    public DateTime getStop() {
        return interval.getStop();
    }

    public TimeInterval.TimePeriod getCountPeriod() {
        return interval.getCountPeriod(scaleFactor);
    }

    public long getDateX() {
        return dateX;
    }

    public void scale(ScaleGestureDetector detector) {
        final float scale = detector.getScaleFactor();
        scaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(scaleFactor * scale, MAX_SCALE_FACTOR));
        if (scaleFactor > MIN_SCALE_FACTOR && scaleFactor < MAX_SCALE_FACTOR) {
            final long date = view.getDate(detector.getFocusX());
            float diff = date - dateX;
            diff = diff / scale - diff;
            dateX -= diff;
        }
        normalizeDate();
    }

    public void scroll(float distanceX) {
        dateX = view.getDate(distanceX);
        normalizeDate();
    }

    private void normalizeDate() {
        dateX = (long) Math.max(0, Math.min(dateX, getDuration() - getDuration() / scaleFactor));
    }
}
