package com.example.listmaker.server.service.common;

import ca.defuse.PasswordHash;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.common.domain.UserSession;
import com.example.listmaker.server.dao.UserDao;
import com.example.listmaker.server.dao.UserSessionDao;
import com.example.listmaker.server.domain.AuthCookie;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.googlecode.objectify.Ref;
import com.turbomanage.gwt.exception.TooManyResultsException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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
//            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public UserSession findSession(AuthCookie authCookie) {
        if (authCookie != null) {
            String sessionId = authCookie.getSessionId();
            String sessionHash = md5(sessionId);
            String username = authCookie.getUsername();
            UserSessionDao dao = new UserSessionDao();
            UserSession userSession = dao.find(sessionHash, username);
            return userSession;
        }
        return null;
    }

    @Override
    public UserSession replaceSession(User registeredUser, UserSession oldSession) {
        // Generate new persistent session for user
        UserSession newSession = newSession(registeredUser);
        UserSessionDao dao = new UserSessionDao();
        dao.put(newSession);
        // Delete old session if found
        removeSession(oldSession);
        return newSession;
    }

    @Override
    public void logout(UserSession userSession) {

    }

    private UserSession newSession(User u) {
        Ref<User> userKey = Ref.create(u);
        String username = u.getEmailAddress();
        String sessionId = UUID.randomUUID().toString();
        String sessionHash = md5(sessionId);
        UserSession newSession = new UserSession(userKey, sessionId, sessionHash, username);
        return newSession;
    }

    public void removeSession(UserSession oldSession) {
        if (oldSession != null)
        {
            new UserSessionDao().delete(oldSession);
        }
    }

    public static String md5(String sessionId) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(sessionId.getBytes());
            String hash = new BigInteger(digest).toString(36);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}