package com.kbnt.qam.timeline.channel;

import java.util.ArrayList;

public class Channel {

    private ArrayList<Track> tracks;

    public Channel(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "tracks=" + tracks +
                '}';
    }
}
