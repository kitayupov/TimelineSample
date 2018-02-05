package com.kbnt.qam.timeline.detectors;


import android.view.MotionEvent;

import com.kbnt.qam.timeline.TimelineView;
import com.kbnt.qam.timeline.channel.Channel;
import com.kbnt.qam.timeline.channel.Track;
import com.kbnt.qam.timeline.parameters.DrawChannels;
import com.kbnt.qam.timeline.parameters.IntervalHelper;

import org.joda.time.DateTime;

import java.util.Calendar;

public class EventDetector {

    private static final int CLICK_ACTION_THRESHOLD = 200;
    private static final int MOVE_ACTION_THRESHOLD = 10;

    private long startClickTime;
    private float startMove;

    private OnTrackClickListener mOnTrackClickListener;

    private DateTime clickedDate;
    private Track clickedTrack;

    private TimelineView view;
    private IntervalHelper interval;
    private DrawChannels channels;

    public EventDetector(TimelineView view, IntervalHelper interval, DrawChannels channels) {
        this.view = view;
        this.interval = interval;
        this.channels = channels;
    }

    public DateTime getClickedDate() {
        return clickedDate;
    }

    public Track getClickedTrack() {
        return clickedTrack;
    }

    public void onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startClickTime = Calendar.getInstance().getTimeInMillis();
                startMove = x;
                break;
            case MotionEvent.ACTION_UP:
                final long duration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (duration < CLICK_ACTION_THRESHOLD) {
                    performClick(x, y);
                    view.invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float distance = Math.abs(x - startMove);
                if (distance > MOVE_ACTION_THRESHOLD) {
                    startClickTime = 0;
                }
                break;
        }
    }

    private void performClick(float x, float y) {
        final Channel channel = getChannel(y - getRelativeTop());
        if (channel != null) {
            final long datePoint = view.getDate(x - view.getPaddingStart());
            clickedDate = interval.getStart().plus(datePoint);
            clickedTrack = getTrack(channel, clickedDate);
            if (clickedTrack != null) {
                if (mOnTrackClickListener != null) {
                    mOnTrackClickListener.onTrackClick(clickedTrack, clickedDate);
                    return;
                }
            } else if (mOnTrackClickListener != null) {
                mOnTrackClickListener.onFailure(x, y, "no clickedTrack found in this point");
            }
        } else if (mOnTrackClickListener != null) {
            mOnTrackClickListener.onFailure(x, y, "no channel found in the point");
        }
        clickedDate = null;
        clickedTrack = null;
    }

    private int getRelativeTop() {
        final int height = channels.getHeight();
        return (view.getHeight() - height) / 2;
    }

    private Channel getChannel(float y) {
        for (int index = 0; index < channels.size(); index++) {
            final int top = channels.getChannelTop(index);
            final int bottom = channels.getChannelBottom(index);
            if (y + 10 >= top && y - 10 <= bottom) {
                return channels.get(index);
            }
        }
        return null;
    }

    private Track getTrack(Channel channel, DateTime date) {
        for (Track track : channel.getTracks()) {
            if (track.contains(date)) {
                return track;
            }
        }
        return null;
    }

    public void setOnTrackClickListener(OnTrackClickListener onTrackClickListener) {
        this.mOnTrackClickListener = onTrackClickListener;
    }

    public interface OnTrackClickListener {
        void onTrackClick(Track track, DateTime dateTime);

        void onFailure(float x, float y, String message);
    }
}
