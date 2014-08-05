package com.example.listmaker.server.servlet;

import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.auth.LoginHelper;
import com.googlecode.objectify.Ref;
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
        User registeredUser = LoginHelper.getUserService().tryLogin(email, password);
        if (registeredUser == null) {
            // Invalid username or password
            resp.sendRedirect("/login.html");
            return;
        }
        // username and password matched
        AuthFilter.login(registeredUser, null);
        resp.sendRedirect(LoginHelper.getAppUrl(req));
    }

}
