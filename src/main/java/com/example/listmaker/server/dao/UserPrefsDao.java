package com.example.listmaker.server.dao;

import java.util.logging.Logger;

import com.example.listmaker.common.domain.UserPrefs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("api/userPrefs")
public class UserPrefsDao extends RestServiceDao<UserPrefs>
{
	private static final Logger LOG = Logger.getLogger(UserPrefsDao.class.getName());

    @Override
    @Path("own")
    @GET
    public UserPrefs getForOwner() {
        return super.getForOwner();
    }
}
