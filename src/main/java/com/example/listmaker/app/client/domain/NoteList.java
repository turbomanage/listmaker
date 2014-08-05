package com.example.listmaker.app.client.domain;

import java.util.Date;
import java.util.List;

public class NoteList {
	public Long id;
	public String name;
	public Date dateCreated = new Date();
	public transient List<Note> notes;
}
