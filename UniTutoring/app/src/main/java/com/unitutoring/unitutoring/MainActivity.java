package com.unitutoring.unitutoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.unitutoring.unitutoring.activities.SignInActivity;
import com.unitutoring.unitutoring.events.RegisterEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BusSingleton.getInstance().register(this);
        BackendSingleton.init(this);

//        BackendSingleton.getInstance().getTutorMatches(UserSingleton.getInstance().getUser().email);

        BackendSingleton.getInstance().register("u", "p", BackendSingleton.ACCOUNT_TYPE_STUDENT);

    }

    @Subscribe
    public void onRegisterEvent(RegisterEvent event) {
        if (event.isSuccessful == true) {
            Log.d(TAG, "Register succeeded.");
        } else {
            Log.d(TAG, "Register failed.");
        }

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
