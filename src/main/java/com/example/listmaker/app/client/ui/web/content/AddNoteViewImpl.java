package com.example.listmaker.app.client.ui.web.content;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.*;
import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.AddNoteActivity;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.example.listmaker.common.client.ui.widget.FormBuilder;
import com.turbomanage.gwt.client.ui.widget.HasSelectedValue;
import com.turbomanage.gwt.client.ui.widget.PromptedTextBox;
import com.turbomanage.gwt.client.ui.widget.SelectOneListBox;
import com.turbomanage.gwt.client.ui.widget.SelectOneListBox.OptionFormatter;

/**
 *
 */
public class AddNoteViewImpl extends ViewImpl<AddNoteActivity> implements AddNoteActivity.AddNoteView
{
    private FormBuilder fb;
    private Image spin;
    private PromptedTextBox what;
    private SelectOneListBox<NoteList> inList;
	private ListBox when;
	private Button saveButton;
	private Button cancelButton;

	public AddNoteViewImpl()
	{
        init();
	}

	@Override
	public void init()
	{
        super.init();
		fb = new FormBuilder("newNote", "New Note");
		what = new PromptedTextBox("What...", AppStyles.PROMPT);
		when = new ListBox();
		inList = new SelectOneListBox<NoteList>(new OptionFormatter<NoteList>()
		{
			@Override
			public String getLabel(NoteList option)
			{
				return option.name;
			}

			@Override
			public String getValue(NoteList option)
			{
				return String.valueOf(option.id);
			}

		});
		inList.addItem("Select list", AppStyles.SELECT_PROMPT.toString());
		inList.addStyleName(AppStyles.SELECT_NOTE_LIST);
        inList.addValueChangeHandler(valueChangeHandler);
		saveButton = new Button();
        saveButton.addClickHandler(saveClickHandler);
		cancelButton = new Button();
        cancelButton.addClickHandler(cancelClickHandler);
		spin = App.getAppImages().spin().createImage();
		spin.setVisible(false);
		saveButton.addStyleName(AppStyles.ACCEPT_BUTTON);
		saveButton.setTitle("Save");
		cancelButton.addStyleName(AppStyles.CANCEL_BUTTON);
		cancelButton.setTitle("Cancel");
		what.setTitle("Enter note");
		inList.setTitle("Select a list for this request");
		fb.addField(what, "what");
		fb.addField(inList, "list");
		fb.addField(saveButton, "save");
		fb.addField(spin, "spin");
		fb.addField(cancelButton, "cancel");
	}

	@Override
	public void reset()
	{
		what.showPrompt();
	}

	@Override
	public String getNewNoteSelectedList()
	{
		int selectedIndex = inList.getSelectedIndex();
		return inList.getValue(selectedIndex);
	}

    @Override
    public UIObject getNewListAnchor()
    {
        return inList;
    }

	@Override
	public void focusOnSaveButton()
	{
		DeferredCommand.addCommand(new Command()
		{
			@Override
			public void execute()
			{
				saveButton.setFocus(true);
			}
		});
	}

    @Override
    public Widget asWidget()
    {
        return fb.asWidget();
    }

    @Override
    public void hide() {
        fb.asWidget().setVisible(false);
    }

    @Override
    public void show() {
        fb.asWidget().setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return fb.asWidget().isVisible();
    }

    @Override
    public void startProcessing()
    {
        super.startProcessing();
        saveButton.setVisible(false);
        spin.setVisible(true);
    }

    @Override
    public void stopProcessing()
    {
        super.stopProcessing();
        spin.setVisible(false);
        saveButton.setVisible(true);
    }

    /**--------------
     * Handlers
     * --------------
     */

    private ClickHandler saveClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().addNewNote();
        }
    };

    private ClickHandler cancelClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            reset();
        }
    };

    private ValueChangeHandler<NoteList> valueChangeHandler = new ValueChangeHandler<NoteList>() {
        @Override
        public void onValueChange(ValueChangeEvent<NoteList> valueChangeEvent) {
            if (valueChangeEvent.getValue().id == 0) {
                getActivity().createNewNoteListActivity();
            }
        }
    };

    @Override
    public HasSelectedValue<NoteList> getNoteListsBox()
    {
        return inList;
    }

    @Override
    public HasValue<String> getWhat()
    {
        return what;
    }


}