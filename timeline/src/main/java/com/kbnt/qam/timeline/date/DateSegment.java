package com.kbnt.qam.timeline.date;

import org.joda.time.DateTime;

public class DateSegment {

    public DateTime start;
    public DateTime stop;

    DateSegment() {
        this.start = new DateTime(0);
        this.stop = new DateTime();
    }

    public DateSegment(DateTime start, DateTime stop) {
        this.start = start;
        this.stop = stop;
    }

    DateTime getStart() {
        return start;
    }

    void setStart(DateTime start) {
        this.start = start;
    }

    DateTime getStop() {
        return stop;
    }

    void setStop(DateTime stop) {
        this.stop = stop;
    }

    long getInterval() {
        return stop.getMillis() - start.getMillis();
    }

    public boolean contains(DateTime dateTime) {
        return dateTime.getMillis() >= start.getMillis() &&
                dateTime.getMillis() <= stop.getMillis();
    }
}
