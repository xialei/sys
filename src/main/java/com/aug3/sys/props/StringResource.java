package com.aug3.sys.props;

import java.util.Locale;

public class StringResource {

	/**
	 * HashMap for remembering the FileProperties we've already seen.
	 * 
	 * The map key is the Locale string.
	 */
	private static I18NProperties i18nProperties;

	/**
	 * There is no reason to construct an instance
	 */
	private StringResource() {
	}

	/**
	 * Get the internationalized string for the specified resource key from
	 * string resource bundle..
	 * 
	 * @param resourceKey
	 * @param locale
	 *            Locale (used to determine the language)
	 * @return internationalized string
	 */
	public static String getValue(String resourceKey, Locale locale) {

		if (i18nProperties == null) {
			synchronized (StringResource.class) {
				if (i18nProperties == null) {
					i18nProperties = new I18NProperties("lang", "string");
				}
			}
		}
		return i18nProperties.getValue(resourceKey, locale);
	}
	
	/**
     * Get the internationalized string for the specified resource key from
     * string resource bundle..
     * if can't find the key, return default value 
	 */
	public static String getValue(String resourceKey, Locale locale, String defaultValue) {
	    String value = getValue(resourceKey, locale);
	    if(value == null){
	        return defaultValue;
	    }else{
	        return value;
	    }
	}

	static void refreshProperties() {
		if (i18nProperties != null) {
			i18nProperties.refreshProperties();
		}
	}

}
