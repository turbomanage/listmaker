package com.example.listmaker.app.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;
import com.example.listmaker.app.client.mvp.AppPlaceHistoryMapper;
import com.example.listmaker.app.client.service.*;
import com.example.listmaker.app.client.ui.web.images.AppImages;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.example.client.dispatcher.DispatcherFactory;

import java.util.logging.Logger;

/**
 * Use this to get images, constants, model, and messages
 * Created by Gene on 6/5/2014.
 */
public class App {

    private static AppModel appModel;
    private static AppConstants appConstants;
    private static AppMessages appMessages;
    private static AppImages appImages;

    private static ClientFactory clientFactory;
    private static DispatcherFactory dispatcherFactory = new DispatcherFactory();

    private static PlaceHistoryMapper placeHistoryMapper;

    private static final Logger rootLogger = Logger.getLogger("");


    private App() {
        // prevent instantiation
    }

    public static Logger getLogger() {
        return rootLogger;
    }

    public static PlaceHistoryMapper getPlaceHistoryMapper() {
        if (placeHistoryMapper == null) {
            placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
        }
        return placeHistoryMapper;
    }

    public static EventBus getEventBus() {
        return getClientFactory().getEventBus();
    }

    public static ClientFactory getClientFactory() {
        if (clientFactory == null) {
            clientFactory = new ClientFactoryImpl();
        }
        return clientFactory;
    }

    public static AppModel getAppModel() {
        if (appModel == null) {
            appModel = new AppModel();
        }
        return appModel;
    }

    public static AppConstants getAppConstants() {
        if (appConstants == null) {
            appConstants = GWT.create(AppConstants.class);
        }
        return appConstants;
    }

    public static AppMessages getAppMessages() {
        if (appMessages == null) {
            appMessages = GWT.create(AppMessages.class);
        }
        return appMessages;
    }

    public static AppImages getAppImages() {
        if (appImages == null) {
            appImages = GWT.create(AppImages.class);
        }
        return appImages;
    }

    public static Dispatcher getDispatcher() {
        return dispatcherFactory.cachingDispatcher();
    }

}