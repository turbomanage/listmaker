package com.example.listmaker.server.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.example.listmaker.common.domain.ListWrapper;
import com.example.listmaker.common.domain.Owned;
import com.example.listmaker.common.domain.User;
import com.turbomanage.gwt.exception.TooManyResultsException;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO for use with Objectify
 * 
 * @author turbomanage
 * 
 * @param <T>
 */
@Produces(MediaType.APPLICATION_JSON)
public class RestServiceDao<T extends Owned> extends ObjectifyDao<T>
{

    @Context
    ServletContext servletContext;

    public T getForOwner() {
        User user = AuthFilter.getUser();
        T obj = null;
        try {
            obj = this.getByOwner(user);
            return obj;
        } catch (TooManyResultsException e) {
            throw new WebApplicationException(e);
        }
    }

    public T get(Long id) {
        return verifyOwner(id);
    }

    public ListWrapper<T> findAll() {
        User user = AuthFilter.getUser();
        List<T> userAll = this.listByOwner(user);
        return new ListWrapper<T>(userAll);
    }

    public T save(T obj) {
        User user = AuthFilter.getUser();
        // prevent tampering
        obj.setOwnerKey(Ref.create(user));
        put(obj);
        return obj;
    }

    public int saveMany(List<T> list) {
        User user = AuthFilter.getUser();
        for (T obj: list) {
            obj.setOwnerKey(Ref.create(user));
            put(obj);
        }
        return list.size();
    }

    /**
     * Delete an object.
     *
     * @param id to delete
     * @return 0 on success, exception if failure
     */
    public int remove(long id) {
        User user = AuthFilter.getUser();
        // load from datastore to check owner key
        try {
            T obj = this.get(id);
            if (obj.getOwnerKey() == Ref.create(user)) {
                super.delete(obj);
            }
            return 0;
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    public int removeMany(List<Long> ids) {
        User user = AuthFilter.getUser();
        Ref<User> userRef = Ref.create(user);
        // Create List of Keys
        List<Key<T>> keys = new ArrayList<Key<T>>();
        for (long id : ids) {
            keys.add(Key.create(clazz, id));
        }
        Map<Key<T>, T> items = super.get(keys);
        for (T item : items.values()) {
            if (!item.getOwnerKey().equals(userRef)) {
                throw new NotAuthorizedException("User does not own the object");
            }
        }
        super.deleteKeys(keys);
        return keys.size();
    }

    protected T verifyOwner(long id) {
        User user = AuthFilter.getUser();
        T obj = super.get(id);
        if (obj.getOwnerKey().equals(Ref.create(user))) {
            return obj;
        }
        throw new NotAuthorizedException("This object does not belong to user");
    }

}