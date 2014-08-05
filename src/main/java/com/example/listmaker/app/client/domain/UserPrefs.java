package com.example.listmaker.app.client.domain;

import com.googlecode.objectify.annotation.Index;

import java.util.HashSet;
import java.util.Set;

//public class UserPrefs implements Serializable
public class UserPrefs
{
	public Long id;
    @Index
	public long ownerId;
	public Set<String> whos = new HashSet<String>();

}
