package com.example.listmaker.app.client.mvp;

import com.example.listmaker.app.client.App;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndEvent;
import com.googlecode.mgwt.ui.client.widget.animation.impl.AnimationEndHandler;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Created by david on 3/10/15.
 */
public class AnimatedPanel extends Composite implements AcceptsOneWidget, AnimationEndHandler {

    private FlowPanel flowPanel = new FlowPanel();
    private SimplePanel lastPanel = new SimplePanel();
    private SimplePanel thisPanel = new SimplePanel();

    public AnimatedPanel() {
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
            lastPanel.addStyleName(Bundle.INSTANCE.css().out());
            thisPanel.addStyleName(Bundle.INSTANCE.css().in());
        }
    }

    @Override
    public void setWidget(IsWidget newWidget) {
        // Remove old lastWidget from panel
        Widget thisWidget = thisPanel.getWidget();
        if (thisWidget != null) {
            thisWidget.removeFromParent();
            lastPanel.setWidget(thisWidget.asWidget());
        }
        thisPanel.setWidget(newWidget);
        animate();
    }

    @Override
    public void onAnimationEnd(AnimationEndEvent event) {
        lastPanel.removeStyleName(Bundle.INSTANCE.css().out());
        thisPanel.removeStyleName(Bundle.INSTANCE.css().in());
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
