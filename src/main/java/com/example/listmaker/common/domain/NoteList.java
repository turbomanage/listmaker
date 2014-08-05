package com.example.listmaker.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Index;

@Entity @Cache
public class NoteList implements Serializable, Owned {
	@Id
	private Long id;
	private String name;
	@Index @JsonIgnore
    private Ref<User> ownerKey;
    @Index @JsonIgnore
	private Date dateCreated = new Date();
	private transient List<Note> notes;

	public List<Note> getItems() {
		return notes;
	}

	public void setItems(List<Note> items) {
		this.notes = items;
	}

	// Empty constructor needed for GWT serialization
	public NoteList()
	{

	}

	public NoteList(long id, String name)
	{
		this();
		setId(id);
		setName(name);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

    public Ref<User> getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(Ref<User> ownerKey) {
        this.ownerKey = ownerKey;
    }

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

}
