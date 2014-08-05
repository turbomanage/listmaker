package com.example.listmaker.app.client.handler;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.example.listmaker.app.client.domain.NoteList;

public interface NoteListAddedHandler extends EventHandler
{
	void onNoteListAdded(ArrayList<NoteList> allLists, NoteList noteList);
}
