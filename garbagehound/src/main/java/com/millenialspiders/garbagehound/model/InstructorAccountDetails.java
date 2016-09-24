package com.millenialspiders.garbagehound.model;

import java.time.DayOfWeek;
import java.util.Set;
import com.google.common.base.Preconditions;

/**
 * Account details for instructors
 */
public class InstructorAccountDetails extends AccountDetails {
    private final Set<Course> courses;
    private final Set<DayOfWeek> preferredDaySlot;
    private final Set<StudentAccountDetails> myStudents;

    private InstructorAccountDetails(InstructorAccountDetailsBuilder builder) {
        this.courses = builder.courses;
        this.preferredDaySlot = builder.preferredDaySlot;
        this.myStudents = builder.myStudents;

        this.setFirstName(builder.firstName);
        this.setLastName(builder.lastName);
        this.setEmailAddress(builder.emailAddress);
        this.setPhoneNo(builder.phoneNo);
    }

    public static class InstructorAccountDetailsBuilder {
        private Set<Course> courses;
        private Set<DayOfWeek> preferredDaySlot;
        private Set<StudentAccountDetails> myStudents;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String phoneNo;

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

        public InstructorAccountDetailsBuilder withPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        public InstructorAccountDetails build() {
            Preconditions.checkNotNull(firstName, "firstname must not be null");
            Preconditions.checkNotNull(emailAddress, "emailAddress must not be null");

            return new InstructorAccountDetails(this);
        }
    }

}
