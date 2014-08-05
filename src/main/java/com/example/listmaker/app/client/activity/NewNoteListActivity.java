package com.example.listmaker.app.client.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.UIObject;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.ui.web.content.NewNoteListViewImpl;
import com.example.listmaker.common.client.ui.web.View;

/**
 * Created by Gene on 6/5/2014.
 */
public class NewNoteListActivity extends PopupPresenter<NewNoteListViewImpl> {

    public interface NewNoteListView extends View<NewNoteListActivity>
    {
        void showRelativeTo(UIObject target);
    }

    public NewNoteListActivity(Place place) {
    }

    @Override
    public void start() {
        setView(getNewNoteListView());
        super.start();
    }

    public NewNoteListViewImpl getNewNoteListView () {
        return App.getClientFactory().getNewNoteListView();
    }

    public void addNoteList()
    {
        String newListName = getView().getNewListName().getValue();
        if ((newListName != null) && newListName.length() > 0) {
            NoteList newList = GWT.create(NoteList.class);
            newList.name = newListName;
            App.getNoteListService().actionAddList(getView(), newList);
        }
    }

}
