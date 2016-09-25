package com.unitutoring.unitutoring.events;

import com.unitutoring.unitutoring.models.Tutor;

import java.util.List;

/**
 * Created by rickychang on 2016-09-24.
 */

public class MatchEvent extends BaseEvent {
    public List<Tutor> tutorList;

    @Override
    public String toString() {
        return "MatchEvent{" +
                "tutorList=" + tutorList +
                "} " + super.toString();
    }
}
