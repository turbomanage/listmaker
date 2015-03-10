package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Edit contact
 */
public class ContactDetailPlace extends Place {

    private String token;

    public ContactDetailPlace(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;

    }

    public static class Tokenizer implements PlaceTokenizer<ContactDetailPlace>
    {
        @Override
        public String getToken(ContactDetailPlace place)
        {
            return place.getToken();
        }

        @Override
        public ContactDetailPlace getPlace(String token)
        {
            return new ContactDetailPlace(token);
        }
    }

}
