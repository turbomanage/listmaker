package com.example.listmaker.server.dao;

import java.util.Date;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.example.listmaker.common.domain.User;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public class UserDao extends ObjectifyDao<User>
{
	private static final Logger LOG = Logger.getLogger(UserDao.class.getName());

    @GET
    @Path("me")
    public User getMe() {
        return AuthFilter.getUser();
    }

	@Override
	public Key<User> put(User entity)
	{
		// Ensure date created is set
		entity.setDateCreated(new Date());
		return super.put(entity);
	}

}
