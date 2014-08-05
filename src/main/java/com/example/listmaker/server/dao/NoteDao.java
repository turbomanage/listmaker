package com.example.listmaker.server.dao;

import java.util.*;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.example.listmaker.common.domain.*;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("api/note")
public class NoteDao extends RestServiceDao<Note>
{
	private static final Logger LOG = Logger.getLogger(NoteDao.class.getName());

    @Override
    @Path("all")
    @GET
    public ListWrapper<Note> findAll() {
        User user = AuthFilter.getUser();
        List<Note> notes = this.listByOwner(user);
        return new ListWrapper<Note>(notes);
    }

    @Override
    public Key<Note> put(Note item)
    {
        item.setDateCreated(new Date());
        Ref<Note> itemKey = Ref.create(super.put(item));
        return itemKey.key();
    }

    @Override
    @Path("save")
    @POST
    public Note save(Note item) {
        // Populate item
        // User creator = action.getItemCreator();
        User creator = AuthFilter.getUser();
        long listId = item.getListId();
        Ref<User> creatorKey = Ref.create(creator);
        Ref<NoteList> listKey = Ref.create(Key.create(NoteList.class, listId));
        item.setOwnerKey(creatorKey);
        item.setDateCreated(new Date());
        item.setListKey(listKey);
        this.put(item);

        LOG.info("Saved item = " + item.getId() + "and itemText = " + item.getItemText());
        return item;
    }

	@Override
	public void delete(Note note)
	{
		super.delete(note);
	}

	@Override
    @POST
    @Path("deleteMany")
	public int removeMany(List<Long> ids)
	{
		return super.removeMany(ids);
	}

	/**
	 * Group items by list.
	 * 
	 * @param items
	 * @return List<NoteList>
	 */
	public static List<NoteList> prepareListOfLists(ArrayList<Note> items)
	{
		// List of lists
		List<NoteList> lol = new ArrayList<NoteList>();
		NoteList pl = new NoteList();
		for (Note item : items)
		{
			if (!item.getListName().equals(pl.getName()))
			{
				pl = new NoteList();
				pl.setName(item.getListName());
				pl.setItems(new ArrayList<Note>());
				lol.add(pl);
			}
			pl.getItems().add(item);
		}
		return lol;
	}

}