package com.turbomanage.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.turbomanage.gwt.client.handler.ShowMessageHandler;
import com.turbomanage.gwt.client.ui.widget.MessageWidget.MessageType;

public class ShowMessageEvent extends GwtEvent<ShowMessageHandler>
{
	public static final GwtEvent.Type<ShowMessageHandler> TYPE = new GwtEvent.Type<ShowMessageHandler>();
	private final MessageType msgType;
	private final String msg;
	
	public ShowMessageEvent(String msg)
	{
		this(msg, MessageType.INFO);
	}
	
	public ShowMessageEvent(String msg, MessageType msgType)
	{
		this.msg = msg;
		this.msgType = msgType;
	}
	
	@Override
	protected void dispatch(ShowMessageHandler handler)
	{
		handler.showMessage(msg, msgType);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowMessageHandler> getAssociatedType()
	{
		return TYPE;
	}

	public String getMsg()
	{
		return msg;
	}

}
