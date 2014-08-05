package com.example.listmaker.app.client.activity;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.app.client.place.ProfilePlace;
import com.example.listmaker.app.client.ui.web.content.ProfileViewImpl;
import com.example.listmaker.common.client.presenter.ActivityPresenter;
import com.example.listmaker.common.client.ui.web.View;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Created by Gene on 6/17/2014.
 */
public class ProfileActivity extends ActivityPresenter<ProfileViewImpl> {

    private User user;

    public interface ProfileView extends View<ProfileActivity> {
        void setInfo(User user);
        void setEditInfo(User user);
    }

    public ProfileActivity(ProfilePlace place) {
        setView(App.getClientFactory().getProfileView());
    }


    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        setView(getView());
        super.start(acceptsOneWidget, eventBus);

        user = App.getAppModel().getMe();
        viewProfile();
      }

    public void editProfile() {
        getView().setEditInfo(user);
    }

    public void viewProfile() {
        getView().setInfo(user);
    }

}
