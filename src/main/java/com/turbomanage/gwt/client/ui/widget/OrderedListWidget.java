package com.turbomanage.gwt.client.ui.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrderedListWidget extends ComplexPanel
{
	public OrderedListWidget()
	{
		setElement(Document.get().createOLElement());
	}

	public void setId(String id)
	{
		((OListElement) getElement().cast()).setId(id);
	}
	
	public void add(Widget w)
	{
		super.add(w, getElement());
	}
}
