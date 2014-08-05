package com.example.listmaker.app.client.activity;

import com.example.listmaker.app.client.AppConstants;
import com.example.listmaker.app.client.domain.Note;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.UIObject;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.event.NoteListAddedEvent;
import com.example.listmaker.app.client.event.NoteListsModifiedEvent;
import com.example.listmaker.app.client.handler.NoteListAddedHandler;
import com.example.listmaker.app.client.handler.NoteListsModifiedEventHandler;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.app.client.ui.web.content.AddNoteViewImpl;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.client.ui.web.View;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.ui.widget.HasSelectedValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gene on 6/5/2014.
 */
public class AddNoteActivity extends ActivityPresenter<AddNoteViewImpl> {

    public interface AddNoteView extends View<AddNoteActivity> {
        String getNewNoteSelectedList();

        HasSelectedValue<NoteList> getNoteListsBox();

        HasValue<String> getWhat();

        // bad, haven't solved pop-up anchor problem yet
        UIObject getNewListAnchor();

        void focusOnSaveButton();
    }

    private HomePlace place;

    public AddNoteActivity(HomePlace place) {
        this.place = place;
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(App.getClientFactory().getAddNoteView());
        super.start(acceptsOneWidget, eventBus);
        App.getEventBus().addHandler(NoteListsModifiedEvent.TYPE,
                new NoteListsModifiedEventHandler() {
                    @Override
                    public void onNoteListsModified(Map<Long, NoteList> noteLists) {
                        getView().stopProcessing();
                        populateLists(noteLists.values(), null);
                    }
                });
        App.getEventBus().addHandler(NoteListAddedEvent.TYPE, new NoteListAddedHandler() {
            @Override
            public void onNoteListAdded(ArrayList<NoteList> allLists, NoteList noteList) {
                populateLists(allLists, noteList);
                getView().focusOnSaveButton();
            }
        });
        // Fetch lists
        App.getNoteListService().refreshNoteLists(new AppCallback<ListResponse<NoteList>>(
                getView()) {
            @Override
            public void handleSuccess(ListResponse<NoteList> result) {
                getView().stopProcessing();
                populateLists(result.list, getSelectedList());
            }
        });
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

    public void createNewNoteListActivity() {
        NewNoteListActivity newNoteListActivity = new NewNoteListActivity(new HomePlace("-1"));
        newNoteListActivity.start();
        newNoteListActivity.getNewNoteListView().showRelativeTo(getView().getNewListAnchor());
    }

    private void populateLists(Collection<NoteList> lists, NoteList selected) {
        NoteList newList = GWT.create(NoteList.class);
        newList.name = App.getAppConstants().newListMenuText();
        newList.id = 0L;
        ArrayList<NoteList> options = new ArrayList<NoteList>();
        options.add(newList);
        options.addAll(lists);
        getView().getNoteListsBox().setSelections(options);
        if (selected != null) {
            getView().getNoteListsBox().setValue(selected);
        } else {
            getView().getNoteListsBox().clearSelectedValue();
        }
    }

    public void addNewNote() {
        // Create new note request
        long listId = Long.parseLong(getView().getNewNoteSelectedList());
        String what = getView().getWhat().getValue();
        Note item = new Note();
        item.itemText = what;
        App.getClientNoteItemService().addNote(getView(), listId, item);
        getView().reset();

    }

    public void checkFilter() {
        if (place.getToken() != null) {
            NoteList currentList = App.getNoteListService().getNoteList(Long.parseLong(place.getToken()));
            getView().getNoteListsBox().setValue(currentList, true);
        } else {
            getView().getNoteListsBox();
        }
    }
}
