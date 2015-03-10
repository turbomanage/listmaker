package com.example.listmaker.app.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
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
 */
public class App {

    private static final AppConstants appConstants = GWT.create(AppConstants.class);
    private static final AppMessages appMessages = GWT.create(AppMessages.class);
    private static final AppImages appImages = GWT.create(AppImages.class);
    private static final AppModel appModel = new AppModel();

    private static final ClientFactory clientFactory = new ClientFactoryImpl();
    private static final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private static final DispatcherFactory dispatcherFactory = new DispatcherFactory();

    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);
    private static final PlaceHistoryMapper placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);

    private static final Logger rootLogger = Logger.getLogger("");

    private App() {
        // prevent instantiation
    }

    public static Logger getLogger() {
        return rootLogger;
    }

    public static PlaceHistoryMapper historyMapper() {
        return placeHistoryMapper;
    }

    public static EventBus eventBus() {
        return eventBus;
    }

    public static PlaceController placeController() {
        return placeController;
    }

    public static ClientFactory clientFactory() {
        return clientFactory;
    }

    public static ServiceFactory serviceFactory() {
        return serviceFactory;
    }

    public static AppModel model() {
        return appModel;
    }

    public static AppConstants constants() {
        return appConstants;
    }

    public static AppMessages messages() {
        return appMessages;
    }

    public static AppImages images() {
        return appImages;
    }

    public static Dispatcher getDispatcher() {
        return dispatcherFactory.cachingDispatcher();
    }

}