package com.kbnt.qam.timeline.date;

import android.util.Pair;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;

import java.util.LinkedHashSet;
import java.util.Set;

public class TimeInterval {

    private DateSegment dateSegment;

    private int maxCount;

    public enum TimePeriod {
        DECADE, YEAR, MONTH,
        DAY, HALF_DAY, QUARTER_DAY, ONE_EIGHT_DAY,
        HOUR, HALF_HOUR, QUARTER_HOUR, FIVE_MINUTE, MINUTE;

        private static TimePeriod[] values = values();

        public TimePeriod previous() {
            final TimePeriod period = values[Math.max(0, this.ordinal() - 1) % values.length];
            switch (period) {
                case HALF_DAY:
                case QUARTER_DAY:
                case ONE_EIGHT_DAY:
                case HOUR:
                case HALF_HOUR:
                case QUARTER_HOUR:
                case FIVE_MINUTE:
                case MINUTE:
                    return DAY;
                default:
                    return period;
            }
        }
    }

    private TimeInterval() {
        this.dateSegment = new DateSegment();
    }

    public static TimeInterval getInstance() {
        return new TimeInterval();
    }

    public DateTime getStart() {
        return dateSegment.start;
    }

    public void setStart(DateTime startDateTime) {
        dateSegment.setStart(startDateTime);
    }

    public DateTime getStop() {
        return dateSegment.getStop();
    }

    public void setStop(DateTime stopDateTime) {
        dateSegment.setStop(stopDateTime);
    }

    public long getInterval() {
        return dateSegment.getInterval();
    }

    public float getMaxFactor() {
        return (float) getDiff(TimePeriod.MINUTE) / 10;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    private int getDiff(TimePeriod period) {
        final DateTime start = dateSegment.getStart();
        final DateTime stop = dateSegment.getStop();
        switch (period) {
            case DECADE:
                return (int) Math.ceil((float) getDiff(TimePeriod.YEAR) / 10);
            case YEAR:
                return stop.getYear() - start.getYear();
            case MONTH:
                return Months.monthsBetween(start.withDayOfMonth(1), stop.withDayOfMonth(1)).getMonths() + 1;
            case DAY:
                return Days.daysBetween(start, stop).getDays();
            case HALF_DAY:
                return getDiff(TimePeriod.DAY) * 2;
            case QUARTER_DAY:
                return getDiff(TimePeriod.DAY) * 4;
            case ONE_EIGHT_DAY:
                return getDiff(TimePeriod.DAY) * 8;
            case HOUR:
                return Hours.hoursBetween(start, stop).getHours();
            case HALF_HOUR:
                return getDiff(TimePeriod.HOUR) * 2;
            case QUARTER_HOUR:
                return getDiff(TimePeriod.HOUR) * 4;
            case FIVE_MINUTE:
                return (int) Math.ceil((float) getDiff(TimePeriod.MINUTE) / 5);
            case MINUTE:
                return Minutes.minutesBetween(start, stop).getMinutes();
            default:
                return -1;
        }
    }

    public TimePeriod getCountPeriod(float scaleFactor) {
        final Set<Pair<TimePeriod, Integer>> periods = new LinkedHashSet<>();
        for (TimeInterval.TimePeriod timePeriod : TimeInterval.TimePeriod.values()) {
            periods.add(new Pair<>(timePeriod, getDiff(timePeriod)));
        }
        Pair<TimeInterval.TimePeriod, Integer> result = periods.iterator().next();
        for (Pair<TimeInterval.TimePeriod, Integer> period : periods) {
            final Integer value = period.second;
            if (value > result.second && value <= maxCount * scaleFactor) {
                result = period;
            }
        }
        return result.first;
    }

    public static DateTime getNormalizedDate(TimePeriod timePeriod, DateTime date) {
        switch (timePeriod) {
            case DECADE:
                return date.withYear((date.getYear() / 10) * 10).withDayOfYear(1).withMillisOfDay(0);
            case YEAR:
                return date.withDayOfYear(1).withMillisOfDay(0);
            case MONTH:
                return date.withDayOfMonth(1).withMillisOfDay(0);
            case DAY:
                return date.withMillisOfDay(0);
            case HALF_DAY:
            case QUARTER_DAY:
            case ONE_EIGHT_DAY:
            case HOUR:
                return date.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HALF_HOUR:
            case QUARTER_HOUR:
            case FIVE_MINUTE:
            case MINUTE:
                return date.withSecondOfMinute(0).withMillisOfSecond(0);
            default:
                return date;
        }
    }

    public static DateTime getNext(TimePeriod timePeriod, DateTime date) {
        switch (timePeriod) {
            case DECADE:
                return date.plusYears(10).withDayOfYear(1).withMillisOfDay(0);
            case YEAR:
                return date.plusYears(1).withDayOfYear(1).withMillisOfDay(0);
            case MONTH:
                return date.plusMonths(1).withDayOfMonth(1).withMillisOfDay(0);
            case DAY:
                return date.plusDays(1).withMillisOfDay(0);
            case HALF_DAY:
                return date.plusHours(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case QUARTER_DAY:
                return date.plusHours(6).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case ONE_EIGHT_DAY:
                return date.plusHours(3).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HOUR:
                return date.plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HALF_HOUR:
                return date.plusMinutes(30).withSecondOfMinute(0).withMillisOfSecond(0);
            case QUARTER_HOUR:
                return date.plusMinutes(15).withSecondOfMinute(0).withMillisOfSecond(0);
            case FIVE_MINUTE:
                return date.plusMinutes(5).withSecondOfMinute(0).withMillisOfSecond(0);
            case MINUTE:
                return date.plusMinutes(1).withSecondOfMinute(0).withMillisOfSecond(0);
            default:
                return date;
        }
    }
}
