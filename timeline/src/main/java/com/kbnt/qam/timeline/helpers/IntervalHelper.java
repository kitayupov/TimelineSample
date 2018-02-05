package com.kbnt.qam.timeline.helpers;

import com.kbnt.qam.timeline.date.TimeInterval;

import org.joda.time.DateTime;

public class IntervalHelper {

    private static final int MAX_COUNT = 20;
    private static final float MIN_SCALE_FACTOR = 1.F;
    private static float MAX_SCALE_FACTOR;

    private float scaleFactor = MIN_SCALE_FACTOR;

    private TimeInterval interval;

    public IntervalHelper() {
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

    public boolean enlargeScaleFactor(float scale) {
        scaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(scaleFactor * scale, MAX_SCALE_FACTOR));
        return scaleFactor > MIN_SCALE_FACTOR && scaleFactor < MAX_SCALE_FACTOR;
    }
}
