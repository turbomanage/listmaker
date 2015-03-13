package com.example.listmaker.app.client.mvp;

import com.example.listmaker.app.client.App;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndEvent;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndHandler;

import java.util.Stack;
import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Created by david on 3/10/15.
 */
public class AnimatedPanel extends Composite implements AcceptsOneWidget, AnimationEndHandler {

    private FlowPanel flowPanel = new FlowPanel();
    private SimplePanel lastPanel = new SimplePanel();
    private SimplePanel thisPanel = new SimplePanel();
    private AnimatedActivityManager activityManager;
    private int lastHistorySize;
    private final Stack<String> history = new Stack<String>();

    public AnimatedPanel(AnimatedActivityManager animatedActivityManager) {
        this.activityManager = animatedActivityManager;
        flowPanel.setStyleName(Bundle.INSTANCE.css().display());
        lastPanel.getElement().setId("lastPanel");
        thisPanel.getElement().setId("thisPanel");
        lastPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        thisPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        flowPanel.add(lastPanel);
        flowPanel.add(thisPanel);
        initWidget(flowPanel);
        getElement().setId("animatedPanel");
        addDomHandler(this, AnimationEndEvent.getType());
    }

    protected void animate() {
        if (lastPanel.getElement().hasChildNodes()) {
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
        // save history
        int size = this.lastHistorySize = history.size();
        Logger.getLogger("AAM").info("size before = " + history.size());
        String newToken = App.historyMapper().getToken(App.placeController().getWhere());
        int priorIndex = history.search(newToken) - 1; // GWT bug?
        // if this place token has been seen before, move backwards to it
        if (priorIndex > 0) {
            for (int i=size; i>priorIndex; i--) {
                history.pop();
            }
        } else {
            history.push(newToken);
        }
        Logger.getLogger("AAM").info("size after = " + history.size());
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
