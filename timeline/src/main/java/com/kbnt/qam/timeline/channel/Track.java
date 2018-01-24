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

    public long getStart() {
        return dateSegment.start.getMillis();
    }

    public long getStop() {
        return dateSegment.stop.getMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        return dateSegment != null ? dateSegment.equals(track.dateSegment) : track.dateSegment == null;
    }

    @Override
    public int hashCode() {
        return dateSegment != null ? dateSegment.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Track{" +
                "start=" + dateSegment.start +
                ", stop=" + dateSegment.stop +
                '}';
    }
}
