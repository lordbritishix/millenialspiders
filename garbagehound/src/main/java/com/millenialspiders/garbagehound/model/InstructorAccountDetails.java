package com.millenialspiders.garbagehound.model;

import java.time.DayOfWeek;
import java.util.Set;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Account details for instructors
 */
public class InstructorAccountDetails extends AccountDetails {
    private final Set<Course> courses;
    private final Set<DayOfWeek> preferredDaySlot;
    private final Set<StudentAccountDetails> myStudents;
    private final String photoLocation;

    private InstructorAccountDetails(InstructorAccountDetailsBuilder builder) {
        this.courses = builder.courses != null
                ? ImmutableSet.copyOf(builder.courses) : ImmutableSet.of();
        this.preferredDaySlot = builder.preferredDaySlot != null
                ? ImmutableSet.copyOf(builder.preferredDaySlot) : ImmutableSet.of();
        this.myStudents = builder.myStudents != null
                ? ImmutableSet.copyOf(builder.myStudents) : ImmutableSet.of();
        this.photoLocation = builder.photoLocation;

        this.setFirstName(builder.firstName);
        this.setLastName(builder.lastName);
        this.setEmailAddress(builder.emailAddress);
        this.setPhoneNo(builder.phoneNo);
        this.setUsername(builder.username);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Set<DayOfWeek> getPreferredDaySlot() {
        return preferredDaySlot;
    }

    public Set<StudentAccountDetails> getMyStudents() {
        return myStudents;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public static class InstructorAccountDetailsBuilder {
        private Set<Course> courses;
        private Set<DayOfWeek> preferredDaySlot;
        private Set<StudentAccountDetails> myStudents;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String phoneNo;
        private String photoLocation;
        private String username;

        public static InstructorAccountDetailsBuilder newInstance() {
            return new InstructorAccountDetailsBuilder();
        }

        public InstructorAccountDetailsBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public InstructorAccountDetailsBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public InstructorAccountDetailsBuilder withEmail(String email) {
            this.emailAddress = email;
            return this;
        }

        public InstructorAccountDetailsBuilder withCourses(Set<Course> courses) {
            this.courses = courses;
            return this;
        }

        public InstructorAccountDetailsBuilder withStudents(Set<StudentAccountDetails> myStudents) {
            this.myStudents = myStudents;
            return this;
        }

        public InstructorAccountDetailsBuilder withPreferredDaySlot(Set<DayOfWeek> preferredDaySlot) {
            this.preferredDaySlot = preferredDaySlot;
            return this;
        }

        public InstructorAccountDetailsBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public InstructorAccountDetailsBuilder withPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        public InstructorAccountDetailsBuilder withPhotoLocation(String photoLocation) {
            this.photoLocation = photoLocation;
            return this;
        }

        public InstructorAccountDetails build() {
            Preconditions.checkNotNull(firstName, "firstname must not be null");
            Preconditions.checkNotNull(emailAddress, "emailAddress must not be null");

            return new InstructorAccountDetails(this);
        }
    }

}
