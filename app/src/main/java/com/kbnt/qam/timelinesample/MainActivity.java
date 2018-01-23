package com.kbnt.qam.timelinesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kbnt.qam.timeline.TimelineView;
import com.kbnt.qam.timeline.episode.Channel;
import com.kbnt.qam.timeline.episode.Episode;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final DateTime START = DateTime.now().minusYears(10).minusMonths(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Channel> channels = new ArrayList<>();

        final ArrayList<Episode> episodes1 = new ArrayList<>();
        episodes1.add(new Episode(200000000000L, 2000000.0F));
        for (int i = 0; i < 50; i++) {
            episodes1.add(new Episode(START.plusMonths(i).getMillis(), 2000000.0F));
        }
        channels.add(new Channel(episodes1));

        final ArrayList<Episode> episodes2 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            episodes2.add(new Episode(START.minusYears(3).plusMonths(i).getMillis(), 2000000.0F));
        }
        channels.add(new Channel(episodes2));

        final ArrayList<Episode> episodes3 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            episodes3.add(new Episode(START.minusYears(6).plusMonths(i).getMillis(), 2000000.0F));
        }
        channels.add(new Channel(episodes3));

        final TimelineView timelineView = findViewById(R.id.timeline);
        timelineView.setChannels(channels);
    }
}
