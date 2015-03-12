package com.example.listmaker.app.client.mvp;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * ActivityManager that will show an animation when transition to the next activity.
 * Uses its own AnimatableDisplay and won't work without it so there is no need to call
 * setDisplay().
 *
 * Created by david on 3/10/15.
 */
public class AnimatedActivityManager extends ActivityManager {

    private AnimatedPanel animatedPanel;

    public AnimatedActivityManager(ActivityMapper mapper, EventBus eventBus) {
        super(mapper, eventBus);
    }

    @Override
    public void onPlaceChange(PlaceChangeEvent event) {
        super.onPlaceChange(event);
        if (animatedPanel != null) {
            animatedPanel.animate();
        }
    }

    public void setAnimatedPanel(AnimatedPanel animatedDisplay) {
        setDisplay(animatedDisplay);
        this.animatedPanel = animatedDisplay;
    }

    @Override
    public void setDisplay(AcceptsOneWidget display) {
        if (animatedPanel == null) {
            super.setDisplay(display);
        } else {
            Window.alert("Don't call setDisplay() on AnimatedActivityManager");
        }
    }
}
