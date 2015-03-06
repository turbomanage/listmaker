package com.example.listmaker.app.client.domain;

import com.example.listmaker.common.domain.Contact;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by david on 2/26/15.
 */
public interface ContactProperties extends PropertyAccess<Contact> {
    ModelKeyProvider<Contact> id();

    ValueProvider<Contact, String> firstName();

    ValueProvider<Contact, String> lastName();
}
