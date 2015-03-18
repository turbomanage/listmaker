package com.example.listmaker.server.dao;

import com.example.listmaker.server.domain.UserSession;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 * Created by david on 3/5/15.
 */
public class UserSessionDao extends ObjectifyDao<UserSession> {

    public UserSession find(String sessionHash, String username) {
        QueryResultIterator<UserSession> sessions = ofy().load().type(UserSession.class)
                .filter("sessionHash", sessionHash)
                .filter("username", username)
                .iterator();
        if (sessions.hasNext()) {
            // Record found
            return sessions.next();
        }
        return null;
    }


}
