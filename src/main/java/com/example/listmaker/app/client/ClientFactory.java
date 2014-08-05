package com.example.listmaker.app.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.example.listmaker.app.client.ui.web.content.*;
import com.example.listmaker.app.client.ui.web.nav.NavViewImpl;
import com.example.listmaker.common.client.ui.web.ViewImpl;

/**
 * Created by Gene on 6/5/2014.
 */
public interface ClientFactory {

    EventBus getEventBus();
    PlaceController getPlaceController();

    AddNoteListViewImpl getAddNoteListView();
    AddNoteViewImpl getAddNoteView();
    ManageNotesViewImpl getManageNotesView();
    NewNoteListViewImpl getNewNoteListView();
    UserViewImpl getUserView();
    NavViewImpl getNavView();
    ProfileViewImpl getProfileView();

}
