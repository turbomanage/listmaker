package com.example.listmaker.server.servlet;

import ca.defuse.PasswordHash;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.auth.LoginHelper;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.example.listmaker.server.service.common.AppUserService;
import com.example.listmaker.server.service.common.AppUserServiceFactory;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

public class NewUserServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");

        User newUser = new User();
        newUser.setDateCreated(new Date());
        // TODO validation
        newUser.setEmailAddress(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        try {
            String hash = PasswordHash.createHash(password);
            newUser.setPasswordHash(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        AppUserService appUserService = AppUserServiceFactory.getAppUserService();
        try
        {
            appUserService.registerUser(newUser);
            AuthFilter.login(newUser, null);
            resp.sendRedirect(LoginHelper.getAppUrl(req));
        }
        catch (DuplicateUserException e)
        {
            resp.sendRedirect("/s/already_registered.html");
            return;
        }
    }

    private boolean isAlphaNumeric(String s)
    {
        char[] sChars = s.toCharArray();
        for (char c : sChars)
        {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

}