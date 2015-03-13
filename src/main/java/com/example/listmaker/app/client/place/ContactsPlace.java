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

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof ContactsPlace)) {
            ContactsPlace place = (ContactsPlace) obj;
            Tokenizer tokenizer = new Tokenizer();
            String placeToken = tokenizer.getToken(place);
            String thisToken = tokenizer.getToken(this);
            return thisToken.equals(placeToken);
        }
        return false;
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
