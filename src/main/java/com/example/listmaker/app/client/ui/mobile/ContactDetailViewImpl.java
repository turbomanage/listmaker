package com.example.listmaker.app.client.ui.mobile;

import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.app.shared.domain.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Created by david on 3/10/15.
 */
public class ContactDetailViewImpl extends ViewImpl<ContactDetailView.Delegate> implements ContactDetailView {

    private Contact contact;

    interface ContactDetailViewImplUiBinder extends UiBinder<Widget, ContactDetailViewImpl> {}
    private static ContactDetailViewImplUiBinder ourUiBinder = GWT.create(ContactDetailViewImplUiBinder.class);

    public ContactDetailViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiField
    public TextField firstName;
    @UiField
    public TextField lastName;
    @UiField
    public TextField email;
    @UiField
    public TextButton saveButton;
    @UiField
    public TextButton cancelButton;

    @Override
    public Contact getContact() {
        return this.contact;
    }

    @Override
    public void setContact(Contact contact) {
        this.contact = contact;
        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
    }

    @UiHandler("saveButton")
    public void saveContact(SelectEvent e) {
        this.contact.setFirstName(firstName.getText());
        this.contact.setLastName(lastName.getText());
        getPresenter().saveContact(this.contact);
    }

    @UiHandler("cancelButton")
    public void cancel(SelectEvent e) {
        getPresenter().cancelEdit();
    }
}