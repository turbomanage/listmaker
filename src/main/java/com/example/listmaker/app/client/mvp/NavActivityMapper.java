package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.example.listmaker.app.client.activity.NavActivity;

/**
 * Created by Gene on 6/11/2014.
 */
public class NavActivityMapper implements ActivityMapper {

    public NavActivityMapper() {
        super();
    }

    @Override
    public Activity getActivity(Place place) {
        return new NavActivity(place);
    }
}
