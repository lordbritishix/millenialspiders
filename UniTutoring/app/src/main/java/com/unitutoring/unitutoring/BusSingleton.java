package com.unitutoring.unitutoring;

import android.support.annotation.NonNull;

import com.squareup.otto.Bus;

/**
 * Created by rickychang on 2016-09-24.
 */
public class BusSingleton {
    private static BusSingleton ourInstance = new BusSingleton();

    public static BusSingleton getInstance() {
        return ourInstance;
    }

    private Bus mBus = new Bus();

    private BusSingleton() {
    }

    public void register(Object object) {
        mBus.register(object);
    }

    public void unregister(Object object) {
        mBus.unregister(object);
    }

    public void post(@NonNull Object event) {
        mBus.post(event);
    }
}
