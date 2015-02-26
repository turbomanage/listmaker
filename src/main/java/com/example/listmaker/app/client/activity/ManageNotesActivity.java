package com.example.listmaker.app.client.activity;

import com.example.listmaker.app.client.domain.Note;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.event.NoteListAddedEvent;
import com.example.listmaker.app.client.event.NoteListsModifiedEvent;
import com.example.listmaker.app.client.event.NotesLoadedEvent;
import com.example.listmaker.app.client.handler.NoteListAddedHandler;
import com.example.listmaker.app.client.handler.NoteListsModifiedEventHandler;
import com.example.listmaker.app.client.handler.NotesLoadedEventHandler;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.ui.web.content.ManageNotesViewImpl;
import com.example.listmaker.app.client.ui.widget.NotesTable;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.client.ui.web.View;
import com.turbomanage.gwt.client.event.ShowMessageEvent;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.ui.widget.HasSelectedValue;

import java.util.*;

/**
 * Created by Gene on 6/5/2014.
 */
public class ManageNotesActivity extends ActivityPresenter<ManageNotesViewImpl> implements NotesTable.CanEdit {

    protected static NoteList SHOW_ALL = GWT.create(NoteList.class);
    protected Set<Note> selectedItems;
    private HomePlace place;

    public ManageNotesActivity(HomePlace place) {
        this.place = place;
        SHOW_ALL.id = AppStyles.SHOW_ALL_PROMPT;
        SHOW_ALL.name = "All lists";
    }

    public interface ManageNoteActivityView extends View<ManageNotesActivity>
    {
        HasSelectedValue<NoteList> getNoteListFilter();
        void enableBulkActions();
        void disableBulkActions();
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(App.getClientFactory().getManageNotesView());
        super.start(acceptsOneWidget, eventBus);
        populateNoteListFilter();
        getView().disableBulkActions();
        eventBus.addHandler(NotesLoadedEvent.TYPE, new NotesLoadedEventHandler() {
            @Override
            public void onNotesLoaded(List<Note> items) {
                NoteList currentList = App.getNoteListService().getNoteList(getView().getNoteListFilter().getValue().id);
                getView().getNotesTable().inputNotes(filterNotes(currentList));
                App.getEventBus().fireEvent(new ShowMessageEvent("NEW! Double click a note to edit!"));
            }
        });
        eventBus.addHandler(NoteListsModifiedEvent.TYPE, new NoteListsModifiedEventHandler() {
            @Override
            public void onNoteListsModified(Map<Long, NoteList> noteLists) {
                populateNoteListFilter();
            }
        });
        eventBus.addHandler(NoteListAddedEvent.TYPE, new NoteListAddedHandler() {
            @Override
            public void onNoteListAdded(ArrayList<NoteList> allLists, NoteList noteList) {
                populateNoteListFilter();
            }
        });
        App.getClientNoteItemService().findNotes(getView());
        getView().show();
    }

    private NoteList getSelectedList() {
        if (place != null) {
            try {
                Long listId = Long.parseLong(place.getToken());
                return App.getNoteListService().getNoteList(listId);
            } catch (Exception e) {
                // none selected
            }
        }
        return null;
    }

    @Override
    public void updateNote(Note item) {
        App.getClientNoteItemService().editNoteItem(getView(), item.listId, item);
    }

    public ArrayList<Note> filterNotes(NoteList currentList) {
        ArrayList<Note> filteredNotes = new ArrayList<Note>();
        for (Note note : App.getAppModel().getAllNotes()) {
            if (currentList == null || note.listName.equals(currentList.name) || currentList.equals(SHOW_ALL)) {
                filteredNotes.add(note);
            }
        }
        return filteredNotes;
    }

    public void checkBulkActions(Set<Note> items) {
        selectedItems = items;
        if (items.size() > 0) {
            getView().enableBulkActions();
        } else {
            getView().disableBulkActions();
        }
    }

    private void populateNoteListFilter()
    {
        App.getNoteListService().refreshNoteLists(new AppCallback<ListResponse<NoteList>>() {
            @Override
            public void handleSuccess(ListResponse<NoteList> result) {
                ArrayList<NoteList> noteLists = new ArrayList<NoteList>();
                noteLists.add(SHOW_ALL);
                noteLists.addAll(result.list);
                getView().getNoteListFilter().setSelections(noteLists);
                NoteList selectedList = getSelectedList();
                if (getSelectedList() != null) {
                    getView().getNoteListFilter().setValue(selectedList, false);
                } else {
                    getView().getNoteListFilter().clearSelectedValue();
                }
            }
        });
    }

    public void deleteSelectedNotes() {
        App.getClientNoteItemService().deleteNotes(getView(), selectedItems);
        getView().disableBulkActions();
    }
}
