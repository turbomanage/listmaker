package com.example.listmaker.common.client.ui.web;

import com.google.gwt.resources.client.ClientBundle;

/**
 * CSS style names
 * 
 * @author David Chandler
 */
public interface AppStyles extends ClientBundle
{
	static final String BODY_PANEL_CONTENT_ID = "main";
	static final String BODY_PANEL_NAV_ID = "quick_links";
    static final String BODY_PANEL_TOP_ID = "top";
    static final String BODY_PANEL_USER_ID = "user";
	static final String MESSAGE_PANEL_ID = "listmaker-message-panel";
	static final String ID_LISTS_TABLE = "listmaker-lists-table";
	static final String ID_SPLASH = "loading";
	static final String ID_USER_LINKS = "user_links";
	static final String ID_DETAIL_MENU = "detail_menu";
	static final String ID_MANAGE_NOTES = "manage_notes";
	static final String ID_NOTES_TABLE = "notes_table";
	static final String ID_USER_VIEW = "user_view";

    static final String ACCEPT_BUTTON = "listmaker-acceptButton";
    static final String CANCEL_BUTTON = "listmaker-cancelButton";
    static final String ACTION_BUTTON = "listmaker-actionButton";
    static final String BUTTON_FACE = "listmaker-buttonFace";
    static final String BUTTON_ICON = "listmaker-buttonIcon";
    static final String BUTTON_LABEL = "listmaker-buttonLabel";
    static final String FORM_LABEL = "listmaker-formLabel";
    static final String HEADING_1 = "listmaker-heading1";
    static final String TABLE_ROW = "listmaker-tableRow";
    static final String VIEW_ICON = "listmaker-viewIcon";
    static final String MESSAGE_INFO = "listmaker-messageInfo";
    static final String MESSAGE_WARN = "listmaker-messageWarn";
    static final String VIEW_PANEL = "listmaker-viewPanel";
    static final String TOGGLE_IMAGE = "toggleImage";
    static final String INLINE = "inline";
    static final String HORIZONTAL_MENU = "listmaker-hMenu";
    static final String LOADING_PANEL = "listmaker-viewLoading";
    static final String LISTMAKER_VIEW = "listmaker-view";
    static final String NOTE_LIST_NAME = "listmaker-noteItemListName";
    static final String NOTE_TEXT = "listmaker-noteItemText";
    static final String ACTION_BAR = "listmaker-actionBar";
    static final String PROMPT = "listmaker-prompt";
    static final String SELECTED = "selected";
    static final String HEADER_ROW = "listmaker-headerRow";
    static final String SELECT_NOTE_LIST = "listmaker-selectNoteList";
    static final Long SELECT_PROMPT = -2L;
    // Special values in ListBoxes
	static final Long NEW_PROMPT = 0L;
    static final Long SHOW_ALL_PROMPT = -1L;
}
