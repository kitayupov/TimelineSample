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

    void setStart(long start) {
        this.start = new DateTime(start);
    }

    void setStop(long stop) {
        this.stop = new DateTime(stop);
    }

    long getInterval() {
        return stop.getMillis() - start.getMillis();
    }

    public boolean contains(DateTime dateTime) {
        return dateTime.getMillis() >= start.getMillis() &&
                dateTime.getMillis() <= stop.getMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateSegment that = (DateSegment) o;

        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return stop != null ? stop.equals(that.stop) : that.stop == null;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (stop != null ? stop.hashCode() : 0);
        return result;
    }
}
