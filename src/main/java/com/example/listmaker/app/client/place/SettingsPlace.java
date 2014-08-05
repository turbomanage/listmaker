package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by Gene on 6/5/2014.
 */
public class SettingsPlace extends Place {

    private String token = "";

    public SettingsPlace() {

    }

    public String getToken() {
        return token;

    }

    public static class Tokenizer implements PlaceTokenizer<SettingsPlace>
    {
        @Override
        public String getToken(SettingsPlace place)
        {
            return place.getToken();
        }

        @Override
        public SettingsPlace getPlace(String token)
        {
            return new SettingsPlace();
        }
    }
}
