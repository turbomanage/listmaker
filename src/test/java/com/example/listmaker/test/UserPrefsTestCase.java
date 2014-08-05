package com.example.listmaker.test;

import com.example.listmaker.common.domain.User;
import com.example.listmaker.common.domain.UserPrefs;
import com.example.listmaker.server.dao.UserPrefsDao;
import com.example.listmaker.test.helper.BaseTest;
import com.turbomanage.gwt.server.servlet.AuthFilter;


public class UserPrefsTestCase extends BaseTest
{
	User u;
    UserPrefsDao dao = new UserPrefsDao();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		u = AuthFilter.getUser();
	}

	public void testUserPrefsCreated() throws Exception
	{
        UserPrefs prefs = dao.getForOwner();
        assertNotNull(prefs);
		assertNotNull(prefs.getWhos());
	}

}
