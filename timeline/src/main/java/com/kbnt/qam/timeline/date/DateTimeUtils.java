package com.kbnt.qam.timeline.date;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeUtils {

    public static String getUpString(DateTime date, TimeInterval.TimePeriod timePeriod) {
        return getString(date, getUpPattern(timePeriod));
    }

    public static String getDownString(DateTime date, TimeInterval.TimePeriod timePeriod) {
        return getString(date, getDownPattern(timePeriod));
    }

    private static String getUpPattern(TimeInterval.TimePeriod timePeriod) {
        switch (timePeriod) {
            case DECADE:
            case YEAR:
                return "YYYY'x'";
            case MONTH:
                return "YYYY";
            case DAY:
                return "MMMM YYYY";
            case HALF_DAY:
                return "dd.MM.YYYY";
            case QUARTER_DAY:
            case ONE_EIGHT_DAY:
            case HOUR:
            case HALF_HOUR:
            case QUARTER_HOUR:
            case FIVE_MINUTE:
                return "dd MMMM YYYY";
            case MINUTE:
                return "dd MMMM YYYY";
            default:
                return "";
        }
    }

    private static String getDownPattern(TimeInterval.TimePeriod timePeriod) {
        switch (timePeriod) {
            case DECADE:
            case YEAR:
                return "YYYY";
            case MONTH:
                return "MMM";
            case DAY:
                return "dd";
            case HALF_DAY:
            case QUARTER_DAY:
            case ONE_EIGHT_DAY:
            case HOUR:
            case HALF_HOUR:
            case QUARTER_HOUR:
            case FIVE_MINUTE:
            case MINUTE:
                return "HH:mm";
            default:
                return "";
        }
    }

    private static String getString(DateTime date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.ROOT).format(date.getMillis());
    }
}
