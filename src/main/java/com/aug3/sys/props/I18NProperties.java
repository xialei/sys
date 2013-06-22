package com.aug3.sys.props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class I18NProperties implements Serializable {

	private final static Logger log = Logger.getLogger(I18NProperties.class);

	protected String relpath;

	// the internationalized filename
	protected String filename;

	private static final String DOLLAR_SIGN = "$";

	private static final String UNDERLINE = "_";

	/**
	 * HashMap for remembering the FileProperties we've already seen.
	 * 
	 * The map key is the Locale String.
	 */
	protected final Map<String, FileProperties[]> mapFileProps = new HashMap<String, FileProperties[]>();

	public I18NProperties(String filename) {
		this(null, filename);
	}

	public I18NProperties(String relativePath, String filename) {
		this.relpath = relativePath;
		this.filename = filename;
	}

	/**
	 * Refresh.
	 * 
	 * All this does is throw away the cache so we'll read everything back in as
	 * needed.
	 * 
	 * It is synchronized on the cache object to make sure that this method and
	 * getProperties don't get each other confused.
	 * 
	 */
	public void refreshProperties() {
		synchronized (mapFileProps) {
			log.info("Refreshing I18NProperties");
			mapFileProps.clear();
		}
	}

	/**
	 * Get the FileProperties associated with the specified Locale.
	 * 
	 * This method backs off on the specified Locale as necessary, in a manner
	 * similar to what the java.util.ResourceBundle.getBundle method would,
	 * although somewhat simpler.
	 * 
	 * Note that this method is synchronized on the cache object. This
	 * eliminates multiple FileProperties objects being created for the same
	 * Locale, and also allows us to use the HashMap class .
	 * 
	 * @param locale
	 *            Locale (never null) -- Locale.ENGLISH,
	 *            Locale.SIMPLIFIED_CHINESE
	 * 
	 * @return array of FileProperties objects for the specified Locale, in
	 *         descending order of specificity (may be zero length but shouldn't
	 *         since this method backs off to the default Locale as necessary)
	 */
	protected FileProperties[] getProperties(Locale locale) {

		FileProperties[] fileProps = null;
		synchronized (mapFileProps) {

			// Get the FileProperties array from the cache if it's there
			// "en", "zh_CN", "de_DE", "_GB", "en_US_WIN", "de__POSIX",
			// "fr__MAC"
			String mapKey = locale.toString();
			mapKey = mapKey.intern();
			fileProps = (FileProperties[]) mapFileProps.get(mapKey);

			// If it's not in the cache...
			if (null == fileProps) {
				log.debug("Initialize properties files " + this.filename);
				String baseName = this.filename;
				String[] names = new String[4];
				int numNames = 0;
				names[numNames++] = baseName;
				if (!StringUtils.isBlank(locale.getLanguage())) {
					names[numNames++] = baseName + UNDERLINE + locale.getLanguage();
					if (!StringUtils.isBlank(locale.getCountry())) {
						names[numNames++] = baseName + UNDERLINE + locale.getLanguage() + UNDERLINE
								+ locale.getCountry();
						if (!StringUtils.isBlank(locale.getVariant())) {
							names[numNames++] = baseName + UNDERLINE + locale.getLanguage() + UNDERLINE
									+ locale.getCountry() + UNDERLINE + locale.getVariant();
						}
					}
				}

				// Try each of the names, starting with the most specific
				List<FileProperties> list_fileproperties = new ArrayList<FileProperties>(numNames);
				for (int i = numNames - 1; i >= 0; i--) {
					FileProperties fp = null;
					try {
						fp = (FileProperties) new FileProperties(relpath, names[i]);
					} catch (Exception ex) {
						fp = null;
					}
					if (null != fp) {
						list_fileproperties.add(fp);
						log.debug("Get file properties successfully - " + names[i]);
					} else {
						log.debug("Get file properties failed - " + names[i]);
					}
				}

				/*
				 * Convert the results to an array (more efficient, remembering
				 * that most of the time we will get a hit in the cache)
				 */
				fileProps = new FileProperties[list_fileproperties.size()];
				fileProps = (FileProperties[]) (list_fileproperties.toArray(fileProps));

				/*
				 * Save the FileProperties, if any, in the cache (even if there
				 * isn't one, this prevents the expensive process of finding
				 * that out each time)
				 */
				mapFileProps.put(mapKey, fileProps);
			}
		}

		return fileProps;
	}

	/**
	 * Get the internationalized string for the specified key.
	 * 
	 * @param key
	 *            key to look up in the properties file
	 * @param locale
	 *            Locale (used to determine the language)
	 * 
	 * @return internationalized string
	 */
	public String getValue(String key, Locale locale) {

		// Make sure we have a locale
		locale = rectifyVariantLocale(locale);
		log.debug("I18NProperties.getValue(): Locale is " + locale);
		if (null == locale) {
			throw new IllegalArgumentException("I18NProperties.getValue: Null locale");
		}

		// Get the appropriate FileProperties objects
		FileProperties[] fileProps = getProperties(locale);

		// Try to look it up in the FileProperties, in order
		String iName = null;
		for (int i = 0; i < fileProps.length; i++) {
			iName = fileProps[i].getProperty(key);
			if (null != iName) {
				break;
			}
		}

		// if (iName != null) {
		// iName = replaceCurrencySymbol(iName, locale);
		//
		// } else {
		// log.info("I18NProperties.getValue not found: " + key + ", " +
		// locale.toString());
		// }
		return iName;
	}

	/**
	 * Get the internationalized string for the specified group + subKey.
	 * 
	 * @param groupName
	 *            group name of the key
	 * @param memberName
	 *            member name of the key
	 * @param locale
	 *            Locale (used to determine the language)
	 * @return internationalized (and possibly company-specific) string
	 */
	public String getValue(String groupName, String memberName, Locale locale) {
		String key = (groupName + "." + memberName).replace(' ', '_').replace(':', '.');
		String value = getValue(key, locale);

		if (value == null || value.length() == 0) {
			if (memberName.length() == 0) {
				value = groupName;
			} else {
				value = memberName;
			}
		}

		return value;
	}

	/**
	 * if current locale has $ as its currency, do not bother to replace
	 * currency symbol. country must not be null.
	 * 
	 * @param iName
	 * @param locale
	 * @return
	 */
	protected static String replaceCurrencySymbol(String iName, Locale locale) {
		String currency = Currency.getInstance(locale).getSymbol(locale);
		if (!currency.equals(DOLLAR_SIGN)) {
			iName.replace(DOLLAR_SIGN, currency);
		}
		return iName;
	}

	/**
	 * Currently some (JDBC, to name a few ) Java libraries are unable to handle
	 * the Variant component of Locale. For instance, fr_FR_EURO, returns
	 * "FR_EURO" as country, that is causing exceptions
	 * 
	 * @param locale
	 * @return
	 */
	public static Locale rectifyVariantLocale(Locale locale) {
		String country = locale.getCountry();
		if (country.indexOf(UNDERLINE) >= 2 && country.length() > 2) {
			// Could be a EURO variant, problems galore here
			String ctry[] = country.split(UNDERLINE);
			if (ctry.length > 1) {
				country = ctry[0];
				String variant = ctry[1];
				locale = new Locale(locale.getLanguage(), country, variant);
			} else {
				locale = new Locale(locale.getLanguage(), country.substring(0, 2));
			}

		}
		return locale;
	}

	public static void main(String[] args) {
		I18NProperties i18n = new I18NProperties("language");
		System.out.println(i18n.getValue("test", Locale.ENGLISH));
		System.out.println(i18n.getValue("test", Locale.SIMPLIFIED_CHINESE));
		System.out.println(i18n.getValue("default", Locale.SIMPLIFIED_CHINESE));
	}

}
