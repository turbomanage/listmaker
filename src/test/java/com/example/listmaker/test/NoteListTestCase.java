package com.example.listmaker.test;

import com.example.listmaker.common.domain.ListWrapper;
import com.example.listmaker.common.domain.Note;
import com.example.listmaker.common.domain.NoteList;
import com.example.listmaker.common.domain.User;
import com.example.listmaker.server.dao.NoteDao;
import com.example.listmaker.server.dao.NoteListDao;
import com.example.listmaker.test.helper.BaseTest;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import java.util.ArrayList;
import java.util.List;

public class NoteListTestCase extends BaseTest
{
	User u;
	NoteList pl;
    NoteListDao dao;

	@Override
	protected void setUp() throws Exception
	{
        super.setUp();
        u = AuthFilter.getUser();
        dao = new NoteListDao();
		pl = NoteListTestCase.addTestNoteList(u);
	}

	public void testFindNoteLists() throws Exception
	{
        ListWrapper<NoteList> all = dao.findAll();
		assertTrue(all.getList().size() == 1);
	}

	public void testFindNoteList() throws Exception
	{
		NoteListDao noteListDao = new NoteListDao();
		NoteList noteList = noteListDao.get(pl.getId());
		assertNotNull(noteList);
        NoteList list = dao.get(pl.getId());
        assertNotNull(list);
	}

	public void testAddItem() throws Exception
	{
		Note item = new Note();
		item.setItemText("Important item");
		item = NoteListTestCase.addNoteItem(u, pl, item);
        Ref<NoteList> listRef = Ref.create(pl);
		assertNotNull(item.getId());
		assertEquals("Important item", item.getItemText());
		assertEquals(listRef, item.getListKey());
	}

	public void testAddNoteList() throws Exception
	{
		NoteList newList = new NoteList();
		newList.setName("Test List");
        newList.setOwnerKey(Ref.create(u));
        dao.save(newList);

		// Verify that all attributes are persisted
        NoteList saved = dao.get(newList.getId());

		// ID should be set
		assertNotNull(saved.getId());
	}

	public void testDeleteNoteLists() throws Exception
	{
		NoteListTestCase.addTestNoteItems(u, pl);

		// Delete list
		NoteListTestCase.deleteNoteList(pl);

		// Verify no items remain
        Ref<NoteList> listKey = Ref.create(pl);
		List<NoteList> foundItems = dao.listByProperty("listKey", listKey);
		assertEquals(0, foundItems.size());
	}

	public static NoteList addTestNoteList(User u) throws Exception
	{
		// Add list
		NoteList pl = new NoteList();
		pl.setName("Test List");
        Key<User> ownerKey = Key.create(User.class, u.getId());
		pl.setOwnerKey(Ref.create(u));
        return new NoteListDao().save(pl);
	}

	public static void addTestNoteItems(User u, NoteList pl)
		throws Exception
	{
		for (int i=0; i<30; i++)
		{
			Note item = new Note("Item " + i);
			Note newItem = addNoteItem(u, pl, item);
		}
	}

	public static int deleteNoteList(NoteList pl)
		throws Exception
	{
		List<NoteList> deleteLists = new ArrayList<NoteList>();
		deleteLists.add(pl);
        List<Long> ids = new ArrayList<Long>();
        for (NoteList noteList : deleteLists) {
            ids.add(noteList.getId());
        }
		return new NoteListDao().removeMany(ids);
	}

	public static Note addNoteItem(User u, NoteList pl, Note i)
		throws Exception
	{
        i.setListId(pl.getId());
        return new NoteDao().save(i);
	}
}
