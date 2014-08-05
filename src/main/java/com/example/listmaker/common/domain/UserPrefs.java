package com.example.listmaker.common.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;

@Entity
public class UserPrefs implements Serializable, Owned
{
	@Id
	private Long id;
    @Index @JsonIgnore
	private Ref<User> ownerKey;
	private Set<String> whos = new HashSet<String>();
	
	public UserPrefs()
	{
		// For GWT and Objectify
	}

	public Set<String> getWhos()
	{
		return whos;
	}

	public void setWhos(Set<String> whoList)
	{
		this.whos = whoList;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Ref<User> getOwnerKey()
	{
		return ownerKey;
	}

	public void setOwnerKey(Ref<User> ownerKey)
	{
		this.ownerKey = ownerKey;
	}
}
