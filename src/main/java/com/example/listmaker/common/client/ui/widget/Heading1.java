package com.example.listmaker.common.client.ui.widget;

import com.google.gwt.user.client.ui.Label;
import com.example.listmaker.common.client.ui.web.AppStyles;

public class Heading1 extends Label
{

	public Heading1(String text)
	{
		super(text);
		this.addStyleName(AppStyles.HEADING_1);
	}

	public Heading1()
	{
		super();
		this.addStyleName(AppStyles.HEADING_1);
	}

}
