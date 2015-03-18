package com.turbomanage.gwt.server.servlet;

import com.example.listmaker.app.shared.domain.User;
import com.example.listmaker.server.domain.UserSession;
import com.example.listmaker.server.auth.LoginHelper;
import com.example.listmaker.server.domain.AuthCookie;
import com.example.listmaker.server.service.common.AppUserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


public class AuthFilter implements Filter {
    public static final String LOGIN_FORM = "/login.html";
    public static final String USER_KEY = "loggedInUser";
    public static final String TOKEN = "token";

    private ServletContext servletContext;
    private static final ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) resp;
        perThreadRequest.set(httpReq);
        String path = httpReq.getRequestURI().substring(((HttpServletRequest) req).getContextPath().length());
        if (path.startsWith("/listmaker/login")
                || path.startsWith("/listmaker/signup")
                || path.startsWith("/listmaker/remote_logging")) {
            // login URLs don't require auth
            chain.doFilter(req, resp);
        } else {
            // require logged in user
            if (getUser() != null) {
                chain.doFilter(req, resp);
            } else {
                // not logged in
                // if an API call, return JSON response
                if (path.startsWith("/listmaker/api")) {
                    ((HttpServletResponse) resp).setStatus(401);
                    resp.setContentType(MediaType.TEXT_PLAIN);
                    resp.getWriter().write("User must log in");
                } else if (refreshPersistentSession(httpReq, httpRes)) {
                    chain.doFilter(req, resp);
                } else {
                    // otherwise redirect
                    httpRes.sendRedirect(LOGIN_FORM);
                }
            }
        }
    }

    /**
     * Attempt to login using the authCookie. If successful, refresh the authCookie with new sessionId.
     *
     * @param httpReq
     * @param httpRes
     * @return true if authCookie is valid
     */
    private boolean refreshPersistentSession(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        AuthCookie authCookie = LoginHelper.getAuthCookie(httpReq);
        if (authCookie != null) {
            AppUserService userService = LoginHelper.getUserService();
            UserSession userSession = userService.findSession(authCookie);
            if (userSession != null) {
                // login and refresh persistent session
                User authUser = userSession.getOwnerKey().get();
                login(authUser, null);
                UserSession newSession = userService.replaceSession(authUser, userSession);
                AuthCookie newAuthCookie = LoginHelper.makeSessionCookie(newSession);
                httpRes.addCookie(newAuthCookie.getCookie());
                return true;
            }
        }
        return false;
    }

    public static void login(User u, String accessToken) {
        perThreadRequest.get().getSession().setAttribute(USER_KEY, u);
        perThreadRequest.get().getSession().setAttribute(TOKEN, accessToken);
    }

    public static void logout() {
        perThreadRequest.get().getSession().invalidate();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void destroy() {
        servletContext = null;
    }

    public static User getUser() {
        return (User) perThreadRequest.get().getSession().getAttribute(USER_KEY);
    }

    public static String getToken() {
        return (String) perThreadRequest.get().getSession().getAttribute(TOKEN);
    }

    /**
     * Inject a mock servlet request in test environment
     *
     * @param mockServletRequest
     * @param user
     */
// TODO eliminate this method--create alt filter impl for testing
    public static void testLogin(HttpServletRequest mockServletRequest, User user) {
        perThreadRequest.set(mockServletRequest);
        login(user, null);
    }
}