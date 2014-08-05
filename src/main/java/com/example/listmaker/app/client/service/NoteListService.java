package com.example.listmaker.app.client.service;

import com.example.listmaker.app.client.App;
import com.example.listmaker.app.client.domain.NoteList;
import com.example.listmaker.app.client.event.NoteListAddedEvent;
import com.example.listmaker.app.client.event.NoteListsModifiedEvent;
import com.google.gwt.core.shared.GWT;
import com.turbomanage.gwt.client.Display;
import com.turbomanage.gwt.client.event.ShowMessageEvent;
import com.turbomanage.gwt.client.rest.ListResponse;
import com.turbomanage.gwt.client.rest.RestApi;
import com.turbomanage.gwt.client.ui.widget.MessageWidget;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteListService
{

    private static final NoteListRestService service = GWT.create(NoteListRestService.class);

    @Path("/api/noteList")
    public interface NoteListRestService extends RestApi<NoteList> {
    }

	private HashMap<Long, NoteList> noteLists = new HashMap<Long, NoteList>();

    public void refreshNoteLists(final AppCallback<ListResponse<NoteList>> callback) {
        if (noteLists != null) {
            List<NoteList> lists = new ArrayList<NoteList>(noteLists.values());
            ListResponse<NoteList> res = new ListResponse<NoteList>();
            res.list = lists;
            callback.handleSuccess(res);
        }
        service.listAll(new AppCallback<ListResponse<NoteList>>() {
            @Override
            public void handleSuccess(ListResponse<NoteList> responseObj) {
                setNoteLists(responseObj.list);
                callback.handleSuccess(responseObj);
            }
        });
    }


    public void actionAddList(final Display display, NoteList pl) {
        service.save(pl, new AppCallback<NoteList>(display) {
            @Override
            public void handleSuccess(NoteList result) {
                NoteList newNoteList = result;
                addNoteList(newNoteList);
                App.getEventBus().fireEvent(new NoteListAddedEvent(new ArrayList<NoteList>(noteLists.values()), newNoteList));
            }

        });
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.example.listmaker.client.service.NoteListService#deleteSelectedLists(net.customware
	 * .gwt.presenter.client.Display, java.util.ArrayList)
	 */
	public void deleteSelectedLists(final Display display, ArrayList<NoteList> selectedLists)
	{
        final List<Long> listIds = new ArrayList<Long>();
        for (NoteList list : selectedLists) {
            listIds.add(list.id);
        }
		service.deleteMany(listIds, new AppCallback<Integer>(display) {
            @Override
            public void handleSuccess(Integer value) {
                removeLists(listIds);
                App.getEventBus().fireEvent(new NoteListsModifiedEvent(noteLists));
                App.getEventBus().fireEvent(new ShowMessageEvent("Deleted successfully", MessageWidget.MessageType.WARN));
            }
        });
	}

    private void removeLists(List<Long> listIds) {
        for (NoteList pl : noteLists.values()) {
            if (listIds.contains(pl.id)) {
                noteLists.remove(pl);
            }
        }
    }

	public NoteList getNoteList(Long id)
	{
		assert noteLists != null;
		return this.noteLists.get(id);
	}

	private void setNoteLists(List<NoteList> noteLists)
	{
        this.noteLists.clear();
		for (NoteList pl : noteLists)
		{
			addNoteList(pl);
		}
	}

	private void addNoteList(NoteList noteList)
	{
		this.noteLists.put(noteList.id, noteList);
	}

}