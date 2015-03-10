package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by Gene on 6/5/2014.
 */
public class ContactsPlace extends Place {

    private String token;

    public ContactsPlace(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;

    }

    public static class Tokenizer implements PlaceTokenizer<ContactsPlace>
    {
        @Override
        public String getToken(ContactsPlace place)
        {
            return place.getToken();
        }

        @Override
        public ContactsPlace getPlace(String token)
        {
            return new ContactsPlace(token);
        }
    }

}
