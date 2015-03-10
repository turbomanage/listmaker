package com.example.listmaker.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Edit contact
 */
public class ContactDetailPlace extends Place {

    private final int selectedContactIdx;

    public ContactDetailPlace(int i) {
        this.selectedContactIdx = i;
    }

    public int getSelectedContactIdx() {
        return this.selectedContactIdx;
    }

    public static class Tokenizer implements PlaceTokenizer<ContactDetailPlace>
    {
        @Override
        public String getToken(ContactDetailPlace place)
        {
            return String.valueOf(place.getSelectedContactIdx());
        }

        @Override
        public ContactDetailPlace getPlace(String token)
        {
            return new ContactDetailPlace(Integer.parseInt(token));
        }
    }

}
