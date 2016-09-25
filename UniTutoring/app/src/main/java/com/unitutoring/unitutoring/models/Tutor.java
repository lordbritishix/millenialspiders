package com.unitutoring.unitutoring.models;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by rickychang on 2016-09-24.
 */

public class Tutor {
    public String username;
    public String firstname;
    public String lastname;
    public String email;
    public String phoneNo;
    public String photo;
    public List<Course> courses;
    public List<Availability> availability;

    public static Tutor create(JSONObject jsonObject) {
        Gson gson = new Gson();
        Tutor tutor = gson.fromJson(jsonObject.toString(), Tutor.class);

        Log.d("Tutor", tutor.toString());
        return tutor;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", photo='" + photo + '\'' +
                ", courses=" + courses +
                ", availability=" + availability +
                '}';
    }
}
