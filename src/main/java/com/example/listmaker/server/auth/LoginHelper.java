package com.example.listmaker.server.auth;

import com.example.listmaker.common.domain.UserSession;
import com.example.listmaker.server.domain.AuthCookie;
import com.example.listmaker.server.service.common.AppUserService;
import com.example.listmaker.server.service.common.AppUserServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.googlecode.objectify.Ref;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by david on 7/18/14.
 */
public abstract class LoginHelper {
    private static final Logger log = Logger.getLogger( LoginHelper.class.getName() );

    protected static final String APPLICATION_NAME = "Listmaker";
    private static final String APP_URL = "/listmaker/index.jsp";
    public static final String AUTH_COOKIE_KEY = "authCookie";
    protected static final AppUserService appUserSvc = new AppUserServiceImpl();

    protected User registerUser(User newUser) throws DuplicateUserException {
            Ref<User> userRef = appUserSvc.registerUser(newUser);
            return userRef.get();
    }

    public static AppUserService getUserService() {
        return appUserSvc;
    }

    public static String getAppUrl(HttpServletRequest req) {
        String q = "";
//        if (req.getServerPort() == 8888)
//            q = "?gwt.codesvr=127.0.0.1:9997";
        return APP_URL + q;
    }

    public String getCallbackURI(HttpServletRequest req) {
        String port = "";
        if (req.getServerPort() != 80) {
            port = ":" + req.getServerPort();
        }
        String uri = req.getScheme() + "://" + req.getServerName() + port + req.getRequestURI();
        log.info("Callback URI: " + uri);
        return uri;
    }

    /**
     * Gets the named cookie from the request
     *
     * @param req
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns the value of the current authCookie from the request
     *
     * @param req
     * @return
     */
    public static AuthCookie getAuthCookie(HttpServletRequest req) {
        Cookie cookie = getCookie(req, AuthCookie.COOKIE_NAME);
        if (cookie != null) {
            return new AuthCookie(cookie);
        }
        return null;
    }

    public static AuthCookie makeSessionCookie(UserSession newSession) {
        User user = newSession.getOwnerKey().get();
        return new AuthCookie(newSession.getSessionId(), user.getEmailAddress());
    }
}