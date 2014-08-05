package com.example.listmaker.app.client.handler;

import com.example.listmaker.app.client.domain.Note;
import com.google.gwt.event.shared.EventHandler;

public interface NoteAddedEventHandler extends EventHandler
{
	void onNoteAdded(Note item);
}
