package com.turbomanage.gwt.client.ui.widget;

import java.util.HashSet;

import com.google.gwt.event.shared.EventHandler;

public interface ItemSelectionHandler<T> extends EventHandler
{
	void onItemsSelected(HashSet<T> selectedItems);
}
