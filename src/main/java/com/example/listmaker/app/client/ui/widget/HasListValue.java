package com.example.listmaker.app.client.ui.widget;

import java.util.List;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;

public interface HasListValue extends HasValue<String>, HasValueChangeHandlers<String>
{
	List<String> getValues();
}