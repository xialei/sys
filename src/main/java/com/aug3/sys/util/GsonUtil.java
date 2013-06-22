package com.aug3.sys.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;

/**
 * @author: jacky.chen
 * @date 2012-8-28
 * @deprecated
 */
public class GsonUtil {

	private GsonUtil() {
	}

	private static final Gson DEFAULT_GSON = new Gson();
	private static Gson SERIAL_NULLS_GSON;
	private static Gson SERIAL_NULLS_DATEFORMAT_GSON;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		SERIAL_NULLS_GSON = builder.create();
	}

	/**
	 * 
	 * @Title: toJson
	 * @Description: by default,this method allows serialize null-value
	 *               properties
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		String result = "";
		if (src == null) {
			return result;
		}
		return SERIAL_NULLS_GSON.toJson(src);
	}

	public static String toJson(Object src, String dateFormat) {
		String result = "";
		if (src == null) {
			return result;
		}

		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		try {
			if (SERIAL_NULLS_DATEFORMAT_GSON == null) {
				SERIAL_NULLS_DATEFORMAT_GSON = builder.setDateFormat(dateFormat).create();
			}
			return SERIAL_NULLS_DATEFORMAT_GSON.toJson(src);
		} catch (Exception e) {
			return SERIAL_NULLS_GSON.toJson(src);
		}
	}

	/**
	 * 
	 * @Title: toJson
	 * @Description: to covert a target object to json-formatted text
	 * @param src
	 * @param isSerializeNulls
	 *            : a flag to check if it's allowed to serialize null-value
	 *            properties
	 * @return
	 */
	public static String toJson(Object src, boolean isSerializeNulls) {
		String result = "";
		if (src == null) {
			return result;
		}

		if (isSerializeNulls) {
			result = SERIAL_NULLS_GSON.toJson(src);
		} else {
			result = DEFAULT_GSON.toJson(src);
		}
		return result;
	}

	/**
	 * 
	 * @Title: fromJson
	 * @Description: to covert a JsonElement to a class
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
		return SERIAL_NULLS_GSON.fromJson(json, classOfT);
	}

	/**
	 * 
	 * @Title: fromJson
	 * @Description: to covert a string to a class
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return SERIAL_NULLS_GSON.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type type) {
		return SERIAL_NULLS_GSON.fromJson(json, type);
	}

}
