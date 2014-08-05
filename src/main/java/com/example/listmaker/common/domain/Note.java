package com.example.listmaker.common.domain;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import com.googlecode.objectify.Ref;

@Entity
public class Note implements Serializable, Owned
{
	@Id private Long id;
    @JsonIgnore
	private Ref<NoteList> listKey;
	private String itemText;
    @Index @JsonIgnore
	private Ref<User> ownerKey;
	private Date dateCreated = new Date();

	public String getListName()
	{
		return listKey.get().getName();
	}

    public void setListName(String listName) {}

	public Note()
	{
		// Empty constructor for GWT-RPC
	}

	public Note(String itemText)
	{
		this.itemText = itemText;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getItemText()
	{
		return itemText;
	}

	public void setItemText(String itemText)
	{
		this.itemText = itemText;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Ref<NoteList> getListKey()
	{
		return listKey;
	}

    public void setListKey(Ref<NoteList> listKey)
	{
		this.listKey = listKey;
	}

    public long getListId() {
        return listKey.get().getId();
    }

    // for JSON
    public void setListId(long id) {
        this.listKey = Ref.create(Key.create(NoteList.class, id));
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
