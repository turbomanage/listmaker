package com.example.listmaker.app.client.service;

import com.example.listmaker.app.client.domain.ContactProperties;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.common.domain.Contact;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.data.shared.ListStore;

import java.util.List;

/**
 * Singleton that represents the current state of the UI. Getters
 * are public to be called from various other presenters, whereas
 * setters are package access to be called only by service facades.
 * Setters may fire an event to notify listening widgets that the
 * model has been updated.
 * 
 * @author David Chandler
 */
public class AppModel
{
    private User me;
    private final ContactProperties contactProperties = GWT.create(ContactProperties.class);
    private final ListStore<Contact> contactStore = new ListStore<Contact>(contactProperties.id());

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public ListStore<Contact> getContactStore() {
        return contactStore;
    }
}
