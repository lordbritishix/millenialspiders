package com.unitutoring.unitutoring;

import com.unitutoring.unitutoring.models.Student;

/**
 * Created by rickychang on 2016-09-24.
 */
public class UserSingleton {
    private static UserSingleton ourInstance = new UserSingleton();

    public static UserSingleton getInstance() {
        return ourInstance;
    }

    private Student mUser;

    private UserSingleton() {
    }

    public void setUser(Student user) {
        mUser = user;
    }

    public Student getUser() {
        return mUser;
    }
}
