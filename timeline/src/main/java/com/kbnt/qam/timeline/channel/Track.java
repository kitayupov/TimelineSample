package com.kbnt.qam.timeline.channel;

import com.kbnt.qam.timeline.date.DateSegment;

import org.joda.time.DateTime;

public class Track {

    private DateSegment dateSegment;

    public Track(long date, float duration) {
        final DateTime start = new DateTime(date);
        final DateTime stop = start.plus((long) (duration * 1000));
        dateSegment = new DateSegment(start, stop);
    }

    public boolean contains(DateTime dateTime) {
        return dateSegment.contains(dateTime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "start=" + dateSegment.start +
                ", stop=" + dateSegment.stop +
                '}';
    }

    public long getStart() {
        return dateSegment.start.getMillis();
    }

    public long getStop() {
        return dateSegment.stop.getMillis();
    }
}
