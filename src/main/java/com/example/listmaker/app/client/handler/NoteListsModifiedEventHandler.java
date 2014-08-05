package com.example.listmaker.app.client.handler;

import java.util.Map;

import com.google.gwt.event.shared.EventHandler;
import com.example.listmaker.app.client.domain.NoteList;

public interface NoteListsModifiedEventHandler extends EventHandler
{
	void onNoteListsModified(Map<Long, NoteList> noteLists);
}
