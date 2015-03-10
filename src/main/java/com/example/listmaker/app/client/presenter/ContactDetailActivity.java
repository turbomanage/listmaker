package com.example.listmaker.app.client.presenter;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.place.ContactDetailPlace;
import com.example.listmaker.app.client.ui.mobile.ContactDetailView;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Created by david on 3/10/15.
 */
public class ContactDetailActivity extends ActivityPresenter<ContactDetailView> implements Activity {

    private final int contactIndex;

    public ContactDetailActivity(ContactDetailPlace place) {
        // Extract id from token and find this record
        contactIndex = place.getSelectedContactIdx();
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        bind(App.clientFactory().getContactDetailView());
        super.start(acceptsOneWidget, eventBus);
        getView().setContact(App.model().getContactStore().get(contactIndex));
    }
}
