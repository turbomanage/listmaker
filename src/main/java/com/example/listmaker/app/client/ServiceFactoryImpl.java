package com.example.listmaker.app.client;

import com.example.listmaker.app.client.service.ContactService;
import com.google.gwt.core.shared.GWT;

/**
 * Created by david on 3/10/15.
 */
public class ServiceFactoryImpl implements ServiceFactory {

    private static final ContactService contactService = GWT.create(ContactService.class);

    @Override
    public ContactService contactService() {
        return contactService;
    }

}
