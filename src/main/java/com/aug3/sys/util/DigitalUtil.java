package com.aug3.sys.util;

import java.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;

public class DigitalUtil {
	
	public static final String DECIMAL_FORMATTER = "0.#########";
	
	public static final String DECIMAL_FORMATTER_ISO = "0.0000";
	
	public static final DecimalFormat decimalFormat_iso = new DecimalFormat(DECIMAL_FORMATTER_ISO);
	
	public static final DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMATTER);
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String getISOFormat(double number){
		return decimalFormat_iso.format(number);
	}
	
	/**
	 * 带小数点补全(4位)的数字格式化 
	 * 如：10.091161 --> 10.0912
	 * @param number
	 * @return
	 */
	public static String getFormat(double number){
		return decimalFormat.format(number);
	}
	
	public static String getFormat(double number,String partten){
		if(StringUtils.isBlank(partten) || DECIMAL_FORMATTER.equals(partten)){
			return getFormat(number);
		}else if(DECIMAL_FORMATTER_ISO.equals(partten)){
			return getISOFormat(number);
		}
		DecimalFormat df = new DecimalFormat(partten);
		return df.format(number);
	}
	
}
