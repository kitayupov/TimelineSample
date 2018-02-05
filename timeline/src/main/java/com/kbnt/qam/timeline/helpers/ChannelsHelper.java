package com.kbnt.qam.timeline.helpers;

import com.kbnt.qam.timeline.channel.Channel;

import java.util.ArrayList;

public class ChannelsHelper {

    private static final int TRACKS_MARGIN_TOP = 40;
    private static final int TRACK_HEIGHT = 30;
    private static final int TRACK_MARGIN = 20;

    private ArrayList<Channel> channels;

    public ChannelsHelper() {
        channels = new ArrayList<>();
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public int getHeight() {
        return TRACKS_MARGIN_TOP + (TRACK_MARGIN + TRACK_HEIGHT) * (channels.size() - 1);
    }

    public int size() {
        return channels.size();
    }

    public int getChannelTop(int index) {
        return TRACKS_MARGIN_TOP + (TRACK_MARGIN + TRACK_HEIGHT) * index;
    }

    public int getChannelBottom(int index) {
        return getChannelTop(index) + TRACK_HEIGHT;
    }

    public Channel get(int index) {
        return channels.get(index);
    }
}
