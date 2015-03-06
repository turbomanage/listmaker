package com.example.listmaker.app.client;

import com.example.listmaker.app.client.api.ContactsView;
import com.example.listmaker.app.client.ui.mobile.ContactsViewImpl;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Manages instances of Views.
 * Created by Gene on 6/5/2014.
 */
public class ClientFactoryImpl implements ClientFactory {

    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);
    private static ContactsView contactsView;

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public ContactsView getContactsView() {
        if (contactsView == null) {
            contactsView = new ContactsViewImpl();
        }
        return contactsView;
    }
}
