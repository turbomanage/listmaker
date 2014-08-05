package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by Gene on 6/5/2014.
 */
public class HomePlace extends Place {

    private String token;

    public HomePlace(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;

    }

    public static class Tokenizer implements PlaceTokenizer<HomePlace>
    {
        @Override
        public String getToken(HomePlace place)
        {
            return place.getToken();
        }

        @Override
        public HomePlace getPlace(String token)
        {
            return new HomePlace(token);
        }
    }

}
