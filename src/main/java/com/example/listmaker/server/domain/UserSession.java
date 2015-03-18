package com.example.listmaker.server.domain;

import com.example.listmaker.app.shared.domain.Owned;
import com.example.listmaker.app.shared.domain.User;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

/**
 * Represents a persistent "remember me" session.
 *
 * Created by david on 3/5/15.
 */
@Entity
public class UserSession implements Owned {
    @Id
    private Long id;
    @Index
    private Ref<User> userKey;
    @Ignore
    private String sessionId;
    @Index
    private String sessionHash;
    @Index
    private String username;

    public UserSession() {
        // for Objectify
    }

    public UserSession(Ref<User> userKey, String sessionId, String sessionHash, String username) {
        this.userKey = userKey;
        this.sessionId = sessionId;
        this.sessionHash = sessionHash;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Ref<User> getOwnerKey() {
        return userKey;
    }

    @Override
    public void setOwnerKey(Ref<User> owner) {
        this.userKey = owner;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
