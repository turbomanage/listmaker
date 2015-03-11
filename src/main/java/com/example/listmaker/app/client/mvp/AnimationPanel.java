package com.example.listmaker.app.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Effects;

/**
 * Created by david on 3/10/15.
 */
public class AnimationPanel extends Composite implements AnimatedDisplay {

    private FlowPanel flowPanel = new FlowPanel();
    private SimplePanel lastPanel = new SimplePanel();
    private SimplePanel thisPanel = new SimplePanel();

    public AnimationPanel() {
        flowPanel.setStyleName(Bundle.INSTANCE.css().display());
        lastPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        thisPanel.addStyleName(Bundle.INSTANCE.css().displayContainer());
        flowPanel.add(lastPanel);
        flowPanel.add(thisPanel);
        initWidget(flowPanel);
    }

    @Override
    public void animate() {
        //TODO why not lastPanel?
        if (thisPanel.getWidget() != null) {
            lastPanel.addStyleName(Bundle.INSTANCE.css().out());
            thisPanel.addStyleName(Bundle.INSTANCE.css().in());
            //TODO use onAnimationEnd event instead
            Timer timer = new Timer() {
                @Override
                public void run() {
                    lastPanel.removeStyleName(Bundle.INSTANCE.css().out());
                    thisPanel.removeStyleName(Bundle.INSTANCE.css().in());
                }
            };
            timer.schedule(300);
        }
    }

    @Override
    public void setWidget(IsWidget newWidget) {
        // Remove old lastWidget from panel
        lastPanel.setWidget(thisPanel.getWidget());
        thisPanel.setWidget(newWidget);
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
