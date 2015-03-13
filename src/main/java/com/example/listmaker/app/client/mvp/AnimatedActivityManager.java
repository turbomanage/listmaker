package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceChangeRequestEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * ActivityManager that will show an animation when transition to the next activity.
 * Uses its own AnimationPanel and delegates all calls to ActivityManager.
 *
 * Created by david on 3/10/15.
 */
public class AnimatedActivityManager implements PlaceChangeEvent.Handler, PlaceChangeRequestEvent.Handler {

    private final ActivityManager wrapped;
    private final AnimatedPanel animatedPanel = new AnimatedPanel();

    public AnimatedActivityManager(ActivityMapper mapper, EventBus eventBus) {
        wrapped = new ActivityManager(mapper, eventBus);
    }

    public void setDisplay(AcceptsOneWidget display) {
        display.setWidget(animatedPanel);
        wrapped.setDisplay(animatedPanel);
    }

    @Override
    public void onPlaceChange(PlaceChangeEvent event) {
        wrapped.onPlaceChange(event);
    }

    @Override
    public void onPlaceChangeRequest(PlaceChangeRequestEvent placeChangeRequestEvent) {
        wrapped.onPlaceChangeRequest(placeChangeRequestEvent);
    }

}
