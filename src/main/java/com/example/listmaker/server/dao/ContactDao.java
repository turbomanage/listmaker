package com.example.listmaker.server.dao;

import com.example.listmaker.app.shared.domain.Contact;
import com.example.listmaker.app.shared.domain.User;
import com.example.listmaker.common.domain.ListWrapper;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.logging.Logger;

@Path("contact")
public class ContactDao extends RestServiceDao<Contact>
{
	private static final Logger LOG = Logger.getLogger(ContactDao.class.getName());

    @Override
    @Path("all")
    @GET
    public ListWrapper<Contact> findAll() {
        User user = AuthFilter.getUser();
        List<Contact> notes = this.listByOwner(user);
        return new ListWrapper<Contact>(notes);
    }

    @Override
    @Path("save")
    @POST
    public Contact save(Contact obj) {
        LOG.info("contact id = " + obj.getId());
        return super.save(obj);
    }
}