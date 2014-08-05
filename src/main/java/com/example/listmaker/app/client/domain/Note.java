package com.example.listmaker.app.client.domain;

import com.googlecode.objectify.annotation.Unindex;

import java.util.Date;

//public class Note implements Serializable
public class Note
{
	public Long id;
	public Long listId;
	public String itemText;
	public Date dateCreated = new Date();
	// Intentionally denormalized,
	@Unindex public String listName;

}
