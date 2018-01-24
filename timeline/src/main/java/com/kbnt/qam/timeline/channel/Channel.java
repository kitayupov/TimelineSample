package com.kbnt.qam.timeline.channel;

import java.util.List;

public class Channel {

    private List<? extends Track> tracks;

    public Channel(List<? extends Track> tracks) {
        this.tracks = tracks;
    }

    public List<? extends Track> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "tracks=" + tracks +
                '}';
    }
}
