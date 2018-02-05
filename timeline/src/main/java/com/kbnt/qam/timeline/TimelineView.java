package com.kbnt.qam.timeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.kbnt.qam.timeline.channel.Channel;
import com.kbnt.qam.timeline.channel.Track;
import com.kbnt.qam.timeline.date.DateSegment;
import com.kbnt.qam.timeline.date.DateTimeUtils;
import com.kbnt.qam.timeline.date.TimeInterval;
import com.kbnt.qam.timeline.detectors.EventDetector;
import com.kbnt.qam.timeline.helpers.ChannelsHelper;
import com.kbnt.qam.timeline.helpers.IntervalHelper;
import com.kbnt.qam.timeline.helpers.PaintsHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class TimelineView extends View {

    private PaintsHelper paints;
    private ChannelsHelper channels;
    private IntervalHelper interval;

    private long dateX = 0;

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mScrollDetector;
    private EventDetector mEventDetector;

    public TimelineView(Context context) {
        this(context, null);
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels.setChannels(channels);
        long start = System.currentTimeMillis();
        long stop = 0;
        for (Channel channel : channels) {
            for (Track track : channel.getTracks()) {
                start = Math.min(start, track.getStart());
                stop = Math.max(stop, track.getStop());
            }
        }
        interval.setInterval(start, stop);
        setMeasuredDimension(getWidth(), calculateHeight());
        invalidate();
    }

    private void init() {
        paints = new PaintsHelper();
        channels = new ChannelsHelper();
        interval = new IntervalHelper();

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mScrollDetector = new GestureDetector(getContext(), new ScrollListener());
        mEventDetector = new EventDetector(this, interval, channels);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measuredHeight = calculateHeight();
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int calculateHeight() {
        return 50 + channels.getHeight() + getPaddingTop() + getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final long duration = interval.getDuration();
        final float scaleFactor = interval.getScaleFactor();
        dateX = (long) Math.max(0, Math.min(dateX, duration - duration / scaleFactor));

        canvas.save();
        canvas.translate(getPaddingStart(), getRelativeTop());
        drawTimelineBar(canvas);
        drawChannels(canvas);
        canvas.restore();
    }

    private int getRelativeTop() {
        final int height = channels.getHeight();
        return (getHeight() - height) / 2;
    }

    private void drawTimelineBar(Canvas canvas) {
        canvas.drawLine(0, 0, getTotalWidth(), 0, paints.line);
        drawSegments(canvas);
    }

    private void drawChannels(Canvas canvas) {
        for (int i = 0; i < channels.size(); i++) {
            drawTracks(canvas, i);
        }
    }

    private void drawTracks(Canvas canvas, int index) {
        for (Track track : channels.get(index).getTracks()) {
            final long start = track.getStart();
            final long stop = track.getStop();
            final float startX = Math.max(0, getPoint(new DateTime(start)));
            final float stopX = Math.min(getTotalWidth(), getPoint(new DateTime(stop)));

            if (startX < stopX) {
                final int top = channels.getChannelTop(index);
                final int bottom = channels.getChannelBottom(index);
                canvas.drawRect(startX, top, stopX, bottom, paints.edgeSerif);

                final DateTime clickedDate = mEventDetector.getClickedDate();
                final Track clickedTrack = mEventDetector.getClickedTrack();
                if (track.equals(clickedTrack) && clickedDate != null) {
                    final float point = getPoint(clickedDate);
                    if (point >= 0 && point <= getTotalWidth()) {
                        canvas.drawLine(point, top, point, bottom, paints.line);
                    }
                }
            }
        }
    }

    private void drawSegments(Canvas canvas) {
        final TimeInterval.TimePeriod secondaryPeriod = interval.getCountPeriod();
        final TimeInterval.TimePeriod mainPeriod = secondaryPeriod.previous();
        final DateTime start = interval.getStart().plus(getDate(0));
        final DateTime stop = interval.getStart().plus(getDate(getTotalWidth()));
        final List<DateSegment> segments = getSegments(new DateSegment(start, stop), mainPeriod);
        for (DateSegment segment : segments) {
            // Draws line serif line
            drawMainSerifLine(canvas, segment);
            // Draws line serif text
            drawMainSerifText(canvas, secondaryPeriod, segment);
            // Draws secondary serifs
            drawSecondarySerifs(canvas, secondaryPeriod, segment);
        }
        drawMainEdges(canvas);
    }

    private List<DateSegment> getSegments(DateSegment segment, TimeInterval.TimePeriod period) {
        final List<DateSegment> segments = new ArrayList<>();
        DateTime date = TimeInterval.getNormalizedDate(period, segment.start);
        while (date.getMillis() < segment.stop.getMillis()) {
            final DateTime nextDate = TimeInterval.getNext(period, date);
            segments.add(new DateSegment(date, nextDate));
            date = nextDate;
        }
        return segments;
    }

    private void drawMainEdges(Canvas canvas) {
        final DateTime start = interval.getStart();
        final float left = getPoint(start);
        if (left >= 0) {
            drawSerif(canvas, start, 20, 20, paints.edgeSerif);
        }
        final DateTime stop = interval.getStop();
        final float right = getPoint(stop);
        if (right <= getTotalWidth()) {
            drawSerif(canvas, stop, 20, 20, paints.edgeSerif);
        }
    }

    private void drawMainSerifLine(Canvas canvas, DateSegment segment) {
        final DateTime start = segment.start;
        final float left = getPoint(start);
        if (left >= 0 && left <= getTotalWidth()) {
            drawSerif(canvas, start, 20, 0, paints.mainSerif);
        }
    }

    private void drawMainSerifText(Canvas canvas, TimeInterval.TimePeriod secondaryPeriod, DateSegment mainSegment) {
        final DateTime start = mainSegment.start;
        final DateTime stop = mainSegment.stop;
        final String text = DateTimeUtils.getUpString(start, secondaryPeriod);
        final float left = Math.max(0, getPoint(start));
        final float right = Math.min(getPoint(stop), getTotalWidth());
        final float middle = (left + right) / 2;
        if (middle >= (paints.mainText.measureText(text) + 10) / 2 &&
                middle <= getTotalWidth() - (paints.mainText.measureText(text) + 10) / 2) {
            canvas.drawText(text, middle, -10, paints.mainText);
        }
    }

    private void drawSecondarySerifs(Canvas canvas, TimeInterval.TimePeriod secondaryPeriod, DateSegment mainSegment) {
        final List<DateSegment> secondarySegments = getSegments(mainSegment, secondaryPeriod);
        for (DateSegment segment : secondarySegments) {
            if (getPoint(segment.stop) >= 0 && getPoint(segment.start) <= getTotalWidth()) {
                // Draws secondary serif line
                drawSecondarySerifLine(canvas, segment);
                // Draws secondary serif text
                drawSecondarySerifText(canvas, secondaryPeriod, segment);
            }
        }
    }

    private void drawSecondarySerifLine(Canvas canvas, DateSegment segment) {
        final DateTime start = segment.start;
        final float left = getPoint(start);
        if (left >= 0 && left <= getTotalWidth()) {
            drawSerif(canvas, start, 0, 15, paints.secondarySerif);
        }
    }

    private void drawSecondarySerifText(Canvas canvas, TimeInterval.TimePeriod secondaryPeriod, DateSegment segment) {
        final DateTime start = segment.start;
        final DateTime stop = segment.stop;
        final float left = Math.max(0, getPoint(start));
        final float right = Math.min(getPoint(stop), getTotalWidth());
        final String text = DateTimeUtils.getDownString(start, secondaryPeriod);
        if (right >= (paints.secondaryText.measureText(text) + 15) &&
                left <= getTotalWidth() - (paints.secondaryText.measureText(text) + 10)) {
            canvas.drawText(text, left + 10, 20, paints.secondaryText);
        }
    }

    private void drawSerif(Canvas canvas, DateTime dateTime, int top, int bottom, Paint paint) {
        final float point = getPoint(dateTime);
        canvas.drawLine(point, -top, point, bottom, paint);
    }

    private int getTotalWidth() {
        return getWidth() - getPaddingEnd() - getPaddingStart();
    }

    private float getPoint(DateTime dateTime) {
        final long date = dateTime.minus(dateX).getMillis();
        final long start = interval.getStart().getMillis();
        final long duration = interval.getDuration();
        final float scaleFactor = interval.getScaleFactor();
        return (float) (date - start) / duration * (getTotalWidth() * scaleFactor);
    }

    public long getDate(float pointX) {
        final int width = getTotalWidth();
        final long duration = interval.getDuration();
        final float scaleFactor = interval.getScaleFactor();
        return (long) ((pointX / width) * (duration / scaleFactor)) + dateX;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mScrollDetector.onTouchEvent(event);
        mEventDetector.onTouchEvent(event);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            final float scale = detector.getScaleFactor();
            if (interval.enlargeScaleFactor(scale)) {
                final long date = getDate(detector.getFocusX());
                float diff = date - dateX;
                diff = diff / scale - diff;
                dateX -= diff;
            }
            invalidate();
            return true;
        }
    }

    private class ScrollListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            dateX = getDate(distanceX);
            invalidate();
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public void setOnTrackClickListener(EventDetector.OnTrackClickListener onTrackClickListener) {
        mEventDetector.setOnTrackClickListener(onTrackClickListener);
    }
}
