package com.aug3.sys.session.service;

/**
 * An immutable candidate for Session persistence.
 * 
 * @author Roger.xia
 */
public final class SessionEntry {

	private String key; // TODO: may need to specify upper-bound on key-length
	private Object value; // TODO: may need to specify upper-bound
	private int expirySeconds; // do we need a sensible default?

	public SessionEntry(String key, Object value, int expirySeconds) {
		this.key = key;
		this.value = value;
		this.expirySeconds = expirySeconds;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public int getExpirySeconds() {
		return expirySeconds;
	}

}
