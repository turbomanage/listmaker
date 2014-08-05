package com.example.listmaker.app.client.ui.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.example.listmaker.common.client.util.EnumTranslator;

public class EnumListBox<T extends Enum<T>> extends ListBox implements HasValue<T>
{

	private final Class<T> clazzOfEnum;
	private boolean valueChangeHandlerInitialized;

	public EnumListBox(final Class<T> clazzOfEnum, final ConstantsWithLookup constants)
	{
		if (clazzOfEnum == null)
			throw new IllegalArgumentException("Enum class cannot be null");

		this.clazzOfEnum = clazzOfEnum;
		EnumTranslator enumTranslator = new EnumTranslator(constants);

		T[] values = clazzOfEnum.getEnumConstants();

		for (T value : values)
		{
//			this.addItem(constant.toString(), constant.name());
			this.addItem(enumTranslator.getText(value), value.name());
		}
	}

	public T getSelectedValue()
	{
		if (getSelectedIndex() >= 0)
		{
			String name = getValue(getSelectedIndex());

			T[] values = clazzOfEnum.getEnumConstants();
			for (T value : values)
			{
				if (value.name().equals(name))
					return value;
			}
		}

		return null;
	}

	public void setSelectedValue(T value)
	{
		T[] values = clazzOfEnum.getEnumConstants();
		for (int i = 0; i < values.length; i++)
		{
			if (values[i] == value)
			{
				this.setSelectedIndex(i);
				return;
			}
		}
		throw new IllegalArgumentException("No index found for value " + value.toString());
	}

	/*
	 * Methods to implement HasValue 
	 */
	
	@Override
	public T getValue()
	{
		return this.getSelectedValue();
	}

	@Override
	public void setValue(T value)
	{
		this.setValue(value, false);
	}

	@Override
	public void setValue(T value, boolean fireEvents)
	{
		T oldValue = getValue();
		this.setSelectedValue(value);
		if (fireEvents)
		{
			ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
	{
		// Initialization code
		if (!valueChangeHandlerInitialized)
		{
			valueChangeHandlerInitialized = true;
			super.addChangeHandler(new ChangeHandler()
			{
				public void onChange(ChangeEvent event)
				{
					ValueChangeEvent.fire(EnumListBox.this, getValue());
				}
			});
		}
		return addHandler(handler, ValueChangeEvent.getType());
	}

}
