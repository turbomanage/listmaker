package com.example.listmaker.app.client.handler;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.example.listmaker.app.client.domain.Note;

public interface NotesLoadedEventHandler extends EventHandler
{
	void onNotesLoaded(List<Note> notes);
}
