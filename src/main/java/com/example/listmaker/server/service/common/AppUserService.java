package com.example.listmaker.server.service.common;

import com.example.listmaker.server.domain.UserSession;
import com.example.listmaker.server.domain.AuthCookie;
import com.googlecode.objectify.Ref;
import com.example.listmaker.app.shared.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;

public interface AppUserService
{
	Ref<User> registerUser(User u) throws DuplicateUserException;
    User getRegisteredUser(String email);
    User tryLogin(String email, String password);
    UserSession findSession(AuthCookie authCookie);
    void removeSession(UserSession userSession);
    UserSession replaceSession(User registeredUser, UserSession oldSession);
    void logout(UserSession userSession);
}
