package com.example.listmaker.common.client.util;

import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Does a properties file lookup to get text associated with an enum value
 * Property keys use the full class name with all dots and dollars
 * converted to underscores. Keys are case-sensitive and GWT requires a
 * method in the interface that extends ConstantsWithLookup, even though 
 * the method is never called.
 * 
 * Example:
 * String my_package_class_Frequency_DAILY();
 * 
 * In the corresponding properties file:
 * my_package_class_Frequency_DAILY=daily
 * 
 * @author David Chandler
 */
public class EnumTranslator
{
	private ConstantsWithLookup constants;
	
	public EnumTranslator(ConstantsWithLookup constants)
	{
		this.constants = constants;
	}
	
	public String getText(Enum e)
	{
		String lookupKey = e.getClass().getName() + "." + e.name();
		lookupKey = lookupKey.replace(".", "_");
		lookupKey = lookupKey.replace("$", "_");
		return constants.getString(lookupKey);
	}
}
