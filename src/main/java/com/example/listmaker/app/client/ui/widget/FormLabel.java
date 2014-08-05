package com.example.listmaker.app.client.ui.widget;

import com.google.gwt.user.client.ui.Label;
import com.example.listmaker.common.client.ui.web.AppStyles;

public class FormLabel extends Label
{
	public FormLabel(String label)
	{
		this.setText(label);
		this.addStyleName(AppStyles.FORM_LABEL);
	}
}
