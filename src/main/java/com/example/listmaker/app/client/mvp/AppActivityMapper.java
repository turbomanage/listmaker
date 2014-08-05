package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.example.listmaker.app.client.activity.*;
import com.example.listmaker.app.client.place.*;

/**
* Created by Gene on 6/5/2014.
*/
public class AppActivityMapper implements ActivityMapper {

    public AppActivityMapper() {
        super();
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HomePlace) {
            return new ManageNotesActivity((HomePlace) place);
        } else if (place instanceof ProfilePlace) {
            return new ProfileActivity((ProfilePlace) place);
        }
        return null;
    }
}

