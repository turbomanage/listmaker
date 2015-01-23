package com.turbomanage.gwt.server.servlet;

import com.example.listmaker.common.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


public class AuthFilter implements Filter {
    private static final String LOGIN_FORM = "/login.html";
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
                // if an API call, return JSON response
                if (path.startsWith("/listmaker/api")) {
                    ((HttpServletResponse) resp).setStatus(401);
                    resp.setContentType(MediaType.TEXT_PLAIN);
                    resp.getWriter().write("User must log in");
                } else {
                    // otherwise redirect
                    httpRes.sendRedirect(LOGIN_FORM);
                }
            }
        }
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