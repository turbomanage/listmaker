package com.example.listmaker.common.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.example.listmaker.common.client.handler.AppLoadingEventHandler;

public class AppLoadingEvent extends GwtEvent<AppLoadingEventHandler>
{

	public static final GwtEvent.Type<AppLoadingEventHandler> TYPE = new GwtEvent.Type<AppLoadingEventHandler>();
	
	// Indicates whether we're starting or stopping loading
	private boolean isComplete; 

	public AppLoadingEvent(boolean isComplete)
	{
		this.isComplete = isComplete;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppLoadingEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(AppLoadingEventHandler handler)
	{
		handler.onAppLoadingEvent(isComplete);
	}

}
