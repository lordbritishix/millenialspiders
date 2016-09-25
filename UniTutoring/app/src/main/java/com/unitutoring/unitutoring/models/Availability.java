package com.unitutoring.unitutoring.models;

/**
 * Created by rickychang on 2016-09-24.
 */

public class Availability {
    public String dayOfWeek;
    public boolean isMatched;

    @Override
    public String toString() {
        return "Availability{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", isMatched=" + isMatched +
                '}';
    }
}
