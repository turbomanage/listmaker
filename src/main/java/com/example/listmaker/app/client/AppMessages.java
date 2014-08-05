/*
 * Copyright 2009 David M Chandler All Rights Reserved.
 */

package com.example.listmaker.app.client;

import com.google.gwt.i18n.client.Messages;

/**
 * Parameterized messages
 * 
 * @author David Chandler
 */
public interface AppMessages extends Messages {
	String welcome(String name);
	String manageListsTitle();
	String notes_view_add_note_label();
}