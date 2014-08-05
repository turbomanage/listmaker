package com.example.listmaker.app.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.example.listmaker.app.client.domain.NoteList;

public interface NoteListModifiedEventHandler extends EventHandler
{

	void onNoteItemsModified(NoteList list);

}
