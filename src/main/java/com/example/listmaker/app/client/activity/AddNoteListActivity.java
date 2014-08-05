package com.example.listmaker.app.client.activity;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.ui.web.content.AddNoteListViewImpl;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.client.ui.web.View;

/**
 * Created by Gene on 6/5/2014.
 */
public class AddNoteListActivity extends ActivityPresenter<AddNoteListViewImpl> {


    public interface AddNoteListView extends View<AddNoteListActivity>
    {
        HasValue<String> getListName();
    }

    public AddNoteListActivity(HomePlace place) {

        // Clear form
        getView().getListName().setValue("");
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(App.getClientFactory().getAddNoteListView());
        super.start(acceptsOneWidget, eventBus);
    }

    public void addNewNoteList() {
        NoteList newList = GWT.create(NoteList.class);
        newList.name = getView().getListName().getValue().toString();
        App.getNoteListService().actionAddList(getView(), newList);
    }

}
