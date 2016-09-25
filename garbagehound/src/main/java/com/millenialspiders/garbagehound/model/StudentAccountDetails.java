package com.millenialspiders.garbagehound.model;

import java.time.DayOfWeek;
import java.util.Set;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Details for a student account
 */
public class StudentAccountDetails extends AccountDetails {
    private final Set<Course> courses;
    private final Set<DayOfWeek> preferredDaySlot;
    private final Set<InstructorAccountDetails> myInstructors;

    private StudentAccountDetails(StudentAccountDetailsBuilder builder) {
        this.courses = builder.courses != null
                ? ImmutableSet.copyOf(builder.courses) : ImmutableSet.of();
        this.preferredDaySlot = builder.preferredDaySlot != null
                ? ImmutableSet.copyOf(builder.preferredDaySlot) : ImmutableSet.of();
        this.myInstructors = builder.myInstructors != null
                ? ImmutableSet.copyOf(builder.myInstructors) : ImmutableSet.of();
        setEmailAddress(builder.emailAddress);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setPhoneNo(builder.phoneNo);
        setUsername(builder.username);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Set<DayOfWeek> getPreferredDaySlot() {
        return preferredDaySlot;
    }

    public Set<InstructorAccountDetails> getMyInstructors() {
        return myInstructors;
    }

    public static class StudentAccountDetailsBuilder {
        private Set<Course> courses;
        private Set<DayOfWeek> preferredDaySlot;
        private Set<InstructorAccountDetails> myInstructors;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String phoneNo;
        private String username;

        public static StudentAccountDetailsBuilder newInstance() {
            return new StudentAccountDetailsBuilder();
        }

        public StudentAccountDetailsBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentAccountDetailsBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentAccountDetailsBuilder withEmail(String email) {
            this.emailAddress = email;
            return this;
        }

        public StudentAccountDetailsBuilder withPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        public StudentAccountDetailsBuilder withCourses(Set<Course> courses) {
            this.courses = courses;
            return this;
        }

        public StudentAccountDetailsBuilder withInstructors(Set<InstructorAccountDetails> instructors) {
            this.myInstructors = instructors;
            return this;
        }

        public StudentAccountDetailsBuilder withPreferredDaySlot(Set<DayOfWeek> preferredDaySlot) {
            this.preferredDaySlot = preferredDaySlot;
            return this;
        }

        public StudentAccountDetailsBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public StudentAccountDetails build() {
            Preconditions.checkNotNull(firstName, "firstname must not be null");
            Preconditions.checkNotNull(emailAddress, "emailAddress must not be null");

            return new StudentAccountDetails(this);
        }
    }
}
