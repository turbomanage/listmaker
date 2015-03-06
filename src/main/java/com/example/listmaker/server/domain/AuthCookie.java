package com.example.listmaker.server.domain;

import com.example.listmaker.server.auth.LoginHelper;

import javax.servlet.http.Cookie;

/**
 * Created by david on 3/5/15.
 */
public class AuthCookie {

    public static final int MAX_AGE = 3600 * 24 * 30; // 30 days
    public static final String COOKIE_NAME = "authCookie";
    private static final String TOKEN_SEPARATOR = ":";

    private final String sessionId;

    private final String username;

    public AuthCookie(String sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;
    }

    /**
     * Parse the Cookie into its parts
     *
     * @param cookie
     */
    public AuthCookie (Cookie cookie) {
        if (cookie != null) {
            String cookieValue = cookie.getValue();
            String[] split = cookieValue.split(TOKEN_SEPARATOR);
            this.sessionId = split[0];
            this.username = split[1];
        } else {
            this.sessionId = null;
            this.username = null;
        }
    }

    /**
     * Make a new Cookie from the username & sessionId
     *
     * @return
     */
    public Cookie getCookie() {
        Cookie cookie = new Cookie(LoginHelper.AUTH_COOKIE_KEY, sessionId + TOKEN_SEPARATOR + username);
        cookie.setPath("/");
        // Should happen automatically on HTTPs and debugging is easier without it
//        cookie.setSecure(true);
        cookie.setMaxAge(MAX_AGE);
        return cookie;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
