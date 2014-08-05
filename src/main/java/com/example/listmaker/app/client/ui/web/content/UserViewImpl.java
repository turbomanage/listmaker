package com.example.listmaker.app.client.ui.web.content;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.UserActivity;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.app.client.place.ProfilePlace;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.common.domain.MenuItem;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.turbomanage.gwt.client.ui.widget.ListItemWidget;
import com.turbomanage.gwt.client.ui.widget.UnorderedListWidget;

import java.util.ArrayList;
import java.util.List;

public class UserViewImpl extends ViewImpl<UserActivity> implements UserActivity.UserView
{
	// So can set min-height on whole panel
	private UnorderedListWidget userMenu;
	private Element userName;
    private static boolean isInitialized;
    private Image profile = App.getAppImages().user_48().createImage();

	public UserViewImpl()
	{
		// So can set min-height on whole panel
		userMenu = new UnorderedListWidget();
		userName = Document.get().createHElement(1);
        init();
	}

	@Override
	public void showUser(User u)
	{
		this.userName.setInnerText(u.firstName + " " + u.lastName);
        if (u.imgUrl != null) {
            profile.setUrl(u.imgUrl);
        }
	}

	@Override
	public void setMenu(List<MenuItem> items)
	{
		userMenu.clear();
		for (int i = 0; i < items.size(); i++)
		{
			MenuItem item = items.get(i);
			if (i > 0)
			{
				userMenu.add(new ListItemWidget("|"));
			}
            if(item.getLabel().equals("Connect")) {
                Anchor y = new Anchor(item.getLabel(), item.getUrl());
                userMenu.add(new ListItemWidget(y));
            } else {
                Hyperlink y = new Hyperlink(item.getLabel(), item.getUrl());
                userMenu.add(new ListItemWidget(y));
            }
		}
	}

	@Override
	public void init()
	{
		super.init();
		this.viewPanel.getElement().setId(AppStyles.ID_USER_VIEW);
		this.viewPanel.add(profile);
		this.viewPanel.getElement().appendChild(userName);
		userMenu.getElement().setId(AppStyles.ID_DETAIL_MENU);
		userMenu.addStyleName(AppStyles.HORIZONTAL_MENU);

        // Build menu
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

        String notesLink = App.getPlaceHistoryMapper().getToken(new ProfilePlace());
        MenuItem notes = new MenuItem("Profile", "");
        notes.setUrl(notesLink);
        menuItems.add(notes);

        setMenu(menuItems);
	}

    @Override
    public Widget asWidget() {
        return viewPanel;
    }
    @Override
    public void hide() {
        this.viewPanel.setVisible(false);
    }

    @Override
    public void show() {
        this.viewPanel.setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return this.viewPanel.isVisible();
    }

    @Override
    public void startProcessing() {
    }

    @Override
    public void stopProcessing() {
    }
}
