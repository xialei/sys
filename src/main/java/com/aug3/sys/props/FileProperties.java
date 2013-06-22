package com.aug3.sys.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.aug3.sys.exception.CommonException;

/**
 * This class provides functionality to access properties from properties file.
 * 
 * @author Roger.xia
 */
@SuppressWarnings("serial")
public class FileProperties implements Serializable {

	private final static Logger log = Logger.getLogger(FileProperties.class);

	protected String relPath;

	protected String propName;

	protected FilePropertiesEntry defaultProps;

	/**
	 * constructor with the properties file name
	 * 
	 * @param name
	 *            the properties file name
	 * @throws CommonException
	 */
	public FileProperties(String name) throws Exception {

		this(null, name);
	}

	/**
	 * constructor with the properties file name
	 * 
	 * @param relativePath
	 * 
	 * @param name
	 *            the properties file name
	 * @throws CommonException
	 */
	public FileProperties(String relativePath, String name) throws Exception {

		relPath = rectifyRelativePath(relativePath);

		// set the file name of the properties
		propName = name;

		// load the properties from file
		load();
	}

	private String rectifyRelativePath(String relativePath) {

		if (relativePath == null || relativePath.trim().length() == 0) {
			relativePath = "/";
		} else {
			if (!relativePath.startsWith("/")) {
				relativePath = "/" + relativePath;
			}
			if (!relativePath.endsWith("/")) {
				relativePath = relativePath + "/";
			}
		}
		return relativePath;
	}

	/**
	 * load the properties from the file storage
	 * 
	 * @throws CommonException
	 */
	protected synchronized void load() throws Exception {

		Properties ps = new Properties();

		InputStream fis = null;
		try {
			fis = FileProperties.class.getResourceAsStream(relPath + propName + ".properties");
			ps.load(fis);
		} catch (FileNotFoundException e) {
			// no updated properties file, that means there's no custom values
		} catch (IOException e) {
			String msg = "failed to read properties from file [" + propName + "]";
			log.warn(msg, e);
			throw new CommonException(msg, e);
		} finally {
			try {
				if (null != fis)
					fis.close();
			} catch (IOException e) {
				log.warn("failed to close file handle [" + propName + "]", e);
			}
		}

		defaultProps = new FilePropertiesEntry(propName, ps);

	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is not found in this property list, the default property list,
	 * and its defaults, recursively, are then checked. The method returns null
	 * if the property is not found.
	 * 
	 * 
	 * @param key
	 *            the hashtable key
	 * @return the value in this property list with the specified key
	 */
	public String getProperty(String key) {

		return defaultProps.props.getProperty(key);
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is not found in this property list, the default property list,
	 * and its defaults, recursively, are then checked. The method returns the
	 * default value argument if the property is not found.
	 * 
	 * 
	 * @param key
	 *            the hashtable key
	 * @param dft
	 *            a default value
	 * @return the value in this property list with the specified key
	 */
	String getProperty(String key, String dft) {
		String val = getProperty(key);

		return null != val ? val : dft;
	}

	/**
	 * Calls the Hashtable method put. Provided for parallelism with the
	 * getProperty method. Enforces use of strings for property keys and values.
	 * The value returned is the result of the Hashtable call to put.
	 * 
	 * @param key
	 *            the key to be placed into this property list
	 * @param val
	 *            the value corresponding to key
	 * @return the previous value of the specified key in this property list, or
	 *         null if it did not have one.
	 */
	Object setProperty(String key, String val) {

		// safe guard the value
		if (StringUtils.isBlank(val)) {
			return defaultProps.props.remove(key);
		} else {
			return defaultProps.props.setProperty(key, val);
		}
	}

	/**
	 * Resets a property value to its default. This means taking the overwrite
	 * value from the inheritance specific storage so the lookup will return the
	 * default value.
	 * <p>
	 * 
	 * @param ctx
	 *            the execution context
	 * @param key
	 *            the property key
	 * @throws CommonException
	 */
	void resetProperty(String key) {
	}

	/**
	 * Writes the values in the properties set to the backing storage.
	 * 
	 */
	void storeProperties() throws CommonException {

		// writes the new value to the backing store
		defaultProps.store();
	}

	/**
	 * This class defines a data structure that contains a Properties object and
	 * the file that backs up the object. The file kept as a full path file
	 * name.
	 */
	protected class FilePropertiesEntry implements Serializable {

		String filename;
		Properties props;

		protected FilePropertiesEntry(String fn, Properties ps) {
			filename = fn;
			props = ps;
		}

		/**
		 * writes the new value to the backing store
		 * 
		 * @throws CommonException
		 */
		protected void store() throws CommonException {
			FileOutputStream fos = null;
			try {
				// check whether a file already exists for the backing store
				File f = new File(filename);

				// if the file is not there yet, create it
				if (!f.exists()) {
					// get the directory path from the file name
					String dirPath = filename.substring(0, filename.lastIndexOf(File.separator));

					// make sure the directory exists
					File dir = new File(dirPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}

					f.createNewFile();
				}

				// open the file for writing
				fos = new FileOutputStream(f);
				props.store(fos, "#");
			} catch (IOException e) {
				throw new CommonException("failed, to write properties to file [" + filename + "]", e);
			} finally {
				// clean up resource
				try {
					if (null != fos)
						fos.close();
				} catch (IOException e) {
					log.warn("failed to close file handle [" + fos + "]", e);
				}
			}
		}
	}

}
