package com.example.listmaker.common.client.ui.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.ui.SimplePanel;

public class LabelWidget extends SimplePanel
{
	public LabelWidget()
	{
		super((Element) Document.get().createLabelElement().cast());
	}

	public void setHtmlFor(String htmlFor)
	{
		((LabelElement) getElement().cast()).setHtmlFor(htmlFor);
	}
}
