package com.example.listmaker.app.client.ui.web.content;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.example.listmaker.app.client.activity.AddNoteListActivity;
import com.example.listmaker.app.client.ui.widget.FormLabel;
import com.example.listmaker.common.client.ui.web.ViewImpl;

public class AddNoteListViewImpl extends ViewImpl<AddNoteListActivity> implements AddNoteListActivity.AddNoteListView
{
	private Button saveButton;
	private TextBox name;
	private FormLabel nameLabel;

    public AddNoteListViewImpl() {

    }

	@Override
	public void init()
	{
		super.init();
		nameLabel = new FormLabel("List name");
		name = new TextBox();
		saveButton = new Button("Save");
        saveButton.addClickHandler(saveClickHandler);
		super.viewPanel.add(viewHeading);
		super.viewPanel.add(nameLabel);
		super.viewPanel.add(name);
		super.viewPanel.add(saveButton);
	}

	@Override
	public Widget asWidget()
	{
		return super.viewPanel;
	}

    @Override
    public void hide() {
        super.viewPanel.setVisible(false);
    }

    @Override
    public void show() {
        super.viewPanel.setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return super.viewPanel.isVisible();
    }

	@Override
	public Image getViewIcon()
	{
		return null;
	}

    /**
     * Handlers
     */

    private ClickHandler saveClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().addNewNoteList();
        }
    };

    @Override
    public HasValue<String> getListName()
    {
        return name;
    }

}
