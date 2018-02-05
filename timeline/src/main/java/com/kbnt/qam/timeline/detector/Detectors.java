package com.kbnt.qam.timeline.detector;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.kbnt.qam.timeline.TimelineView;
import com.kbnt.qam.timeline.channel.Track;
import com.kbnt.qam.timeline.helpers.ChannelsHelper;
import com.kbnt.qam.timeline.helpers.IntervalHelper;

import org.joda.time.DateTime;

public class Detectors {

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mScrollDetector;
    private EventDetector mEventDetector;

    public Detectors(TimelineView view, IntervalHelper interval, ChannelsHelper channels) {
        mScaleDetector = new ScaleGestureDetector(view.getContext(), new ScaleListener(view, interval));
        mScrollDetector = new GestureDetector(view.getContext(), new ScrollListener(view, interval));
        mEventDetector = new EventDetector(view, interval, channels);
    }

    public void onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mScrollDetector.onTouchEvent(event);
        mEventDetector.onTouchEvent(event);
    }

    public DateTime getClickedDate() {
        return mEventDetector.getClickedDate();
    }

    public Track getClickedTrack() {
        return mEventDetector.getClickedTrack();
    }

    public void setOnTrackClickListener(EventDetector.OnTrackClickListener onTrackClickListener) {
        mEventDetector.setOnTrackClickListener(onTrackClickListener);
    }
}
