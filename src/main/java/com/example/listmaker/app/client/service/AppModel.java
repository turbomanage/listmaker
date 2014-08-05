package com.example.listmaker.app.client.service;

import com.example.listmaker.app.client.domain.Note;
import com.example.listmaker.app.client.domain.User;

import java.util.List;

/**
 * Singleton that represents the current state of the UI. Getters
 * are public to be called from various other presenters, whereas
 * setters are package access to be called only by service facades.
 * Setters may fire an event to notify listening widgets that the
 * model has been updated.
 * 
 * @author David Chandler
 */
public class AppModel
{
    private User me;
	private List<Note> allNotes;

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

	public List<Note> getAllNotes()
	{
		return allNotes;
	}

	public void setAllNotes(List<Note> allNotes)
	{
		this.allNotes = allNotes;
	}

}
