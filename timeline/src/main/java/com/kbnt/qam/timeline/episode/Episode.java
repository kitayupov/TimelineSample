package com.kbnt.qam.timeline.episode;

import org.joda.time.DateTime;

public class Episode {

    public final long start;
    public final long stop;

    public Episode(long start, float duration) {
        this.start = start;
        this.stop = start + (long) (duration * 1000);
    }

    public boolean contains(DateTime dateTime) {
        return dateTime.getMillis() >= start && dateTime.getMillis() <= stop;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "start=" + new DateTime(start) +
                ", stop=" + new DateTime(stop) +
                '}';
    }
}
