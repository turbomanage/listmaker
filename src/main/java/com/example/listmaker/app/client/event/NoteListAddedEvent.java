package com.example.listmaker.app.client.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.handler.NoteListAddedHandler;

public class NoteListAddedEvent extends GwtEvent<NoteListAddedHandler>
{

	public static final GwtEvent.Type<NoteListAddedHandler> TYPE = new GwtEvent.Type<NoteListAddedHandler>();
	private NoteList noteList;
	private ArrayList<NoteList> allLists;
	
	public NoteListAddedEvent(ArrayList<NoteList> allLists, NoteList noteList)
	{
		this.noteList = noteList;
		this.allLists = allLists;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NoteListAddedHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(NoteListAddedHandler handler)
	{
		handler.onNoteListAdded(this.allLists, this.noteList);
	}

}
