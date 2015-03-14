package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * ActivityManager that will show an animation when transitioning to the next activity.
 * Uses its own AnimatedActivityPanel and delegates all calls to ActivityManager.
 * <p/>
 * Created by david on 3/10/15.
 */
public class AnimatedActivityManager {

    // The real GWT ActivityManager
    private final ActivityManager wrappedActivityMgr;
    private final AnimatedActivityPanel animatedPanel = new AnimatedActivityPanel(this);
    private final PlaceHistoryMapper historyMapper;
    private final PlaceController placeController;

    public AnimatedActivityManager(ActivityMapper mapper, EventBus eventBus, PlaceHistoryMapper historyMapper, PlaceController placeController) {
        this.wrappedActivityMgr = new ActivityManager(mapper, eventBus);
        this.historyMapper = historyMapper;
        this.placeController = placeController;
    }

    /**
     * Sets the widget in the real display to be the AnimatedActivityPanel.
     * All PlaceChangeEvents are handled by the wrapped ActivityManager because
     * that wiring is done in ActivityManager#setDisplay(), which gets called here.
     *
     * @param display The real display
     */
    public void setDisplay(AcceptsOneWidget display) {
        display.setWidget(animatedPanel);
        wrappedActivityMgr.setDisplay(animatedPanel);
    }

    public PlaceHistoryMapper getHistoryMapper() {
        return historyMapper;
    }

    public PlaceController getPlaceController() {
        return placeController;
    }
}
