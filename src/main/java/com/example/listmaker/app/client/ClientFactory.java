package com.example.listmaker.app.client;

import com.example.listmaker.app.client.ui.mobile.ContactDetailView;
import com.example.listmaker.app.client.ui.mobile.ContactsView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Created by Gene on 6/5/2014.
 */
public interface ClientFactory {

    ContactsView getContactsView();
    ContactDetailView getContactDetailView();

}
