package com.kbnt.qam.timeline.channel;

import org.joda.time.DateTime;

public class Track {

    public final long start;
    public final long stop;

    public Track(long start, float duration) {
        this.start = start;
        this.stop = start + (long) (duration * 1000);
    }

    public boolean contains(DateTime dateTime) {
        return dateTime.getMillis() >= start && dateTime.getMillis() <= stop;
    }

    @Override
    public String toString() {
        return "Track{" +
                "start=" + new DateTime(start) +
                ", stop=" + new DateTime(stop) +
                '}';
    }
}
