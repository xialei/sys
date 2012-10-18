package com.aug3.sys.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Roger.xia
 * 
 */
public class MD5 {

	public static String md5(String str) {
		String result = null;

		if (!StringUtils.isBlank(str)) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(str.getBytes(), 0, str.length());
				result = String
						.format("%032X", new BigInteger(1, md5.digest()));
			} catch (Exception e) {

			}
		}

		return result;
	}

}
