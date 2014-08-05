/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.listmaker.server.auth;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.exception.DuplicateUserException;
import com.example.listmaker.server.exception.RestException;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Simple server to demonstrate how to use Google+ Sign-In and make a request
 * via your own server.
 *
 * @author joannasmith@google.com (Joanna Smith)
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Path("auth/g")
@Produces(MediaType.APPLICATION_JSON)
public class GPlus extends LoginHelper {

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    protected static final String CLIENT_ID = "GPLUS_CLIENT_ID";
    protected static final String CLIENT_SECRET = "GPLUS_CLIENT_SECRET";

    /**
     * Upgrade given auth code to token, and store it in the session.
     * POST body of request should be the authorization code.
     * Example URI: /login?code=
     */
    @GET
    @Path("login")
    public Response login(@QueryParam("code") String code,
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

        try {
            // Upgrade the authorization code into an access and refresh token.
            String callbackUri = getCallbackURI(req);
            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                            CLIENT_ID, CLIENT_SECRET, code, callbackUri).execute();

            // You can read the Google user ID in the ID token.
            // This sample does not use the user ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            String email = idToken.getPayload().getEmail();

            User user = appUserSvc.getRegisteredUser(email);
            if ((user == null) || user.getGoogleId() == null) {
                // Register new user
                User newUser = getUserInfo(tokenResponse);
                try {
                    user = registerUser(newUser);
                } catch (DuplicateUserException e) {
                    res.sendRedirect("/s/ar.html");
                }
            }
            AuthFilter.login(user, tokenResponse.toString());
            return Response.temporaryRedirect(new URI(getAppUrl(req))).build();
        } catch (TokenResponseException e) {
            throw new RestException(e);
        } catch (IOException e) {
            throw new RestException(e);
        } catch (URISyntaxException e) {
            throw new RestException(e);
        }
    }

    private User getUserInfo(GoogleTokenResponse accessToken) {
        try {
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(
                            accessToken.toString(), GoogleTokenResponse.class));
            // Create a new authorized API client.
            Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            // Get a list of people that this user has shared with this app.
            Person me = service.people().get("me").execute();

            User newUser = new User();
            GoogleIdToken idToken = accessToken.parseIdToken();
            newUser.setEmailAddress(idToken.getPayload().getEmail());
            newUser.setGoogleId(idToken.getPayload().getSubject());
            newUser.setFirstName(me.getName().getGivenName());
            newUser.setLastName(me.getName().getFamilyName());
            String imageUrl = me.getImage().getUrl();
            newUser.setImgUrl(imageUrl);

            return newUser;

//        PeopleFeed people = service.people().list("me", "visible").execute();
        } catch (IOException e) {
            throw new RestException(e);
        }

    }

    /**
 * Revoke current user's token and reset their session.
 */
//public static class DisconnectServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//
//        // Only disconnect a connected user.
//        String tokenData = (String) request.getSession().getAttribute("token");
//        if (tokenData == null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().print(GSON.toJson("Current user not connected."));
//            return;
//        }
//        try {
//            // Build credential from stored token data.
//            GoogleCredential credential = new GoogleCredential.Builder()
//                    .setJsonFactory(JSON_FACTORY)
//                    .setTransport(TRANSPORT)
//                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
//                    .setFromTokenResponse(JSON_FACTORY.fromString(
//                            tokenData, GoogleTokenResponse.class));
//            // Execute HTTP GET request to revoke current token.
//            HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
//                    .buildGetRequest(new GenericUrl(
//                            String.format(
//                                    "https://accounts.google.com/o/oauth2/revoke?token=%s",
//                                    credential.getAccessToken()))).execute();
//            // Reset the user's session.
//            request.getSession().removeAttribute("token");
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().print(GSON.toJson("Successfully disconnected."));
//        } catch (IOException e) {
//            // For whatever reason, the given token was invalid.
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().print(GSON.toJson("Failed to revoke token for given user."));
//        }
//    }
//}

/**
 * Get list of people user has shared with this app.
 */
    private Person listPeople() {

        String tokenData = AuthFilter.getToken();

        try {
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(
                            tokenData, GoogleTokenResponse.class));
            // Create a new authorized API client.
            Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            // Get a list of people that this user has shared with this app.
            Person me = service.people().get("me").execute();
//        PeopleFeed people = service.people().list("me", "visible").execute();
            return me;
        } catch (IOException e) {
            throw new RestException(e);
        }
    }
}