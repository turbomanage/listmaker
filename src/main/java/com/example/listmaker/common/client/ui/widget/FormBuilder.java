package com.example.listmaker.common.client.ui.widget;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utility class to build form using fieldset (CaptionPanel) and DIV for each
 * field + label
 * 
 * @author David Chandler
 */
public class FormBuilder
{
	private FormPanel form = new FormPanel();
	private CaptionPanel captionPanel = new CaptionPanel();
	private FlowPanel contentPanel = new FlowPanel();
	private String formId;

	public FormBuilder(String id, String caption)
	{
		this.formId = id;
		form.getElement().setId(id);
		captionPanel.setCaptionText(caption);
		captionPanel.setContentWidget(contentPanel);
		form.add(captionPanel);
	}
	
	public void addField (Widget w, String id)
	{
		id = formId + "_" + id;
		w.getElement().setId(id);
		contentPanel.add(w);
	}
	
	public void addFieldWithLabel(Widget w, String id, String label, String... styles)
	{
		// Prepend form ID for uniqueness
		id = formId + "_" + id;
		// Create HTML DIV for each field
		FlowPanel div = new FlowPanel();
		div.getElement().setId("div_" + id);
		if (w instanceof HasName)
		{
			((HasName) w).setName(id);
		}
		w.getElement().setId(id);
		if (label != null)
		{
			div.add(createLabel(id, label));
		}
		div.add(w);
		for (String styleName : styles)
		{
			div.addStyleName(styleName);
		}
		contentPanel.add(div);
	}

	private LabelWidget createLabel(String forId, String label)
	{
		LabelWidget labelWidget = new LabelWidget();
		labelWidget.add(new InlineLabel(label));
		labelWidget.setHtmlFor(forId);
		return labelWidget;
	}

	public Widget asWidget()
	{
		return form;
	}

}
