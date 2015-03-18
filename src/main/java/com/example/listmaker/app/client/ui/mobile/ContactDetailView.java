package com.example.listmaker.app.client.ui.mobile;

import com.example.listmaker.common.client.presenter.Presenter;
import com.example.listmaker.common.client.ui.web.View;
import com.example.listmaker.app.shared.domain.Contact;

/**
 * Created by david on 2/26/15.
 */
public interface ContactDetailView extends View<ContactDetailView.Delegate> {

    interface Delegate extends Presenter<ContactDetailView> {
        void saveContact(Contact contact);

        void cancelEdit();
    }

    public Contact getContact();

    public void setContact(Contact contact);
}
