package com.unitutoring.unitutoring;

import android.graphics.Color;

/**
 * Created by rickychang on 2016-09-25.
 */

public class Utils {
    public static int colorForWeekday(String weekday) {
        if (weekday.substring(0, 1).toLowerCase().contains("monday")) {
            return Color.RED;
        } else if (weekday.toLowerCase().contains("tuesday")) {
            return Color.MAGENTA;
        } else if (weekday.toLowerCase().contains("wednesday")) {
            return Color.YELLOW;
        } else if (weekday.toLowerCase().contains("thursday")) {
            return Color.GREEN;
        } else if (weekday.toLowerCase().contains("friday")) {
            return Color.BLUE;
        } else if (weekday.toLowerCase().contains("saturday")) {
            return Color.CYAN;
        } else if (weekday.toLowerCase().contains("sunday")) {
            return Color.DKGRAY;
        }

        return Color.BLACK;
    }

    public static int colorForCourse(String courseId) {
        if (courseId.toLowerCase().contains("comp")) {
            return Color.BLUE;
        } else if (courseId.contains("poa")) {
            return Color.BLACK;
        } else if (courseId.contains("scl")) {
            return Color.GREEN;
        }

        return Color.DKGRAY;
    }

    public static String letterForWeekday(String weekday) {
        if (weekday.substring(0, 1).toLowerCase().contains("monday")) {
            return "M";
        } else if (weekday.toLowerCase().contains("tuesday")) {
            return "T";
        } else if (weekday.toLowerCase().contains("wednesday")) {
            return "W";
        } else if (weekday.toLowerCase().contains("thursday")) {
            return "U";
        } else if (weekday.toLowerCase().contains("friday")) {
            return "F";
        } else if (weekday.toLowerCase().contains("saturday")) {
            return "S";
        } else if (weekday.toLowerCase().contains("sunday")) {
            return "S";
        }

        return "";
    }
}
