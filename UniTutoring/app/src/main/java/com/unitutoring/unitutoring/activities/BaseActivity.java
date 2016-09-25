package com.unitutoring.unitutoring.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.unitutoring.unitutoring.BusSingleton;

/**
 * Created by rickychang on 2016-09-24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusSingleton.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusSingleton.getInstance().unregister(this);
    }
}
