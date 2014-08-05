package com.example.listmaker.app.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.event.NoteListAddedEvent;
import com.example.listmaker.app.client.event.NoteListsModifiedEvent;
import com.example.listmaker.app.client.handler.NoteListAddedHandler;
import com.example.listmaker.app.client.handler.NoteListsModifiedEventHandler;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.client.ui.web.View;
import com.turbomanage.gwt.client.rest.ListResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gene on 6/5/2014.
 */
public class NavActivity extends ActivityPresenter<NavActivity.NavView> {

    public NavActivity(Place place) {
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(App.getClientFactory().getNavView());
        super.start(acceptsOneWidget, eventBus);

        listenForListChanges();
        App.getNoteListService().refreshNoteLists(new AppCallback<ListResponse<NoteList>>() {
            @Override
            public void handleSuccess(ListResponse<NoteList> result) {
                getView().populateLists(result.list);
            }
        });
    }

    public interface NavView extends View<NavActivity>
    {
        void populateLists(Collection<NoteList> collection);
    }

    void listenForListChanges()
    {
        App.getEventBus().addHandler(NoteListsModifiedEvent.TYPE, new NoteListsModifiedEventHandler()
        {
            @Override
            public void onNoteListsModified(Map<Long, NoteList> noteLists)
            {
                getView().populateLists(noteLists.values());
            }
        });
        App.getEventBus().addHandler(NoteListAddedEvent.TYPE, new NoteListAddedHandler() {
            @Override
            public void onNoteListAdded(ArrayList<NoteList> allLists, NoteList noteList) {
                getView().populateLists(allLists);
            }
        });
    }
}
