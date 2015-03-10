package com.example.listmaker.app.client.presenter;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.service.ContactService;
import com.example.listmaker.app.client.ui.mobile.ContactsView;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.domain.Contact;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.turbomanage.gwt.client.rest.ListResponse;

/**
 * Created by david on 2/26/15.
 */
public class ContactsActivity extends ActivityPresenter<ContactsView> implements ContactsView.Delegate {

    private static final ContactService svc = App.serviceFactory().contactService();

    public ContactsActivity() {

    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        bind(App.clientFactory().getContactsView());
        super.start(acceptsOneWidget, eventBus);
        svc.listAll(new AppCallback<ListResponse<Contact>>() {
            @Override
            public void handleSuccess(ListResponse<Contact> result) {
                App.model().getContactStore().replaceAll(result.list);
            }
        });
        // add event handlers
    }

    @Override
    public void addContact(Contact contact) {

    }

}