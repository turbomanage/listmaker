package com.example.listmaker.app.client.ui.web.content;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.*;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.NewNoteListActivity;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.client.ui.widget.FormBuilder;
import com.example.listmaker.common.client.ui.widget.SubmittableField;
import com.example.listmaker.common.client.ui.widget.SubmittableTextBox;

public class NewNoteListViewImpl extends ViewImpl<NewNoteListActivity> implements NewNoteListActivity.NewNoteListView

{
    private PopupPanel newListPopup;
    private FocusPanel focusPanel;
    private Button newListSaveButton;
	private Button cancelButton;
    private FormBuilder newListForm;
    private SubmittableTextBox newListName;
	private Image ajaxImage;

	public NewNoteListViewImpl()
	{
        init();
	}

    @Override
    public void init()
    {
        super.init();
        newListPopup = new PopupPanel();
        focusPanel = new FocusPanel();
        newListForm = new FormBuilder("newList", "New List");
        newListName = new SubmittableTextBox();
        newListSaveButton = new Button();
        newListSaveButton.addClickHandler(saveClickHandler);
        cancelButton = new Button();
        cancelButton.addClickHandler(cancelClickHandler);
        cancelButton.addKeyDownHandler(onKeyCancel);
        ajaxImage = App.getAppImages().spin().createImage();
        // configure list popup
        newListForm.addFieldWithLabel(newListName, "listName", "Name", AppStyles.INLINE);
        newListSaveButton.addStyleName(AppStyles.ACCEPT_BUTTON);
        newListForm.addFieldWithLabel(newListSaveButton, "newListSave", null, AppStyles.INLINE);
        newListForm.addFieldWithLabel(ajaxImage, "wait", null, AppStyles.INLINE);
        cancelButton.addStyleName(AppStyles.CANCEL_BUTTON);
        newListForm.addFieldWithLabel(cancelButton, "cancel", null, AppStyles.INLINE);
        focusPanel.add(newListForm.asWidget());
        ajaxImage.setVisible(false);
        newListPopup.setWidget(focusPanel);
        newListPopup.setAnimationEnabled(true);
        hide();
    }

	public SubmittableField<String> getNewListName()
	{
		return newListName;
	}

	@Override
	public void startProcessing()
	{
		super.startProcessing();
		ajaxImage.setVisible(true);
		newListSaveButton.setVisible(false);
	}

	@Override
	public void stopProcessing()
	{
		super.stopProcessing();
		this.reset();
		this.hide();
	}

	@Override
	public void hide()
	{
		newListPopup.hide(true);
	}

	@Override
	public boolean isShowing()
	{
		return newListPopup.isShowing();
	}

	@Override
	public void reset()
	{
		newListName.setValue(null);
		ajaxImage.setVisible(false);
		newListSaveButton.setVisible(true);
	}

	@Override
	public void show()
	{
        newListPopup.show();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute () {
                newListName.setFocus(true);
            }
        });
	}

	@Override
	public Widget asWidget()
	{
		return newListPopup;
	}

	@Override
	public void showRelativeTo(UIObject target)
	{
		newListPopup.showRelativeTo(target);
		newListPopup.show();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute () {
                newListName.setFocus(true);
            }
        });
	}

    /**
     * Handlers
     */

    private ClickHandler saveClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().addNoteList();
        }
    };

    private ClickHandler cancelClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            reset();
            hide();
        }
    };

    private KeyDownHandler onKeyCancel = new KeyDownHandler()
    {
        @Override
        public void onKeyDown(KeyDownEvent keyDownEvent) {
            reset();
            hide();
        }
    };

}
