package com.example.listmaker.common.domain;

import com.googlecode.objectify.Ref;

/**
 * Created by david on 7/11/14.
 */
public interface Owned {

    public Ref<User> getOwnerKey();

    public void setOwnerKey(Ref<User> owner);

}
