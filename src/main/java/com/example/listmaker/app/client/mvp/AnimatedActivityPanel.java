package com.example.listmaker.app.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.*;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndEvent;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndHandler;

import java.util.Stack;

/**
 * A special container which AcceptsOneWidget so it can work with GWT Activities
 * but uses two SimplePanels under the covers in order to show an animation from
 * one activity to the next. Inspired by MGWT's AnimationActivityManager. This
 * one automatically keeps track of place history and reverses the animation
 * whenever going backward in the stack.
 *
 * Created by david on 3/10/15.
 */
public class AnimatedActivityPanel extends Composite implements AcceptsOneWidget, AnimationEndHandler {

    private final AnimatedActivityManager activityMgr;
    private final FlowPanel flowPanel = new FlowPanel();
    private final SimplePanel lastPanel = new SimplePanel();
    private final SimplePanel thisPanel = new SimplePanel();
    private final Stack<String> history = new Stack<String>();
    private int lastHistorySize;

    public AnimatedActivityPanel(AnimatedActivityManager animatedActivityManager) {
        this.activityMgr = animatedActivityManager;
        flowPanel.setStyleName(Bundle.INSTANCE.css().display());
        lastPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        thisPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        flowPanel.add(lastPanel);
        flowPanel.add(thisPanel);
        initWidget(flowPanel);
        addDomHandler(this, AnimationEndEvent.getType());
    }

    protected void animate() {
        // if there is a previous place
        if (lastPanel.getElement().hasChildNodes()) {
            // if going backwards, reverse the animation
            if (history.size() < this.lastHistorySize) {
                lastPanel.addStyleName(Bundle.INSTANCE.css().reverse());
                thisPanel.addStyleName(Bundle.INSTANCE.css().reverse());
            }
            lastPanel.addStyleName(Bundle.INSTANCE.css().out());
            thisPanel.addStyleName(Bundle.INSTANCE.css().in());
        }
    }

    @Override
    public void setWidget(IsWidget newWidget) {
        // gets called 2x per place change, once null then new widget
        if (newWidget == null)
            return;
        // Remove old lastWidget from panel
        Widget thisWidget = thisPanel.getWidget();
        if (thisWidget != null) {
            thisWidget.removeFromParent();
            lastPanel.setWidget(thisWidget.asWidget());
        }
        thisPanel.setWidget(newWidget);
        // Update history stack. This method (setWidget) and PlaceController.goTo() are the only
        // methods that get called on back button (PlaceChangeEvent does not get fired) therefore
        // we cannot use an event listener to detect the back button.
        updateHistory();
        // Unfortunately has to go here or ActivityManager clobbers the old widget
        animate();
    }

    private void updateHistory() {
        // save place history so we can reverse the transition when going backwards
        int size = this.lastHistorySize = history.size();
        PlaceHistoryMapper historyMapper = activityMgr.getHistoryMapper();
        PlaceController placeController = activityMgr.getPlaceController();
        Place currentPlace = placeController.getWhere();
        String newToken = historyMapper.getToken(currentPlace);
        int priorIndex = history.search(newToken) - 1; // GWT bug in Stack.search?
        // if this place token has been seen before, pop the stack back to it
        if (priorIndex > 0) {
            for (int i=size; i>priorIndex; i--) {
                history.pop();
            }
        } else {
            history.push(newToken);
        }
    }

    @Override
    public void onAnimationEnd(AnimationEndEvent event) {
        lastPanel.removeStyleName(Bundle.INSTANCE.css().in());
        lastPanel.removeStyleName(Bundle.INSTANCE.css().out());
        lastPanel.removeStyleName(Bundle.INSTANCE.css().reverse());
        thisPanel.removeStyleName(Bundle.INSTANCE.css().in());
        thisPanel.removeStyleName(Bundle.INSTANCE.css().out());
        thisPanel.removeStyleName(Bundle.INSTANCE.css().reverse());
        lastPanel.clear();
    }

    interface Bundle extends ClientBundle {
        Bundle INSTANCE = GWT.create(Bundle.class);
        @Source({"slide.css"})
        CSS css();
    }

    static {
        Bundle.INSTANCE.css().ensureInjected();
    }

    public interface CSS extends CssResource {
        String in();
        String out();
        String reverse();
        String displayContainer();
        String display();
    }

}