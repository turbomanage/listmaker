package com.example.listmaker.app.client.ui.mobile;

import com.example.listmaker.app.client.api.ContactsView;
import com.example.listmaker.app.client.domain.ContactProperties;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.common.domain.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

/**
 * Created by david on 2/26/15.
 */
public class ContactsViewImpl extends ViewImpl<ContactsView.Delegate> implements ContactsView {

    // TODO Rubén replace GXT stuff with GWT CellTable

    // init store
    private final ContactProperties contactProperties = GWT.create(ContactProperties.class);
    private final ListStore<Contact> contactStore = new ListStore<Contact>(contactProperties.id());

    interface OurUiBinder extends UiBinder<Widget, ContactsViewImpl> { }
    private static OurUiBinder ourUiBinder = GWT.create(OurUiBinder.class);
    @UiField
    public ListView<Contact, Contact> contactListView;

    @UiFactory
    public ListView<Contact, Contact> makeListView() {
        return new ListView(contactStore, new IdentityValueProvider<Contact>(), new ContactCell());
    }

    public ContactsViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public ListStore<Contact> getStore() {
        return contactStore;
    }

}
