package com.example.listmaker.app.client.domain;

import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
//	private static final long serialVersionUID = -1126191336687818754L;
	
	// Objectify auto-generates Long IDs just like JDO / JPA
	public long id;
	public String firstName;
	public String lastName;
	@Index
    public String emailAddress;
	public String zipCode;
	public String googleId;
	public Date dateCreated = new Date();
    public String imgUrl;
}
