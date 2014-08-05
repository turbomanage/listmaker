package com.example.listmaker.test;

import com.example.listmaker.common.domain.*;
import com.example.listmaker.server.dao.NoteDao;
import com.example.listmaker.server.dao.UserPrefsDao;
import com.example.listmaker.test.helper.BaseTest;
import com.turbomanage.gwt.exception.TooManyResultsException;
import com.turbomanage.gwt.server.servlet.AuthFilter;

public class NotesTestCase extends BaseTest
{
	User u;
	NoteList pl;
    NoteDao dao = new NoteDao();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		u = AuthFilter.getUser();
		pl = NoteListTestCase.addTestNoteList(u);
		NoteListTestCase.addTestNoteItems(u, pl);
	}

	public void testFindItems() throws Exception
	{
        ListWrapper<Note> all = dao.findAll();
		assertEquals(30, all.getList().size());
	}

}