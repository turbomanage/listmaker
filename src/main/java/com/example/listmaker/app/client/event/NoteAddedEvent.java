package com.example.listmaker.app.client.event;

import com.example.listmaker.app.client.domain.Note;
import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.handler.NoteAddedEventHandler;

public class NoteAddedEvent extends GwtEvent<NoteAddedEventHandler>
{

	public static final GwtEvent.Type<NoteAddedEventHandler> TYPE = new GwtEvent.Type<NoteAddedEventHandler>();
	
	private Note newNote;

	public NoteAddedEvent(Note note)
	{
		this.newNote = note;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NoteAddedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(NoteAddedEventHandler handler)
	{
		handler.onNoteAdded(this.newNote);
	}

}
