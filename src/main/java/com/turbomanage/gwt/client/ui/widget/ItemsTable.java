package com.turbomanage.gwt.client.ui.widget;

import com.example.listmaker.common.client.ui.web.AppStyles;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ItemsTable<T> extends Composite implements HasSelectableItems<T>
{
	public static class FieldDescriptor<T>
	{
		public interface FieldAccessor<T>
		{
			Object getObject(T item);
		}

		private String id;
		private String style;
		private String headerText;
		private FieldAccessor<T> accessor;

		public FieldDescriptor(String id, String style, String headerText, FieldAccessor<T> acc)
		{
			this.id = id;
			this.style = style;
			this.headerText = headerText;
			this.accessor = acc;
		}

		public FieldDescriptor(String id, String style, String headerText)
		{
			this.id = id;
			this.style = style;
			this.headerText = headerText;
		}

		public FieldDescriptor(String id, String style)
		{
			this.id = id;
			this.style = style;
		}

		public FieldDescriptor(String id)
		{
			this.id = id;
		}

		public String getId()
		{
			return id;
		}

		public String getStyle()
		{
			return style;
		}

		public void setStyle(String style)
		{
			this.style = style;
		}

		public String getHeaderText()
		{
			return headerText;
		}

		public void setHeaderText(String headerText)
		{
			this.headerText = headerText;
		}

		public FieldAccessor<T> getAccessor()
		{
			return accessor;
		}

		public void setAccessor(FieldAccessor<T> accessor)
		{
			this.accessor = accessor;
		}
	}

	public static abstract class ItemDescriptor<T>
	{
		private ArrayList<FieldDescriptor<T>> fields = new ArrayList<FieldDescriptor<T>>();
		private String itemStyle;

		public ArrayList<FieldDescriptor<T>> getFields()
		{
			return fields;
		}

		/**
		 * Shortcut to providing FieldAccessor for each field, but be careful
		 * that this returns the same number of values as there are
		 * FieldDescriptors.
		 * 
		 * @param item
		 * @return String[] values
		 */
		public abstract String[] getValues(T item);

		public String getItemStyle()
		{
			return itemStyle;
		}

		public ItemDescriptor(String itemStyle)
		{
			this.itemStyle = itemStyle;
		}

		public void addField(FieldDescriptor<T> fieldDescriptor)
		{
			fields.add(fieldDescriptor);
		}

		public FieldDescriptor<T> getFieldDescriptor(String id)
		{
			for (FieldDescriptor<T> fieldDescriptor : fields)
			{
				if (fieldDescriptor.getId().equals(id))
					return fieldDescriptor;
			}
			return null;
		}
	}

	private DivElement headerRow;
	private FlowPanel data = new FlowPanel();
	private ArrayList<T> allItems;
	private ArrayList<T> showingItems = new ArrayList<T>();
	private HashSet<T> selectedItems = new HashSet<T>();
	protected int lastSelectedIndex;
	private DivElement[] itemDivs;
	private ItemDescriptor<T> itemDescriptor;
	private HashMap<String, Object> filters = new HashMap<String, Object>();
	private String headerStyle;

	public ItemsTable(ItemDescriptor<T> desc)
	{
		this.itemDescriptor = desc;
		initWidget(data);
		// Lose browser default SHIFT-click highlighting
		disableTextSelectInternal(data.getElement(), true);
		addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				// Convert click to selection event
				NativeEvent e = event.getNativeEvent();
				Element clicked = Element.as(e.getEventTarget());
				// Find item # from ID
				String[] split = clicked.getId().split(":");
				int itemIndex = Integer.parseInt(split[1]);
				if (e.getCtrlKey())
				{
					selectItem(itemIndex);
				}
				else if (e.getShiftKey())
				{
					clearAllSelected();
					// Select all items between current and previous selection
					int step = (itemIndex > lastSelectedIndex) ? 1 : -1;
					for (int i = lastSelectedIndex; i != itemIndex + step; i += step)
					{
						selectItem(i);
					}
				}
				else
				{
					clearAllSelected();
					selectItem(itemIndex);
				}
				fireEvent(new ItemSelectionEvent<T>(selectedItems));
				lastSelectedIndex = itemIndex;
			}
		});
		addDoubleClickHandler(new DoubleClickHandler()
		{
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{
				// Clear all selections
				clearAllSelected();
				// Convert click to activation event
				Element clicked = Element.as(event.getNativeEvent().getEventTarget());
				// Find item # from ID
				String[] split = clicked.getId().split(":");
				int itemIndex = Integer.parseInt(split[1]);
				T doubleClickedItem = showingItems.get(itemIndex);
//				fireEvent(new ItemActivationEvent<T>(doubleClickedItem));
                showNote();
			}
		});

        initHeader();
	}

    private void showNote() {

    }

	private void initHeader()
	{
		headerRow = Document.get().createDivElement();
		headerRow.addClassName(AppStyles.HEADER_ROW);
		for (FieldDescriptor<T> fd : itemDescriptor.fields)
		{
			DivElement headerCell = Document.get().createDivElement();
			headerCell.addClassName(fd.getStyle());
			headerCell.setInnerText(fd.getHeaderText());
			headerRow.appendChild(headerCell);
		}
	}

	protected void clearAllSelected()
	{
		selectedItems.clear();
		for (DivElement div : itemDivs)
		{
			div.removeClassName(AppStyles.SELECTED);
		}
	}

	protected void selectItem(int itemIndex)
	{
		selectedItems.add(showingItems.get(itemIndex));
		itemDivs[itemIndex].addClassName(AppStyles.SELECTED);
	}

	@Override
	public void addItemActivationHandler(ItemActivationHandler handler)
	{
		addHandler(handler, ItemActivationEvent.TYPE);
	}

	@Override
	public void addItemSelectionHandler(ItemSelectionHandler<T> handler)
	{
		addHandler(handler, ItemSelectionEvent.TYPE);
	}

	protected HandlerRegistration addClickHandler(ClickHandler clickHandler)
	{
		return addDomHandler(clickHandler, ClickEvent.getType());
	}

	protected HandlerRegistration addDoubleClickHandler(DoubleClickHandler doubleClickhandler)
	{
		return addDomHandler(doubleClickhandler, DoubleClickEvent.getType());
	}

	private void refresh()
	{
		// Clear any previous items
		selectedItems.clear();
		lastSelectedIndex = 0;
		data.clear();
		data.getElement().appendChild(headerRow);
		itemDivs = new DivElement[this.showingItems.size()];
		for (int i = 0; i < this.showingItems.size(); i++)
		{
			T item = this.showingItems.get(i);
			DivElement itemDiv = Document.get().createDivElement();
			itemDiv.addClassName(itemDescriptor.getItemStyle());
			String[] values = itemDescriptor.getValues(item);

			for (int j = 0; j < values.length; j++)
			{
				FieldDescriptor<T> f = itemDescriptor.getFields().get(j);
				DivElement fieldDiv = Document.get().createDivElement();
				fieldDiv.setId(f.getId() + ":" + i);
				fieldDiv.addClassName(f.getStyle());
				fieldDiv.setInnerText(values[j]);
				itemDiv.appendChild(fieldDiv);
			}
			itemDivs[i] = itemDiv;
		}
		expando(data, itemDivs);
	}

	@Override
	public void populateAllItems(ArrayList<T> items)
	{
		this.allItems = new ArrayList<T>(items);
	}

	protected void expando(final FlowPanel div2, final DivElement[] notes)
	{
		Timer t = new Timer()
		{
			int i = 0;
			final int last = notes.length;

			@Override
			public void run()
			{
				if (i >= last)
					this.cancel();
				else
					div2.getElement().appendChild(notes[i++]);
			}
		};
		// Eye candy
		t.scheduleRepeating(10);
	}

	protected native static void disableTextSelectInternal(Element e, boolean disable)
	/*-{
		if (disable) {
		    e.ondrag = function () { return false; };
		    e.onselectstart = function () { return false; };
		    e.style.MozUserSelect="none"
		} else {
		    e.ondrag = null;
		    e.onselectstart = null;
		    e.style.MozUserSelect="text"
		}
	}-*/;

	@Override
	public void selectAll()
	{
		for (int i = 0; i < itemDivs.length; i++)
		{
			selectItem(i);
		}
		fireEvent(new ItemSelectionEvent<T>(selectedItems));
	}

	@Override
	public void selectNone()
	{
		clearAllSelected();
		// This can't be moved into clearAllSelected() method or will
		// break multiple selection logic
		this.lastSelectedIndex = 0;
		fireEvent(new ItemSelectionEvent<T>(selectedItems));
	}

	/**
	 * Adds a filter and returns this for filter chaining. Must call refresh to
	 * update display
	 * 
	 * @param fieldId
	 * @param filterValue
	 * @return
	 */
	public void filter(String fieldId, Object filterValue)
	{
		if (filterValue == null)
		{
			filters.remove(fieldId);
		}
		else
		{
			filters.put(fieldId, filterValue);
		}
		filterAndSort();
	}

	private void filterAndSort()
	{
		if (filters.isEmpty())
		{
			showingItems = new ArrayList<T>(allItems);
		}
		else
		{
			showingItems.clear();
			for (T item : this.allItems)
			{
				int match = 1;
				for (Entry<String, Object> e : filters.entrySet())
				{
					String fieldId = e.getKey();
					Object filterValue = e.getValue();
					FieldDescriptor<T> fd = itemDescriptor.getFieldDescriptor(fieldId);
					Object fieldValue = fd.getAccessor().getObject(item);
					if (filterValue.equals(fieldValue))
						match *= 1;
					else
						match *= 0;
				}
				if (match == 1)
					showingItems.add(item);
			}
		}
		refresh();
	}

	/**
	 * Reset filter and sort to defaults
	 */
	public void reset()
	{
		filters.clear();
		showingItems = new ArrayList<T>(allItems);
		refresh();
	}

	public String getHeaderStyle()
	{
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle)
	{
		this.headerStyle = headerStyle;
	}

}