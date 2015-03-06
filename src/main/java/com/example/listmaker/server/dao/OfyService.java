package com.example.listmaker.server.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.example.listmaker.common.domain.*;

/**
 * Created by david on 7/3/14.
 */
public class OfyService {

    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(UserSession.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}