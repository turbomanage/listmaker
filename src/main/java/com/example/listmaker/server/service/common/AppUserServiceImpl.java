package com.example.listmaker.server.service.common;

import ca.defuse.PasswordHash;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.common.domain.UserPrefs;
import com.example.listmaker.server.dao.UserDao;
import com.example.listmaker.server.dao.UserPrefsDao;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.googlecode.objectify.Ref;
import com.turbomanage.gwt.exception.TooManyResultsException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Util class has to be an impl in order to get Guice-injected PMF
 *
 * @author David Chandler
 */
public class AppUserServiceImpl implements AppUserService {

    private static final Logger LOG = Logger.getLogger(AppUserServiceImpl.class.getName());
    private static final String LOGIN_URL = "/";

    @Override
    public Ref<User> registerUser(User u) throws DuplicateUserException {
        Ref<User> userKey;

        UserDao userDao = new UserDao();
        // Check for duplicated registrations with this email address
        List<User> existingUsers = userDao.listByProperty("emailAddress", u.getEmailAddress());
        if (existingUsers.size() > 1) {
            throw new DuplicateUserException();
        } else if (existingUsers.size() == 1) {
            User e = existingUsers.get(0);
            // update existing profile
            e.setFirstName(u.getFirstName());
            e.setLastName(u.getLastName());
            if (u.getGoogleId() != null) {
                e.setGoogleId(u.getGoogleId());
            }
            if (u.getFacebookId() != null) {
                e.setFacebookId(u.getFacebookId());
            }
            e.setImgUrl(u.getImgUrl());
            userKey = Ref.create(userDao.put(e));
            LOG.info("Updated user " + e.getEmailAddress());
        } else {
            // create new user
            u.setDateCreated(new Date());
            userKey = Ref.create(userDao.put(u));
            LOG.info("Added user with id = " + u.getId() + " and email = " + u.getEmailAddress());

            // Add default prefs
            UserPrefsDao userPrefsDao = new UserPrefsDao();
            UserPrefs prefs = new UserPrefs();
            prefs.setOwnerKey(userKey);
            prefs.setWhos(new HashSet<String>());
            userPrefsDao.put(prefs);
        }
        return userKey;
    }

    @Override
    public User getRegisteredUser(String email) {
        UserDao userDao = new UserDao();
        User u;
        try {
            u = userDao.getByProperty("emailAddress", email);
            // verify password hash here
        } catch (TooManyResultsException e) {
            throw new RuntimeException(e);
        }
        return u;
    }

    @Override
    public User tryLogin(String email, String password) {
        UserDao userDao = new UserDao();
        User u;
        try {
            u = userDao.getByProperty("emailAddress", email);
            // verify password hash
            if (PasswordHash.validatePassword(password, u.getPasswordHash())) {
                return u;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
