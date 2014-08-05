package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.example.listmaker.app.client.activity.AddNoteActivity;
import com.example.listmaker.app.client.place.HomePlace;

/**
 * Created by Gene on 6/11/2014.
 */
public class AddNoteActivityMapper implements ActivityMapper {

    public AddNoteActivityMapper() {
        super();
    }

    @Override
    public Activity getActivity(Place place) {
        //Hide AddNote if not at HomePlace
        if (place instanceof HomePlace) {
            return new AddNoteActivity((HomePlace) place);
        }
        return null;
    }
}
