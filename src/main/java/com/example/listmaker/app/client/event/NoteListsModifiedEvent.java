package com.example.listmaker.app.client.event;

import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.handler.NoteListsModifiedEventHandler;

public class NoteListsModifiedEvent extends GwtEvent<NoteListsModifiedEventHandler>
{

	public static final GwtEvent.Type<NoteListsModifiedEventHandler> TYPE = new GwtEvent.Type<NoteListsModifiedEventHandler>();
	private Map<Long, NoteList> noteLists;

	public NoteListsModifiedEvent(Map<Long, NoteList> noteLists)
	{
		this.noteLists = noteLists;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NoteListsModifiedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(NoteListsModifiedEventHandler handler)
	{
		handler.onNoteListsModified(noteLists);
	}

}
