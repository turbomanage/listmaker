package com.example.listmaker.server.auth;

import com.example.listmaker.server.service.common.AppUserService;
import com.example.listmaker.server.service.common.AppUserServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.googlecode.objectify.Ref;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by david on 7/18/14.
 */
public abstract class LoginHelper {
    private static final Logger log = Logger.getLogger( LoginHelper.class.getName() );

    protected static final String APPLICATION_NAME = "Listmaker";
    private static final String APP_URL = "/listmaker/app/index.html";
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
        if (req.getServerPort() == 8888)
            q = "?gwt.codesvr=127.0.0.1:9997";
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
}
