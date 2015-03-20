package com.example.listmaker.app.client.service;

import com.example.listmaker.app.shared.domain.Contact;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.rest.RestApi;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.dispatcher.DefaultDispatcher;
import org.fusesource.restygwt.client.dispatcher.DefaultFilterawareDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by david on 2/26/15.
 */
@Path("api/contact")
public interface ContactService extends RestApi<Contact> {

    @Override
    @GET
    @Path("all")
    @Options(dispatcher = DefaultDispatcher.class) // no cache
    void listAll(MethodCallback<ListResponse<Contact>> callback);

}
