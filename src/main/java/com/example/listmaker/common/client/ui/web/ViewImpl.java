package com.example.listmaker.common.client.ui.web;

import com.example.listmaker.common.client.presenter.Presenter;
import com.example.listmaker.common.client.ui.widget.Heading1;
import com.google.gwt.user.client.ui.*;

public class ViewImpl<P extends Presenter> extends Composite implements View<P>
{

    // outer panel that holds loading panel and view panel
	// Must create here so userPresenter.asWidget is non-null in AppPresenter
	private Panel flowPanel = new FlowPanel();
    protected Panel viewPanel;
	protected Heading1 viewHeading;

    private P presenter;

    @Override
    public void setActivity(P presenter) {
        this.presenter = presenter;
    }

    public P getActivity() {
        return presenter;
    }

	public ViewImpl()
	{
	}

	@Override
	public void init()
	{
		flowPanel.addStyleName(AppStyles.LISTMAKER_VIEW);
		viewPanel = new FlowPanel(); 
		viewHeading = new Heading1();

		viewPanel.addStyleName(AppStyles.VIEW_PANEL);
		// Init wait image

		// Add view icon if available
		Image viewIcon = this.getViewIcon();
		if (viewIcon != null)
		{
			viewIcon.addStyleName(AppStyles.VIEW_ICON);
			this.viewPanel.add(viewIcon);
		}
		this.viewHeading.addStyleName(AppStyles.HEADING_1);
		viewPanel.add(viewHeading);
		flowPanel.add(viewPanel);
	}

	@Override
	public Widget asWidget()
	{
		return flowPanel;
	}

    @Override
    public void hide() {
        flowPanel.setVisible(false);
    }

    @Override
    public void show() {
        flowPanel.setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return flowPanel.isVisible();
    }

      @Override
	public void startProcessing()
	{
		this.viewPanel.setVisible(false);
	}

	@Override
	public void stopProcessing()
	{
		this.viewPanel.setVisible(true);
	}

	@Override
	public HasText getViewHeading()
	{
		return viewHeading;
	}

	@Override
	public Image getViewIcon()
	{
		return null;
	}

    public void reset() {

    }

}