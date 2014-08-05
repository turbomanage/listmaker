package com.example.listmaker.app.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.handler.NoteListModifiedEventHandler;

public class NoteListModifiedEvent extends GwtEvent<NoteListModifiedEventHandler>
{

	public static final GwtEvent.Type<NoteListModifiedEventHandler> TYPE = new GwtEvent.Type<NoteListModifiedEventHandler>();
	
	private NoteList list;

	public NoteListModifiedEvent(NoteList list)
	{
		this.list = list;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NoteListModifiedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(NoteListModifiedEventHandler handler)
	{
		handler.onNoteItemsModified(list);
	}

}
