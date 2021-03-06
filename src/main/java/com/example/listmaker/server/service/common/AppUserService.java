package com.example.listmaker.server.service.common;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.example.listmaker.common.domain.LoginInfo;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.example.listmaker.server.exception.UserNotRegisteredException;

import javax.ws.rs.NotAuthorizedException;

public interface AppUserService
{
	Ref<User> registerUser(User u) throws DuplicateUserException;
    User getRegisteredUser(String email);
    User tryLogin(String email, String password);
}
