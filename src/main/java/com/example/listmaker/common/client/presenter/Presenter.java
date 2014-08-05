package com.example.listmaker.common.client.presenter;

import com.example.listmaker.common.client.ui.web.View;

/**
 * Created by Gene on 6/11/2014.
 */
public interface Presenter<V extends View> {


    public void setView(V view);

    public V getView();
}
