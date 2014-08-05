package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by Gene on 6/17/2014.
 */
public class ProfilePlace extends Place {

    private String token = "";

    public ProfilePlace() {

    }

    public String getToken() {
        return token;

    }

    public static class Tokenizer implements PlaceTokenizer<ProfilePlace>
    {
        @Override
        public String getToken(ProfilePlace place)
        {
            return place.getToken();
        }

        @Override
        public ProfilePlace getPlace(String token)
        {
            return new ProfilePlace();
        }
    }
}
