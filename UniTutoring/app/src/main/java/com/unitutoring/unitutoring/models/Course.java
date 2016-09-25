package com.unitutoring.unitutoring.models;

/**
 * Created by rickychang on 2016-09-24.
 */

public class Course {
    public String courseId;
    public boolean isMatched;

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", isMatched=" + isMatched +
                '}';
    }
}
