package com.kbnt.qam.timeline.date;

import org.joda.time.DateTime;

public class DateSegment {

    public final DateTime start;
    public final DateTime stop;

    public DateSegment(DateTime start, DateTime stop) {
        this.start = start;
        this.stop = stop;
    }

    public DateSegment(long start, long stop) {
        this.start = new DateTime(start);
        this.stop = new DateTime(stop);
    }
}
