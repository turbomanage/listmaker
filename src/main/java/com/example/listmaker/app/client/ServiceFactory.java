package com.example.listmaker.app.client;

import com.example.listmaker.app.client.service.ContactService;

/**
 * Created by david on 3/10/15.
 */
public interface ServiceFactory {

    ContactService contactService();
}
