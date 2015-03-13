package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * ActivityManager that will show an animation when transition to the next activity.
 * Uses its own AnimationPanel and delegates all calls to ActivityManager.
 *
 * Created by david on 3/10/15.
 */
public class AnimatedActivityManager {

    private final ActivityManager wrapped;
    private final AnimatedPanel animatedPanel = new AnimatedPanel(this);

    public AnimatedActivityManager(ActivityMapper mapper, EventBus eventBus, PlaceHistoryMapper historyMapper) {
        wrapped = new ActivityManager(mapper, eventBus);
//        this.historyMapper = historyMapper;
    }

    public void setDisplay(AcceptsOneWidget display) {
        display.setWidget(animatedPanel);
        wrapped.setDisplay(animatedPanel);
    }

}
