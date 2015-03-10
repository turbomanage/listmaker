package com.example.listmaker.app.client.ui.mobile;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.ContactProperties;
import com.example.listmaker.app.client.place.ContactDetailPlace;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.common.domain.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;

/**
 * Created by david on 2/26/15.
 */
public class ContactsViewImpl extends ViewImpl<ContactsView.Delegate> implements ContactsView {

    // init store
    private final ContactProperties contactProperties = GWT.create(ContactProperties.class);
    private final ListStore<Contact> contactStore = new ListStore<Contact>(contactProperties.id());

    interface OurUiBinder extends UiBinder<Widget, ContactsViewImpl> { }
    private static OurUiBinder ourUiBinder = GWT.create(OurUiBinder.class);
    @UiField
    public ListView<Contact, Contact> contactListView;

    @UiFactory
    public ListView<Contact, Contact> makeListView() {
        ListView listView = new ListView(contactStore, new IdentityValueProvider<Contact>(), new ContactCell());
        listView.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                App.clientFactory().getPlaceController().goTo(new ContactDetailPlace(null));
            }
        }, ClickEvent.getType());
        return listView;
    }

    public ContactsViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public ListStore<Contact> getStore() {
        return contactStore;
    }

}
