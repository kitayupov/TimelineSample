package com.kbnt.qam.timelinesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kbnt.qam.timeline.Timeline;
import com.kbnt.qam.timeline.channel.Channel;
import com.kbnt.qam.timeline.channel.Track;
import com.kbnt.qam.timeline.detector.EventDetector;

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

        final ArrayList<Track> tracks1 = new ArrayList<>();
        tracks1.add(new Track(FIRST_START, DURATION));
        for (int i = 0; i < 50; i++) {
            tracks1.add(new Track(START.plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(tracks1));

        final ArrayList<Track> tracks2 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tracks2.add(new Track(START.minusYears(3).plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(tracks2));

        final ArrayList<Track> tracks3 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tracks3.add(new Track(START.minusYears(6).plusMonths(i).getMillis(), DURATION));
        }
        channels.add(new Channel(tracks3));

        final Timeline timeline = findViewById(R.id.timeline);
        timeline.setOnTrackClickListener(new EventDetector.OnTrackClickListener() {
            @Override
            public void onTrackClick(Track track, DateTime dateTime) {
                System.out.println(track);
                System.out.println(dateTime);
            }

            @Override
            public void onFailure(float x, float y, String message) {
                System.out.println(message);
            }
        });
        timeline.setChannels(channels);
    }
}
