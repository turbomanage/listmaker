package com.turbomanage.gwt.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.turbomanage.gwt.client.ui.widget.MessageWidget.MessageType;

public interface ShowMessageHandler extends EventHandler
{
	void showMessage(String msg, MessageType type);
}
