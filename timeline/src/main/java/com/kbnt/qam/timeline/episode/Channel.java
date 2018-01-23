package com.kbnt.qam.timeline.episode;

import java.util.ArrayList;

public class Channel {

    private ArrayList<Episode> episodes;

    public Channel(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
}
