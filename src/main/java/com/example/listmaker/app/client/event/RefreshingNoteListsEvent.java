package com.example.listmaker.app.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.app.client.handler.RefreshingNoteListsEventHandler;

public class RefreshingNoteListsEvent extends GwtEvent<RefreshingNoteListsEventHandler>
{

	public static final GwtEvent.Type<RefreshingNoteListsEventHandler> TYPE = new Type<RefreshingNoteListsEventHandler>();
	
	@Override
	protected void dispatch(RefreshingNoteListsEventHandler handler)
	{
		handler.updateNoteLists();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RefreshingNoteListsEventHandler> getAssociatedType()
	{
		return TYPE;
	}

}
