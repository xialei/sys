
/**
 * @Title: GsonUtil.java
 * @Package com.aug3.sys.util
 * Copyright: Copyright (c) 2011 
 * Company:www.chinascopefinancial.com
 * 
 * @author: jacky.chen
 * @date: 2012-8-28 
 * @version: V1.0
 */
package com.aug3.sys.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

 /**
 * @ClassName: GsonUtil
 * @Description: 
 * @author: jacky.chen
 * @date 2012-8-28
 *
 */
public class GsonUtil {
	
	private GsonUtil(){}
	
	private static final Gson DEFAULT_GSON = new Gson();
	private static Gson SERIAL_NULLS_GSON;
	static{
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		SERIAL_NULLS_GSON = builder.create();
	}
	
	/**
	 * 
	  * @Title: toJson
	  * @Description: by default,this method allows serialize null-value properties
	  * @param src
	  * @return
	 */
	public static String toJson(Object src){
		String result = "";
		if(src == null){
			return result;
		}
		return SERIAL_NULLS_GSON.toJson(src);
	}
	
	/**
	 * 
	  * @Title: toJson
	  * @Description: to covert a target object to json-formatted text
	  * @param src
	  * @param isSerializeNulls: a flag to check if it's allowed to serialize null-value properties
	  * @return
	 */
	public static String toJson(Object src,boolean isSerializeNulls){
		String result = "";
		if(src == null){
			return result;
		}
		
		if(isSerializeNulls){
			result = SERIAL_NULLS_GSON.toJson(src);
		}else{
			result = DEFAULT_GSON.toJson(src);
		}
		return result;
	}

}

