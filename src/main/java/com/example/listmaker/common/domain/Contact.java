package com.example.listmaker.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 2/26/15.
 */
@Entity
public class Contact implements Owned {

    @Id
    private long id;
    @JsonIgnore
    private Ref<User> ownerKey;
    private String firstName;
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Ref<User> getOwnerKey() {
        return ownerKey;
    }

    @Override
    public void setOwnerKey(Ref<User> owner) {
        ownerKey = owner;
    }
}
