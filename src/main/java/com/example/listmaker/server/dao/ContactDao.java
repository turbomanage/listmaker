package com.example.listmaker.server.dao;

import com.example.listmaker.common.domain.Contact;
import com.example.listmaker.common.domain.ListWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.logging.Logger;

@Path("contact")
public class ContactDao extends RestServiceDao<Contact>
{
	private static final Logger LOG = Logger.getLogger(ContactDao.class.getName());

    @Override
    @Path("all")
    @GET
    public ListWrapper<Contact> findAll() {
//        User user = AuthFilter.getUser();
//        List<Note> notes = this.listByOwner(user);
        return new ListWrapper<Contact>(ContactsReader.all(servletContext));
    }

}