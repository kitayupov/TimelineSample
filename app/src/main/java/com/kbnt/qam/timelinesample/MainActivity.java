package com.kbnt.qam.timelinesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kbnt.qam.timeline.TimelineView;
import com.kbnt.qam.timeline.episode.Channel;
import com.kbnt.qam.timeline.episode.Episode;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final DateTime START = DateTime.now().minusYears(10).withDayOfYear(1).withMillisOfDay(0);
    private static final long FIRST_START = 200000000000L;
    private static final float DURATION = 2000000.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Channel> channels = new ArrayList<>();

        final ArrayList<Episode> episodes1 = new ArrayList<>();
        episodes1.add(new Episode(FIRST_START, DURATION));
        for (int i = 0; i < 50; i++) {
            episodes1.add(new Episode(START.plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(episodes1));

        final ArrayList<Episode> episodes2 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            episodes2.add(new Episode(START.minusYears(3).plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(episodes2));

        final ArrayList<Episode> episodes3 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            episodes3.add(new Episode(START.minusYears(6).plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(episodes3));

        final TimelineView timelineView = findViewById(R.id.timeline);
        timelineView.setOnEpisodeClickListener(new TimelineView.OnEpisodeClickListener() {
            @Override
            public void onClick(Episode episode, DateTime dateTime) {
                System.out.println(episode);
                System.out.println(dateTime);
            }
        });
        timelineView.setChannels(channels);
    }
}
