package com.example.listmaker.app.client.ui.web.nav;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.NavActivity;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;

public class NavViewImpl extends ViewImpl<NavActivity> implements NavActivity.NavView
{
	private static final String CSS_NAV_ITEM = "listmaker-navItem";
	private static final String CSS_NAV_HEADER = "listmaker-navHeader";

	private FlowPanel listsPanel = new FlowPanel();
	private final VerticalPanel navTree = new VerticalPanel();

	public NavViewImpl()
	{
        navTree.add(createHomeLink());
		navTree.add(createListsMenu());
	}

	@Override
	public void init()
	{
		super.init();
		this.viewPanel.getElement().setId(AppStyles.BODY_PANEL_NAV_ID);
	}

	@Override
	public Widget asWidget()
	{
		return navTree;
	}

    @Override
    public void hide() {
        navTree.setVisible(false);
    }

    @Override
    public void show() {
        navTree.setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return navTree.isVisible();
    }

    @Override
	public void startProcessing()
	{
	}

	@Override
	public void stopProcessing()
	{
	}

    @Override
    public void populateLists(Collection<NoteList> noteLists) {
        listsPanel.clear();
        for (NoteList noteList : noteLists)
        {
            String link = App.getPlaceHistoryMapper().getToken(new HomePlace(String.valueOf(noteList.id)));
            Hyperlink y = new Hyperlink(noteList.name, link);
            y.addStyleName(CSS_NAV_ITEM);
            listsPanel.add(y);
        }
    }


	private Widget createListsMenu()
	{
		FlowPanel h = new FlowPanel();
		h.addStyleName(CSS_NAV_HEADER);
		Image n = App.getAppImages().notepad_32().createImage();
		h.add(n);
		h.add(new Label("My Lists"));
		// Create temp panel to hold lists--not a tree
		DisclosurePanel d = new DisclosurePanel();
		d.setHeader(h);
		d.setContent(listsPanel);
		d.setAnimationEnabled(true);
		return d;
	}

	private Widget createHomeLink()
	{
		FlowPanel f = new FlowPanel();
		f.addStyleName(CSS_NAV_HEADER);
		f.add(App.getAppImages().home_32().createImage());
        String link = App.getPlaceHistoryMapper().getToken(new HomePlace("-1"));
		f.add(new Hyperlink("Home", link));
		return f;
	}

}
