package com.kbnt.qam.timeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.kbnt.qam.timeline.date.DateSegment;
import com.kbnt.qam.timeline.date.DateTimeUtils;
import com.kbnt.qam.timeline.date.TimeInterval;
import com.kbnt.qam.timeline.channel.Channel;
import com.kbnt.qam.timeline.channel.Track;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimelineView extends View {

    private static final int MAX_COUNT = 20;

    private static final float MIN_SCALE_FACTOR = 1.F;
    private static float MAX_SCALE_FACTOR;

    private Paint linePaint;
    private Paint edgeSerifPaint;
    private Paint secondarySerifPaint;
    private Paint mainSerifPaint;
    private Paint mainTextPaint;
    private Paint secondaryTextPaint;
    private Point click;

    private TimeInterval interval;
    private ArrayList<Channel> channels;

    private long dateX = 0;
    private float scaleFactor = MIN_SCALE_FACTOR;

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
        this.channels = channels;
        long start = System.currentTimeMillis();
        long stop = 0;
        for (Channel channel : channels) {
            for (Track track : channel.getTracks()) {
                start = Math.min(start, track.start);
                stop = Math.max(stop, track.stop);
            }
        }
        interval.setStart(new DateTime(start));
        interval.setStop(new DateTime(stop));
        MAX_SCALE_FACTOR = interval.getMaxFactor();
        invalidate();
    }

    private void init() {

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(4.0F);

        edgeSerifPaint = new Paint();
        edgeSerifPaint.setColor(Color.YELLOW);
        edgeSerifPaint.setStrokeWidth(4.0F);

        mainSerifPaint = new Paint();
        mainSerifPaint.setColor(Color.GREEN);
        mainSerifPaint.setStrokeWidth(4.0F);

        secondarySerifPaint = new Paint();
        secondarySerifPaint.setColor(Color.LTGRAY);
        secondarySerifPaint.setStrokeWidth(4.0F);

        mainTextPaint = new Paint();
        mainTextPaint.setColor(Color.GREEN);
        mainTextPaint.setTextAlign(Paint.Align.CENTER);
        mainTextPaint.setTextSize(18);

        secondaryTextPaint = new Paint();
        secondaryTextPaint.setColor(Color.LTGRAY);
        secondaryTextPaint.setTextAlign(Paint.Align.LEFT);
        secondaryTextPaint.setTextSize(18);

        interval = TimeInterval.getInstance();
        interval.setMaxCount(MAX_COUNT);

        MAX_SCALE_FACTOR = interval.getMaxFactor();
        channels = new ArrayList<>();

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mScrollDetector = new GestureDetector(getContext(), new ScrollListener());
        mEventDetector = new EventDetector();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final long interval = this.interval.getInterval();
        dateX = (long) Math.max(0, Math.min(dateX, interval - interval / scaleFactor));

        canvas.save();
        canvas.translate(getPaddingStart(), getRelativeTop());
        drawTimelineBar(canvas);
        drawChannels(canvas);
        canvas.restore();

        if (click != null) {
            canvas.drawCircle(click.x, click.y, 5, linePaint);
        }
    }

    private int getRelativeTop() {
        final int height = 100 + 50 * (channels.size() - 1);
        return (getHeight() - height) / 2;
    }

    private void drawTimelineBar(Canvas canvas) {
        canvas.drawLine(0, 0, getTotalWidth(), 0, linePaint);
        drawSegments(canvas);
    }

    private void drawChannels(Canvas canvas) {
        for (int i = 0; i < channels.size(); i++) {
            drawTracks(canvas, i);
        }
    }

    private void drawTracks(Canvas canvas, int index) {
        for (Track track : channels.get(index).getTracks()) {
            final long start = track.start;
            final long stop = track.stop;
            final float startX = Math.max(0, getPoint(new DateTime(start)));
            final float stopX = Math.min(getTotalWidth(), getPoint(new DateTime(stop)));

            if (startX < stopX) {
                final int top = 40 + 50 * index;
                final int bottom = top + 30;
                canvas.drawRect(startX, top, stopX, bottom, edgeSerifPaint);
            }
        }
    }

    private void drawSegments(Canvas canvas) {
        final TimeInterval.TimePeriod secondaryPeriod = interval.getCountPeriod(scaleFactor);
        final TimeInterval.TimePeriod mainPeriod = secondaryPeriod.previous();
        final DateTime start = interval.getStart().plus(getDate(0));
        final DateTime stop = interval.getStart().plus(getDate(getTotalWidth()));
        final List<DateSegment> segments = getSegments(new DateSegment(start, stop), mainPeriod);
        for (DateSegment segment : segments) {
            // Draws main serif line
            drawMainSerifLine(canvas, segment);
            // Draws main serif text
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
            drawSerif(canvas, start, 20, 20, edgeSerifPaint);
        }
        final DateTime stop = interval.getStop();
        final float right = getPoint(stop);
        if (right <= getTotalWidth()) {
            drawSerif(canvas, stop, 20, 20, edgeSerifPaint);
        }
    }

    private void drawMainSerifLine(Canvas canvas, DateSegment segment) {
        final DateTime start = segment.start;
        final float left = getPoint(start);
        if (left >= 0 && left <= getTotalWidth()) {
            drawSerif(canvas, start, 20, 0, mainSerifPaint);
        }
    }

    private void drawMainSerifText(Canvas canvas, TimeInterval.TimePeriod secondaryPeriod, DateSegment mainSegment) {
        final DateTime start = mainSegment.start;
        final DateTime stop = mainSegment.stop;
        final String text = DateTimeUtils.getUpString(start, secondaryPeriod);
        final float left = Math.max(0, getPoint(start));
        final float right = Math.min(getPoint(stop), getTotalWidth());
        final float middle = (left + right) / 2;
        if (middle >= (mainTextPaint.measureText(text) + 10) / 2 &&
                middle <= getTotalWidth() - (mainTextPaint.measureText(text) + 10) / 2) {
            canvas.drawText(text, middle, -10, mainTextPaint);
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
            drawSerif(canvas, start, 0, 15, secondarySerifPaint);
        }
    }

    private void drawSecondarySerifText(Canvas canvas, TimeInterval.TimePeriod secondaryPeriod, DateSegment segment) {
        final DateTime start = segment.start;
        final DateTime stop = segment.stop;
        final float left = Math.max(0, getPoint(start));
        final float right = Math.min(getPoint(stop), getTotalWidth());
        final String text = DateTimeUtils.getDownString(start, secondaryPeriod);
        if (right >= (secondaryTextPaint.measureText(text) + 15) &&
                left <= getTotalWidth() - (secondaryTextPaint.measureText(text) + 10)) {
            canvas.drawText(text, left + 10, 20, secondaryTextPaint);
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
        final long interval = this.interval.getInterval();
        return (float) (date - start) / interval * (getTotalWidth() * scaleFactor);
    }

    private long getDate(float pointX) {
        final long interval = this.interval.getInterval();
        final int width = getTotalWidth();
        return (long) ((pointX / width) * (interval / scaleFactor)) + dateX;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mScrollDetector.onTouchEvent(event);
        mEventDetector.onTouchEvent(event);
        return true;
    }

    private Channel getChannel(float y) {
        for (int i = 0; i < channels.size(); i++) {
            final int top = 40 + 50 * i;
            final int bottom = top + 30;
            if (y + 10 >= top && y - 10 <= bottom) {
                return channels.get(i);
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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            final float scale = detector.getScaleFactor();
            scaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(scaleFactor * scale, MAX_SCALE_FACTOR));
            if (scaleFactor > MIN_SCALE_FACTOR && scaleFactor < MAX_SCALE_FACTOR) {
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

    private class EventDetector {

        private static final int CLICK_ACTION_THRESHOLD = 200;
        private static final int MOVE_ACTION_THRESHOLD = 10;

        private long startClickTime;
        private float startMove;

        private OnTrackClickListener mOnTrackClickListener;

        void onTouchEvent(MotionEvent event) {
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
                        click = new Point((int) x, (int) y);
                        performClick(x, y);
                        invalidate();
                    } else {
                        click = null;
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
                final long datePoint = getDate(x - getPaddingStart());
                final DateTime dateTime = interval.getStart().plus(datePoint);
                final Track track = getTrack(channel, dateTime);
                if (track != null) {
                    if (mOnTrackClickListener != null) {
                        mOnTrackClickListener.onTrackClick(track, dateTime);
                    }
                } else if (mOnTrackClickListener != null) {
                    mOnTrackClickListener.onFailure(x, y, "no track found in this point");
                }
            } else if (mOnTrackClickListener != null) {
                mOnTrackClickListener.onFailure(x, y, "no channel found in the point");
            }
        }

        void setOnEpisodeClickListener(OnTrackClickListener onTrackClickListener) {
            this.mOnTrackClickListener = onTrackClickListener;
        }
    }

    public void setOnTrackClickListener(OnTrackClickListener onTrackClickListener) {
        mEventDetector.setOnEpisodeClickListener(onTrackClickListener);
    }

    public interface OnTrackClickListener {
        void onTrackClick(Track track, DateTime dateTime);

        void onFailure(float x, float y, String message);
    }
}
