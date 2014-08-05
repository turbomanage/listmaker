package com.turbomanage.gwt.client.ui.widget;

import java.util.ArrayList;

public interface HasSelectableItems<T>
{
	void populateAllItems(ArrayList<T> items);
	void addItemSelectionHandler(ItemSelectionHandler<T> handler);
	void addItemActivationHandler(ItemActivationHandler handler);
	void selectAll();
	void selectNone();	
	void filter(String string, Object object);
}
