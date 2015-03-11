package com.example.listmaker.common.client.presenter;

import com.example.listmaker.common.client.ui.web.View;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import java.util.logging.Logger;

/**
 * Base class for Activity acting as a presenter
 */
public class ActivityPresenter<V extends View> extends AbstractActivity implements Presenter<V> {

    private V view;

    @Override
    public void bind(V view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        Logger.getLogger("activitypresetner").info("ugh");
        acceptsOneWidget.setWidget(getView().asWidget());
//            Widget widget = getView().asWidget();
//            widget.setVisible(false);
//        $("#presenting").as(Effects).slideLeft(2000);
//        acceptsOneWidget.setWidget(widget);

//            widget.getElement().<FxElement> cast().slideIn(Style.Direction.LEFT);
    }

}