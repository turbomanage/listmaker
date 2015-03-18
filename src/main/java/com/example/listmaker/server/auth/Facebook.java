package com.example.listmaker.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.listmaker.app.shared.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.example.listmaker.server.exception.RestException;
import com.turbomanage.gwt.server.servlet.AuthFilter;
import com.turbomanage.httpclient.BasicHttpClient;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by david on 7/18/14.
 */
@Path("auth/f")
public class Facebook extends LoginHelper {

    private static final String APP_ID = "FB_APP_ID";
    private static final String APP_SECRET = "FB_APP_SECRET";

    public static class FBUser {
        public String id;
        public String email;
        public String first_name;
        public String gender;
        public String last_name;
        public String link;
        public String locale;
        public String name;
        public int timezone;
        public String updated_time;
        public boolean verified;
    }

    public static class PicData {
        public PicInfo data;
    }

    public static class PicInfo {
        public String url;
        public String is_silhouette;
    }

    @GET
    @Path("login")
    public Response login(
            @QueryParam("code") String code,
            @Context HttpServletRequest req,
            @Context HttpServletResponse res) throws IOException {

        // already logged in
        if (AuthFilter.getUser() != null) {
            try {
                return Response.temporaryRedirect(new URI(getAppUrl(req))).build();
            } catch (URISyntaxException e) {
                throw new RestException(e);
            }
        }

        // exchange code for token
        String url = "https://graph.facebook.com/oauth/access_token";
        BasicHttpClient httpClient = new BasicHttpClient();
        HttpResponse fbResponse = httpClient.get(url, new ParameterMap()
                .add("client_id", APP_ID)
                .add("client_secret", APP_SECRET)
                .add("redirect_uri", getCallbackURI(req))
                .add("code", code));
        String[] params = fbResponse.getBodyAsString().split("&");
        String token = params[0].split("=")[1];

        url = "https://graph.facebook.com/me?access_token=" + token;
        HttpResponse json = httpClient.get(url, null);
        ObjectMapper om = new ObjectMapper();
        FBUser fbUser = om.readValue(json.getBodyAsString(), FBUser.class);
        String email = fbUser.email;

        User user = appUserSvc.getRegisteredUser(email);
        if ((user == null) || user.getFacebookId() == null) {
            // Register new user
            User newUser = getUserInfo(fbUser);
            try {
                user = registerUser(newUser);
            } catch (DuplicateUserException e) {
                res.sendRedirect("/s/ar.html");
            }
        }
        AuthFilter.login(user, token);
        try {
            return Response.temporaryRedirect(new URI(getAppUrl(req))).build();
        } catch (URISyntaxException e) {
            throw new RestException(e);
        }

    }

    private User getUserInfo(FBUser fbUser) {
        User newUser = new User();
        newUser.setEmailAddress(fbUser.email);
        newUser.setFirstName(fbUser.first_name);
        newUser.setLastName(fbUser.last_name);
        newUser.setFacebookId(fbUser.id);
        BasicHttpClient httpClient = new BasicHttpClient();
        String url = "https://graph.facebook.com/" + fbUser.id + "/picture?redirect=0&type=small";
        String picData = httpClient.get(url, null).getBodyAsString();
        ObjectMapper om = new ObjectMapper();
        try {
            String imgUrl = om.readValue(picData, PicData.class).data.url;
            newUser.setImgUrl(imgUrl);
        } catch (IOException e) {
            new RestException(e);
        }
        return newUser;
    }

}
