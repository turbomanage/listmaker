package com.example.listmaker.server.service.common;

public class AppUserServiceFactory
{
	private static final AppUserService INSTANCE = new AppUserServiceImpl();
	
	public static AppUserService getAppUserService()
	{
		return INSTANCE;
	}
}
