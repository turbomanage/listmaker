package com.example.listmaker.app.client.event;

import java.util.List;

import com.example.listmaker.app.client.domain.Note;
import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.handler.NotesLoadedEventHandler;

public class NotesLoadedEvent extends GwtEvent<NotesLoadedEventHandler>
{

	public static final GwtEvent.Type<NotesLoadedEventHandler> TYPE = new GwtEvent.Type<NotesLoadedEventHandler>();
	
	private List<Note> notes;

	public NotesLoadedEvent(List<Note> allNotes)
	{
		this.notes = allNotes;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NotesLoadedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(NotesLoadedEventHandler handler)
	{
		handler.onNotesLoaded(this.notes);
	}

}
