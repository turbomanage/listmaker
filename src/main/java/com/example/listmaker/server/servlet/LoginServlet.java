package com.example.listmaker.server.servlet;

import com.example.listmaker.common.domain.User;
import com.example.listmaker.common.domain.UserSession;
import com.example.listmaker.server.auth.LoginHelper;
import com.example.listmaker.server.domain.AuthCookie;
import com.example.listmaker.server.service.common.AppUserService;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by david on 8/4/14.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Start new session
        req.getSession().invalidate();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberLogin = req.getParameter("remember");
        AppUserService userService = LoginHelper.getUserService();
        User registeredUser = userService.tryLogin(email, password);
        if (registeredUser == null) {
            // Invalid username or password
            resp.sendRedirect("/login.html");
            return;
        }
        // username and password matched
        AuthFilter.login(registeredUser, null);
        // Store persistent cookie if "Remember me" checked
        if (rememberLogin != null) {
            AuthCookie oldAuthCookie = LoginHelper.getAuthCookie(req);
            UserSession oldSession = userService.findSession(oldAuthCookie);
            UserSession newSession = userService.replaceSession(registeredUser, oldSession);
            AuthCookie newAuthCookie = LoginHelper.makeSessionCookie(newSession);
            resp.addCookie(newAuthCookie.getCookie());
        }
        resp.sendRedirect(LoginHelper.getAppUrl(req));
    }

}
