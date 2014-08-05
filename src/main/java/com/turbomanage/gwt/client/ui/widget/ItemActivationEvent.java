package com.turbomanage.gwt.client.ui.widget;

import com.google.gwt.event.shared.GwtEvent;

public class ItemActivationEvent extends GwtEvent<ItemActivationHandler>
{

	public static final GwtEvent.Type<ItemActivationHandler> TYPE = new GwtEvent.Type<ItemActivationHandler>();
	private int index;
	
	public ItemActivationEvent(int index)
	{
		this.index = index;
	}
	
	@Override
	protected void dispatch(ItemActivationHandler handler)
	{
		handler.onItemActivated(this.index);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ItemActivationHandler> getAssociatedType()
	{
		return TYPE;
	}

	public int getActivatedItem()
	{
		return index;
	}

}
