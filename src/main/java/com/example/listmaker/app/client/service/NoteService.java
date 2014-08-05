package com.example.listmaker.app.client.service;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.Note;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.event.NoteAddedEvent;
import com.example.listmaker.app.client.event.NotesLoadedEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.turbomanage.gwt.client.Display;
import com.turbomanage.gwt.client.event.ShowMessageEvent;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.rest.RestApi;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoteService
{
    private static final NoteItemRestService service = GWT.create(NoteItemRestService.class);

    @Path("/api/note")
    public interface NoteItemRestService extends RestApi<Note> { }

    public void addNote(Display display, long listId, Note item)
    {
        NoteList noteList = App.getNoteListService().getNoteList(listId);
        // All are 0-based for consistency with GWT constants
        item.listId = listId;
        service.save(item, new AppCallback<Note>(display) {
            @Override
            public void handleSuccess(Note result) {
                App.getAppModel().getAllNotes().add(0, result);
                App.getEventBus().fireEvent(new ShowMessageEvent("Note saved."));
                App.getEventBus().fireEvent(new NotesLoadedEvent(App.getAppModel().getAllNotes()));
                App.getEventBus().fireEvent(new NoteAddedEvent(result));
            }
        });
    }

    public void editNoteItem(final Display display, long listId, Note item)
    {
        item.listId = listId;
        service.save(item,
                new AppCallback<Note>(display) {
                    @Override
                    public void handleSuccess(Note result) {
                        App.getAppModel().setAllNotes(null);
                        findNotes(display);
                        App.getEventBus().fireEvent(new ShowMessageEvent("Note updated."));
                    }
                });
    }

    public void findNotes(Display display) {
        List<Note> notes = App.getAppModel().getAllNotes();
        if (notes != null) {
            App.getEventBus().fireEvent(new NotesLoadedEvent(notes));
            return;
        }
        service.listAll(new AppCallback<ListResponse<Note>>() {
            @Override
            public void handleSuccess(ListResponse<Note> result) {
                List<Note> allNotes = result.list;
                App.getAppModel().setAllNotes(allNotes);
                App.getEventBus().fireEvent(new NotesLoadedEvent(allNotes));
            }
        });
    }

    public void deleteNotes(Display display, final Set<Note> notes)
    {
        List<Long> noteIds = new ArrayList<Long>();
        for (Note note: notes) {
            noteIds.add(note.id);
        }
		service.deleteMany(noteIds, new AppCallback<Integer>(display) {
            @Override
            public void handleSuccess(Integer result) {
                App.getEventBus().fireEvent(new ShowMessageEvent("Notes deleted"));
                List<Note> allNotes = App.getAppModel().getAllNotes();
                allNotes.removeAll(notes);
                App.getEventBus().fireEvent(new NotesLoadedEvent(allNotes));
            }
        });
    }

}
