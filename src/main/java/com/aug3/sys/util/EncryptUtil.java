package com.aug3.sys.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author: jacky.chen
 * 
 */
public class EncryptUtil {

	public static final String MD5(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return DigestUtils.md5Hex(data);
	}

	public static final String sha(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return DigestUtils.shaHex(data);
	}

	public static final String sha256(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return DigestUtils.sha256Hex(data);
	}

	public static final String sha384(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return DigestUtils.sha384Hex(data);
	}

	public static final String sha512(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return DigestUtils.sha512Hex(data);
	}

}
