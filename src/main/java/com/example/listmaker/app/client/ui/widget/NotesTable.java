package com.example.listmaker.app.client.ui.widget;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.Note;
import com.example.listmaker.common.client.ui.web.AppStyles;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotesTable extends Composite {

    public interface CanEdit {
        void updateNote(Note item);
    }

    private CellTable table = new CellTable();
    private ListDataProvider<Note> dataProvider;
    private MultiSelectionModel selectionModel;
    private ArrayList<Note> notes;
    private CanEdit presenter;
    private int selectedRow;
    private int selectedColumn;

    private Column<Note, String> info = new Column<Note, String>(new EditTextCell()) {
        @Override
        public String getValue(Note item) {
            return item.itemText;
        }
    };

    private TextColumn<Note> list = new TextColumn<Note>() {
        @Override
        public String getValue(Note item) {
            return item.listName;
        }
    };

    SafeHtmlCell safeHtmlCell = new SafeHtmlCell() {
        public Set<String> getConsumedEvents() {
            HashSet<String> events = new HashSet<String>();
            events.add(BrowserEvents.CLICK);
            return events;
        }
    };

    public NotesTable() {
        selectionModel = new MultiSelectionModel<Note>();
        table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Note> createDefaultManager());
        info.setCellStyleNames(AppStyles.NOTE_TEXT);
        list.setCellStyleNames(AppStyles.NOTE_LIST_NAME);
        table.addColumn(info, "Note");
//        table.addColumn(list, "List");
        initWidget(table);
        dataProvider = new ListDataProvider<Note>();
        dataProvider.addDataDisplay(table);

        table.addCellPreviewHandler(new CellPreviewEvent.Handler() {
            @Override
            public void onCellPreview(CellPreviewEvent event) {
                if (BrowserEvents.CLICK.equalsIgnoreCase(event.getNativeEvent().getType())) {
                    selectedColumn = event.getColumn();
                    selectedRow = event.getIndex();
                    //used to prepare double click info
                }
            }
        });
    }

    public void setListener(final CanEdit presenter) {
        this.presenter = presenter;
        info.setFieldUpdater(new FieldUpdater<Note, String>() {
            @Override
            public void update(int i, Note note, String s) {
                if (!note.itemText.equals(s)) {
                    note.itemText = s;
                    presenter.updateNote(note);
                }
            }
        });

    }
    public CellTable getTable() {
        return table;
    }

    public ListDataProvider<Note> getListDataProvider() {
        return dataProvider;
    }

    public MultiSelectionModel<Note> getSelectionModel() {
        return selectionModel;
    }

    public void selectAll() {
        for (Note note : dataProvider.getList()) {
            selectionModel.setSelected(note, true);
        }
    }

    public void selectNone() {
        selectionModel.clear();
    }

    public void inputNotes(List<Note> notes) {
        table.setVisibleRange(0, notes.size());
        dataProvider.setList(notes);
        dataProvider.refresh();
    }

    @Override
    public Widget asWidget() {
        return table;
    }

}