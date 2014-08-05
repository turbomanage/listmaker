/*
 * Copyright 2009 David M Chandler All Rights Reserved.
 */

package com.example.listmaker.app.client;

import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.app.client.mvp.AddNoteActivityMapper;
import com.example.listmaker.app.client.mvp.AppActivityMapper;
import com.example.listmaker.app.client.mvp.NavActivityMapper;
import com.example.listmaker.app.client.mvp.UserActivityMapper;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.service.LoginInfoService;
import org.fusesource.restygwt.client.Defaults;

/**
 * Listmaker entry point.
 */
public class ListmakerMvp implements EntryPoint {

    private static final String LOGOUT_URL = "/listmaker/logout";
    private SimplePanel addNote = new SimplePanel();
    private SimplePanel mainDisplay = new SimplePanel();
    private SimplePanel userDisplay = new SimplePanel();
    private SimplePanel nav = new SimplePanel();
    private Place defaultPlace = new HomePlace(null);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setDateFormat(null);
        Defaults.setDispatcher(App.getDispatcher());

        addLoggers();
        LoginInfoService loginInfoService = GWT.create(LoginInfoService.class);
        loginInfoService.login(GWT.getHostPageBaseURL(), new AppCallback<User>() {
            @Override
            public void handleSuccess(User me) {
                App.getAppModel().setMe(me);
                loadApp();
            }
        });
    }

    private void loadApp() {
        // Show login links
        Element userLinks = Document.get().getElementById(AppStyles.ID_USER_LINKS);
        UListElement ul = Document.get().createULElement();
        LIElement liSignedIn = Document.get().createLIElement();
        LIElement liSignOut = Document.get().createLIElement();
        User me = App.getAppModel().getMe();
        String firstName = me.firstName;
        String lastName = me.lastName;
        liSignedIn.setInnerHTML("Signed in as <span class=\"nameText\">" + firstName + " " + lastName + "</span>");
        liSignOut.setInnerHTML("<span class=\"listmaker-userEmail\">" + me.emailAddress + "</span>");
        liSignOut.setInnerHTML("<a href=\"" + LOGOUT_URL + "\">Sign out</a>");
        ul.appendChild(liSignedIn);
        ul.appendChild(liSignOut);
        userLinks.appendChild(ul);

        //gwt-activities-and-places
        ActivityMapper userActivityMapper = new UserActivityMapper();
        ActivityManager userActivityManager = new ActivityManager(userActivityMapper, App.getEventBus());
        userActivityManager.setDisplay(userDisplay);

        ActivityMapper addNoteActivityMapper = new AddNoteActivityMapper();
        ActivityManager addNoteActivityManager = new ActivityManager(addNoteActivityMapper, App.getEventBus());
        addNoteActivityManager.setDisplay(addNote);

        ActivityMapper navActivityMapper = new NavActivityMapper();
        ActivityManager navActivityManager = new ActivityManager(navActivityMapper, App.getEventBus());
        navActivityManager.setDisplay(nav);

        ActivityMapper mainActivityMapper = new AppActivityMapper();
        ActivityManager noteDisplayActivityManager = new ActivityManager(mainActivityMapper, App.getEventBus());
        noteDisplayActivityManager.setDisplay(mainDisplay);

        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(App.getPlaceHistoryMapper());
        historyHandler.register(App.getClientFactory().getPlaceController(), App.getEventBus(), defaultPlace);
        DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById(AppStyles.ID_SPLASH));

        RootPanel.get(AppStyles.BODY_PANEL_USER_ID).add(userDisplay);
        RootPanel.get(AppStyles.BODY_PANEL_TOP_ID).add(addNote);
        RootPanel.get(AppStyles.BODY_PANEL_CONTENT_ID).add(mainDisplay);
        RootPanel.get(AppStyles.BODY_PANEL_NAV_ID).add(nav);

        historyHandler.handleCurrentHistory();

    }

    private void addLoggers() {
        App.getEventBus().addHandler(ValueChangeEvent.getType(), new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                App.getLogger().finest("VCE " + event.getValue());
            }
        });
    }

}
