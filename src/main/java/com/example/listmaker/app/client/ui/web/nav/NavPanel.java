/*
* Copyright 2009 David M Chandler All Rights Reserved.
*/

package com.example.listmaker.app.client.ui.web.nav;

import com.google.gwt.user.client.ui.*;
import com.example.listmaker.app.client.ui.web.images.AppImages;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.domain.NoteList;

import java.util.List;

/**
* App main navigation bar.
*
*/
public class NavPanel extends Tree
{

	private static final String CSS_NAV_ITEM = "listmaker-navItem";
	private static final String CSS_NAV_HEADER = "listmaker-navHeader";

	private TreeItem listsItem = new TreeItem(createListsHeader());
	private AppImages images;

	public NavPanel()
	{
		this.getElement().setId(AppStyles.BODY_PANEL_NAV_ID);
		this.setAnimationEnabled(true);
		addItem(listsItem);
	}

	private HorizontalPanel createListsHeader()
	{
		HorizontalPanel h = new HorizontalPanel();
		h.addStyleName(CSS_NAV_HEADER);
		h.setWidth("100%");
		Image n = images.notepad_32().createImage();
		h.add(n);
		h.setCellWidth(n, "32px");
		Hyperlink my_lists = new Hyperlink("My Lists", "");
		h.add(my_lists);
		return h;
	}

}
