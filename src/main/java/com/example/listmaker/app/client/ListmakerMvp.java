/*
 * Copyright 2009 David M Chandler All Rights Reserved.
 */

package com.example.listmaker.app.client;

import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.app.client.mvp.AppActivityMapper;
import com.example.listmaker.app.client.place.ContactsPlace;
import com.example.listmaker.app.client.service.AppCallback;
import com.example.listmaker.app.client.service.LoginInfoService;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.debug.client.DebugInfo;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.fusesource.restygwt.client.Defaults;

/**
 * Listmaker entry point.
 */
public class ListmakerMvp implements EntryPoint {

    private static final String LOGOUT_URL = "/listmaker/logout";
//    private ContentPanel mainDisplay = new ContentPanel();
    private SimplePanel mainDisplay = new SimplePanel();
    private Place defaultPlace = new ContactsPlace(null);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setDateFormat(null);
        Defaults.setDispatcher(App.getDispatcher());
        DebugInfo.setDebugIdPrefix("");

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
        //gwt-activities-and-places
        ActivityMapper mainActivityMapper = new AppActivityMapper();
        ActivityManager mainActivityManager = new ActivityManager(mainActivityMapper, App.getEventBus());
        mainActivityManager.setDisplay(mainDisplay);

        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(App.getPlaceHistoryMapper());
        historyHandler.register(App.clientFactory().getPlaceController(), App.getEventBus(), defaultPlace);
        DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById(AppStyles.ID_SPLASH));

        RootPanel.get(AppStyles.BODY_PANEL_CONTENT_ID).add(mainDisplay);

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
