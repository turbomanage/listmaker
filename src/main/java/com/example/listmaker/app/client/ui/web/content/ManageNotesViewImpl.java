package com.example.listmaker.app.client.ui.web.content;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.activity.ManageNotesActivity;
import com.example.listmaker.app.client.domain.Note;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.place.HomePlace;
import com.example.listmaker.app.client.ui.widget.NotesTable;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.example.listmaker.common.client.ui.web.ViewImpl;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.turbomanage.gwt.client.ui.widget.HasSelectedValue;
import com.turbomanage.gwt.client.ui.widget.MessageWidget;
import com.turbomanage.gwt.client.ui.widget.SelectOneListBox;
import com.turbomanage.gwt.client.ui.widget.SelectOneListBox.OptionFormatter;

import java.util.Set;

public class ManageNotesViewImpl extends ViewImpl<ManageNotesActivity> implements ManageNotesActivity.ManageNoteActivityView
{

	// Only one widget to listen for all events
	// Every Widget must be in hierarchy up to that returned by asWidget()

    //All Notes View
	private FlowPanel box;
	private FlowPanel actionBar;
	private FlowPanel buttonSet;
	private Anchor selectAll;
	private Anchor selectNone;
	private Button deleteButton;
	private FlowPanel loadingPanel;
    private NotesTable notesTable;
	private SelectOneListBox<NoteList> selectNoteList;
    private GQuery animation;

	public ManageNotesViewImpl()
	{
        init();
	}

    @Override
    public void init()
    {
        super.init();
        box = new FlowPanel();
        actionBar = new FlowPanel();
        notesTable = new NotesTable();
        buttonSet = new FlowPanel();
        selectNoteList = new SelectOneListBox<NoteList>(new OptionFormatter<NoteList>()
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
        selectNoteList.addStyleName(AppStyles.SELECT_NOTE_LIST);
        selectNoteList.addValueChangeHandler(valueChangedHandler);
        selectAll = new Anchor("All");
        selectAll.addClickHandler(selectAllClickHandler);
        selectNone = new Anchor("None");
        selectNone.addClickHandler(selectNoneClickHandler);
        deleteButton = new Button("Delete");
        deleteButton.addStyleName(AppStyles.ACTION_BUTTON);
        deleteButton.addClickHandler(deleteClickHandler);
        loadingPanel = new FlowPanel();

        box.getElement().setId(AppStyles.ID_MANAGE_NOTES);
        buildActionBar();
        box.add(actionBar);
        notesTable.getElement().setId(AppStyles.ID_NOTES_TABLE);
        notesTable.getSelectionModel().addSelectionChangeHandler(selectionHandler);
        box.add(notesTable);
        loadingPanel.addStyleName(AppStyles.LOADING_PANEL);
        Image loading = App.getAppImages().spin().createImage();
        loadingPanel.add(loading);
        loadingPanel.setVisible(false);
        box.add(loadingPanel);

        viewPanel.add(new MessageWidget(App.getEventBus()).asWidget());
        viewPanel.add(box);
        show();
    }

	private void buildActionBar()
	{
		disableBulkActions();
		actionBar.addStyleName(AppStyles.ACTION_BAR);

		SpanElement listFilterLabel = Document.get().createSpanElement();
		listFilterLabel.setInnerText("Show");
		buttonSet.getElement().appendChild(listFilterLabel);
		buttonSet.add(selectNoteList);

		SpanElement selectLabel = Document.get().createSpanElement();
		selectLabel.setInnerText("Select:");
		buttonSet.getElement().appendChild(selectLabel);
		buttonSet.add(selectAll);
		buttonSet.add(selectNone);
		buttonSet.add(deleteButton);
//        buttonSet.add(showHistory);
        actionBar.add(buttonSet);
	}

    @Override
    public void setActivity(ManageNotesActivity presenter) {
        super.setActivity(presenter);
        notesTable.setListener(presenter);
    }

    @Override
	public Widget asWidget()
	{
		return viewPanel;
	}

    @Override
    public void hide() {
        notesTable.setVisible(false);
        actionBar.setVisible(false);
    }

    @Override
    public void show() {
        notesTable.getListDataProvider().refresh();
        notesTable.setVisible(true);
        actionBar.setVisible(true);
    }

    @Override
    public boolean isShowing() {
        return notesTable.isVisible();
    }

    @Override
	public void startProcessing()
	{
		this.loadingPanel.setVisible(true);
        hide();
	}

	@Override
	public void stopProcessing()
	{
		this.loadingPanel.setVisible(false);
        show();
	}

	@Override
	public void disableBulkActions()
	{
		deleteButton.setEnabled(false);
	}

	@Override
	public void enableBulkActions()
	{
		deleteButton.setEnabled(true);
	}

	@Override
	public void reset()
	{
		notesTable.selectNone();
	}

    /**
     * Handlers
     */

    @Override
    public HasSelectedValue<NoteList> getNoteListFilter()
    {
        return selectNoteList;
    }

    private ClickHandler selectAllClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            notesTable.selectAll();
        }
    };

    private ClickHandler selectNoneClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            reset();
        }
    };

    private ClickHandler deleteClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            getActivity().deleteSelectedNotes();
        }
    };

    private ValueChangeHandler<NoteList> valueChangedHandler = new ValueChangeHandler<NoteList>() {
        @Override
        public void onValueChange(ValueChangeEvent<NoteList> event) {
            App.getClientFactory().getPlaceController().goTo(new HomePlace(String.valueOf(event.getValue().id)));
        }
    };

    private SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
        @Override
        public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
            Set<Note> set = notesTable.getSelectionModel().getSelectedSet();
            getActivity().checkBulkActions(set);
        }
    };

    public NotesTable getNotesTable() {
        return notesTable;
    }

}
