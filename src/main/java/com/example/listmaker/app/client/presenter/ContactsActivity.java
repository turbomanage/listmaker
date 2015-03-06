package com.example.listmaker.app.client.presenter;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.api.ContactsView;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.domain.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.rest.RestApi;

import javax.ws.rs.Path;

/**
 * Created by david on 2/26/15.
 */
public class ContactsActivity extends ActivityPresenter<ContactsView> implements ContactsView.Delegate {

    // init service
    @Path("/api/contact")
    public interface ContactService extends RestApi<Contact> { }
    private static final ContactService svc = GWT.create(ContactService.class);

    public ContactsActivity() {

    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        bind(App.getClientFactory().getContactsView());
        super.start(acceptsOneWidget, eventBus);
        svc.listAll(new AppCallback<ListResponse<Contact>>() {
            @Override
            public void handleSuccess(ListResponse<Contact> result) {
                getView().getStore().replaceAll(result.list);
            }
        });
        // add event handlers
    }

    @Override
    public void addContact(Contact contact) {

    }

}