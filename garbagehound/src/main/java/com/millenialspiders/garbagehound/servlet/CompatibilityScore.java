package com.millenialspiders.garbagehound.servlet;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import com.google.api.client.util.Maps;
import com.google.appengine.repackaged.com.google.common.collect.Sets;
import com.millenialspiders.garbagehound.model.Course;
import com.millenialspiders.garbagehound.model.InstructorAccountDetails;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

public class CompatibilityScore {
    private final InstructorAccountDetails instructor;
    private final StudentAccountDetails student;

    public CompatibilityScore(InstructorAccountDetails instructor, StudentAccountDetails student) {
        this.instructor = instructor;
        this.student = student;
    }

    public Set<DayOfWeek> getCommonAvailability() {
        return Sets.intersection(instructor.getPreferredDaySlot(), student.getPreferredDaySlot());
    }

    public Set<Course> getCommonCourses() {
        return Sets.intersection(instructor.getCourses(), student.getCourses());
    }

    public Map<String, Boolean> getMarkedCourses() {
        Map<String, Boolean> mark = Maps.newHashMap();

        Set<Course> intersection = getCommonCourses();

        for (Course course : instructor.getCourses()) {
            mark.put(course.getCourseId(), intersection.contains(course));
        }

        return mark;
    }

    public Map<DayOfWeek, Boolean> getMarkedAvailability() {
        Map<DayOfWeek, Boolean> mark = Maps.newHashMap();

        Set<DayOfWeek> intersection = getCommonAvailability();

        for (DayOfWeek dayOfWeek: instructor.getPreferredDaySlot()) {
            mark.put(dayOfWeek, intersection.contains(dayOfWeek));
        }

        return mark;
    }

    public int getScore() {
        Set<DayOfWeek> dayOfWeeks = getCommonAvailability();
        Set<Course> courses = getCommonCourses();

        if (dayOfWeeks.size() <= 0) {
            return 0;
        }

        if (courses.size() <= 0) {
            return 0;
        }

        return dayOfWeeks.size() + courses.size();
    }

    public InstructorAccountDetails getInstructor() {
        return instructor;
    }
}
