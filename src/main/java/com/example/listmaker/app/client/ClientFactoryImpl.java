package com.example.listmaker.app.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.example.listmaker.app.client.ui.web.content.*;
import com.example.listmaker.app.client.ui.web.nav.NavViewImpl;

/**
 * Manages instances of Views.
 * Created by Gene on 6/5/2014.
 */
public class ClientFactoryImpl implements ClientFactory {

    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);
    private static AddNoteListViewImpl addNoteListView;
    private static AddNoteViewImpl addNoteView;
    private static ManageNotesViewImpl manageNotesView;
    private static NewNoteListViewImpl newNoteListView;
    private static NavViewImpl navView;
    private static ProfileViewImpl profileView;

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public AddNoteListViewImpl getAddNoteListView() {
        if (addNoteListView == null) {
            addNoteListView = new AddNoteListViewImpl();
        }
        return addNoteListView;
    }

    @Override
    public AddNoteViewImpl getAddNoteView() {
        if (addNoteView == null) {
            addNoteView = new AddNoteViewImpl();
        }
        return addNoteView;
    }

    @Override
    public ManageNotesViewImpl getManageNotesView() {
        if (manageNotesView == null) {
            manageNotesView = new ManageNotesViewImpl();
        }
        return manageNotesView;
    }

    @Override
    public NewNoteListViewImpl getNewNoteListView() {
        if (newNoteListView == null) {
            newNoteListView = new NewNoteListViewImpl();
        }
        return newNoteListView;
    }

    @Override
    public NavViewImpl getNavView() {
        if (navView == null) {
            navView = new NavViewImpl();
        }
        return navView;
    }

    @Override
    public ProfileViewImpl getProfileView() {
        if (profileView == null) {
            profileView = new ProfileViewImpl();
        }
        return profileView;
    }
}
