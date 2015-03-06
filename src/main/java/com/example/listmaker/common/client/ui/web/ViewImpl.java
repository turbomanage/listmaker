package com.example.listmaker.common.client.ui.web;

import com.example.listmaker.common.client.presenter.Presenter;
import com.example.listmaker.common.client.ui.widget.Heading1;
import com.google.gwt.user.client.ui.*;

public class ViewImpl<P extends Presenter> extends Composite implements View<P>
{

    private P presenter;

	@Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }

	@Override
	public void startProcessing() {

	}

	@Override
	public void stopProcessing() {

	}

}