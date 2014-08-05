package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.example.listmaker.app.client.activity.UserActivity;
import com.example.listmaker.app.client.place.HomePlace;

/**
 * Created by Gene on 6/11/2014.
 */
public class UserActivityMapper implements ActivityMapper {

    public UserActivityMapper() {
        super();
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HomePlace) {
            return new UserActivity((HomePlace) place);
        }
        return null;
    }
}
