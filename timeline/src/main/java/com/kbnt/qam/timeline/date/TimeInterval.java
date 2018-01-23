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

    private DateTime startDateTime;
    private DateTime stopDateTime;
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
        startDateTime = new DateTime(0);
        stopDateTime = new DateTime();
    }

    public static TimeInterval getInstance() {
        return new TimeInterval();
    }

    public DateTime getStart() {
        return startDateTime;
    }

    public void setStart(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getStop() {
        return stopDateTime;
    }

    public void setStop(DateTime stopDateTime) {
        this.stopDateTime = stopDateTime;
    }

    public long getInterval() {
        return stopDateTime.getMillis() - startDateTime.getMillis();
    }

    public float getMaxFactor() {
        return (float) getDiff(TimePeriod.MINUTE) / 10;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    private int getDiff(TimePeriod period) {
        switch (period) {
            case DECADE:
                return (int) Math.ceil((float) getDiff(TimePeriod.YEAR) / 10);
            case YEAR:
                return stopDateTime.getYear() - startDateTime.getYear();
            case MONTH:
                return Months.monthsBetween(startDateTime.withDayOfMonth(1), stopDateTime.withDayOfMonth(1)).getMonths() + 1;
            case DAY:
                return Days.daysBetween(startDateTime, stopDateTime).getDays();
            case HALF_DAY:
                return getDiff(TimePeriod.DAY) * 2;
            case QUARTER_DAY:
                return getDiff(TimePeriod.DAY) * 4;
            case ONE_EIGHT_DAY:
                return getDiff(TimePeriod.DAY) * 8;
            case HOUR:
                return Hours.hoursBetween(startDateTime, stopDateTime).getHours();
            case HALF_HOUR:
                return getDiff(TimePeriod.HOUR) * 2;
            case QUARTER_HOUR:
                return getDiff(TimePeriod.HOUR) * 4;
            case FIVE_MINUTE:
                return (int) Math.ceil((float) getDiff(TimePeriod.MINUTE) / 5);
            case MINUTE:
                return Minutes.minutesBetween(startDateTime, stopDateTime).getMinutes();
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

    public static DateTime getNormalizedDate(TimePeriod timePeriod, DateTime dateTime) {
        switch (timePeriod) {
            case DECADE:
                return dateTime.withYear((dateTime.getYear() / 10) * 10).withDayOfYear(1).withMillisOfDay(0);
            case YEAR:
                return dateTime.withDayOfYear(1).withMillisOfDay(0);
            case MONTH:
                return dateTime.withDayOfMonth(1).withMillisOfDay(0);
            case DAY:
                return dateTime.withMillisOfDay(0);
            case HALF_DAY:
            case QUARTER_DAY:
            case ONE_EIGHT_DAY:
            case HOUR:
                return dateTime.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HALF_HOUR:
            case QUARTER_HOUR:
            case FIVE_MINUTE:
            case MINUTE:
                return dateTime.withSecondOfMinute(0).withMillisOfSecond(0);
            default:
                return dateTime;
        }
    }

    public static DateTime getNext(TimePeriod timePeriod, DateTime dateTime) {
        switch (timePeriod) {
            case DECADE:
                return dateTime.plusYears(10).withDayOfYear(1).withMillisOfDay(0);
            case YEAR:
                return dateTime.plusYears(1).withDayOfYear(1).withMillisOfDay(0);
            case MONTH:
                return dateTime.plusMonths(1).withDayOfMonth(1).withMillisOfDay(0);
            case DAY:
                return dateTime.plusDays(1).withMillisOfDay(0);
            case HALF_DAY:
                return dateTime.plusHours(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case QUARTER_DAY:
                return dateTime.plusHours(6).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case ONE_EIGHT_DAY:
                return dateTime.plusHours(3).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HOUR:
                return dateTime.plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            case HALF_HOUR:
                return dateTime.plusMinutes(30).withSecondOfMinute(0).withMillisOfSecond(0);
            case QUARTER_HOUR:
                return dateTime.plusMinutes(15).withSecondOfMinute(0).withMillisOfSecond(0);
            case FIVE_MINUTE:
                return dateTime.plusMinutes(5).withSecondOfMinute(0).withMillisOfSecond(0);
            case MINUTE:
                return dateTime.plusMinutes(1).withSecondOfMinute(0).withMillisOfSecond(0);
            default:
                return dateTime;
        }
    }
}
