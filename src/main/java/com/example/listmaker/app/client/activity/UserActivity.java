package com.example.listmaker.app.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.ui.web.content.UserViewImpl;
import com.example.listmaker.common.client.ui.web.View;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.domain.MenuItem;

import java.util.List;

/**
 * Created by Gene on 6/5/2014.
 */
public class UserActivity extends ActivityPresenter<UserViewImpl> {

    public User showingUser;

    public UserActivity(HomePlace place) {
    }

    public interface UserView extends View<UserActivity>
    {
        void showUser(User u);

        void setMenu(List<MenuItem> items);
    }

    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(App.getClientFactory().getUserView());
        super.start(acceptsOneWidget, eventBus);
        this.showingUser = App.getAppModel().getMe();
        getView().showUser(showingUser);
    }
}
