package com.kbnt.qam.timeline.helpers;

import com.kbnt.qam.timeline.channel.Channel;

import java.util.ArrayList;

public class ChannelsHelper {

    private final int channelsMarginTop;
    private final int channelHeight;
    private final int channelMargin;

    private ArrayList<Channel> channels;

    public ChannelsHelper(PaintsHelper paints) {
        channelsMarginTop = paints.getBottomHeight() + paints.channelsMarginTop;
        channelHeight = paints.channelHeight;
        channelMargin = paints.channelMargin;
        channels = new ArrayList<>();
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public int getHeight() {
        return channelsMarginTop + channelHeight * channels.size() + channelMargin * (channels.size() - 1);
    }

    public int size() {
        return channels.size();
    }

    public int getChannelTop(int index) {
        return channelsMarginTop + (channelMargin + channelHeight) * index;
    }

    public int getChannelBottom(int index) {
        return getChannelTop(index) + channelHeight;
    }

    public Channel get(int index) {
        return channels.get(index);
    }
}
