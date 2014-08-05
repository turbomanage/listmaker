package com.example.listmaker.app.client.ui.web.content;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.ProfileActivity;
import com.example.listmaker.app.client.domain.User;
import com.example.listmaker.common.client.ui.web.ViewImpl;

/**
 * Created by Gene on 6/17/2014.
 */
public class ProfileViewImpl extends ViewImpl<ProfileActivity> implements ProfileActivity.ProfileView {

    private HeadingElement heading;
    private Image profilePic;
    private Label firstNameLabel;
    private TextBox firstName;
    private Label lastNameLabel;
    private TextBox lastName;
    private Label emailText;
    private Label emailLabel;
    private TextBox email;
    private Label zipcodeText;
    private Label zipcodeLabel;
    private TextBox zipcode;
    private Label userSinceText;
    private Label userSince;
    private Label googleAccountText;
    private Label googleAccount;
    private Button save;
    private Button cancel;
    private Button editProfile;
    private Panel profilePanel, picNamePanel, namePanel;

    public ProfileViewImpl() {
        init();
    }

    @Override
    public void init() {
        super.init();
        profilePanel = new FlowPanel();
        picNamePanel = new HorizontalPanel();
        namePanel = new VerticalPanel();
        heading = Document.get().createHElement(1);
        heading.setInnerText("User Profile");
        profilePic = new Image();
        firstNameLabel = new Label();
        firstName = new TextBox();
        lastNameLabel = new Label();
        lastName = new TextBox();
        emailText = new Label("Email Address:");
        emailLabel = new Label();
        email = new TextBox();
        zipcodeText = new Label("ZipCode:");
        zipcodeLabel = new Label();
        zipcode = new TextBox();
        userSinceText = new Label("App User Since:");
        userSince = new Label();
        googleAccountText = new Label("Connected Google Account?");
        googleAccount = new Label();
        save = new Button("Save Changes");
//        save.addClickHandler(saveClickHandler);
        cancel = new Button("Cancel Changes");
        cancel.addClickHandler(cancelClickHandler);
        editProfile = new Button("Edit Profile");
        editProfile.addClickHandler(editClickHandler);

    }

    public void setInfo(User user) {
        profilePic = App.getAppImages().user_48().createImage();
        firstNameLabel.setText(user.firstName);
        lastNameLabel.setText(user.lastName);
        emailLabel.setText(user.emailAddress);
        zipcodeLabel.setText(user.zipCode);

        profilePanel.clear();
        picNamePanel.clear();
        namePanel.clear();
        profilePanel.getElement().appendChild(heading);
        picNamePanel.add(profilePic);
        namePanel.add(firstNameLabel);
        namePanel.add(lastNameLabel);
        picNamePanel.add(namePanel);
        profilePanel.add(picNamePanel);
        profilePanel.add(emailText);
        profilePanel.add(emailLabel);
        profilePanel.add(zipcodeText);
        profilePanel.add(zipcodeLabel);
        addNonEditInfo(user);
        profilePanel.add(editProfile);

    }

    @Override
    public void setEditInfo(User user) {
        profilePic = App.getAppImages().user_48().createImage();
        firstName.setText(user.firstName);
        lastName.setText(user.lastName);
        email.setText(user.emailAddress);
        zipcode.setText(user.zipCode);

        profilePanel.clear();
        picNamePanel.clear();
        namePanel.clear();
        profilePanel.getElement().appendChild(heading);
        picNamePanel.add(profilePic);
        namePanel.add(firstName);
        namePanel.add(lastName);
        picNamePanel.add(namePanel);
        profilePanel.add(picNamePanel);
        profilePanel.add(emailText);
        profilePanel.add(email);
        profilePanel.add(zipcodeText);
        profilePanel.add(zipcode);
        addNonEditInfo(user);
        profilePanel.add(save);
        profilePanel.add(cancel);

    }

    public void addNonEditInfo(User user) {
        userSince.setText(user.dateCreated.toString());
        if (user.googleId != null) {
            googleAccount.setText("Yup!");
        } else {
            googleAccount.setText("No :-(");
        }
        profilePanel.add(userSinceText);
        profilePanel.add(userSince);
        profilePanel.add(googleAccountText);
        profilePanel.add(googleAccount);
        viewPanel.add(profilePanel);
    }

    @Override
    public Widget asWidget() {
        return viewPanel;
    }

    /**
     * Handlers
     */

    private ClickHandler editClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().editProfile();
        }
    };

    private ClickHandler cancelClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().viewProfile();
        }
    };
}
