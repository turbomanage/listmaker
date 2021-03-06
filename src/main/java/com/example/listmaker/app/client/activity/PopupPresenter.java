package com.example.listmaker.app.client.activity;

import com.example.listmaker.common.client.presenter.Presenter;
import com.example.listmaker.common.client.ui.web.View;

/**
 * Created by Gene on 6/5/2014.
 */
public class PopupPresenter<V extends View> implements Presenter<V> {

    private V view;

    @Override
    public void setView(V view) {
        this.view = view;
        view.setActivity(this);
    }

    @Override
    public V getView() {
        return view;
    }

    public void start() {
        getView().init();
    }

}