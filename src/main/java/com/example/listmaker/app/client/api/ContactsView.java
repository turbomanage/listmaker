package com.example.listmaker.app.client.api;

import com.example.listmaker.common.client.presenter.Presenter;
import com.example.listmaker.common.client.ui.web.View;
import com.example.listmaker.common.domain.Contact;
import com.sencha.gxt.data.shared.ListStore;

/**
 * Created by david on 2/26/15.
 */
public interface ContactsView extends View<ContactsView.Delegate> {

    interface Delegate extends Presenter<ContactsView> {
        void addContact(Contact contact);
    }

    public ListStore<Contact> getStore();
}
