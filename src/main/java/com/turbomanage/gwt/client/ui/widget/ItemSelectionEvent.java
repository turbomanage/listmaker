package com.turbomanage.gwt.client.ui.widget;

import java.util.HashSet;

import com.google.gwt.event.shared.GwtEvent;

public class ItemSelectionEvent<T> extends GwtEvent<ItemSelectionHandler>
{

	public static final GwtEvent.Type<ItemSelectionHandler> TYPE = new GwtEvent.Type<ItemSelectionHandler>();
	private HashSet<T> selectedItems;
	
	public ItemSelectionEvent(HashSet<T> items)
	{
		this.selectedItems = items;
	}
	
	@Override
	protected void dispatch(ItemSelectionHandler handler)
	{
		handler.onItemsSelected(selectedItems);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ItemSelectionHandler> getAssociatedType()
	{
		return TYPE;
	}

	public HashSet<T> getSelectedItems()
	{
		return selectedItems;
	}

}
