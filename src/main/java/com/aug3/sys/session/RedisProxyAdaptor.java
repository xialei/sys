package com.aug3.sys.session;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import com.aug3.sys.session.service.Session;
import com.aug3.sys.session.service.SessionEntry;
import com.aug3.sys.session.service.SessionService;

public class RedisProxyAdaptor implements SessionService {

	@Override
	public Future<Session> createSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> endSession(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> deleteSession(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Session> getSession(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> addEntryToSession(String sessionId,
			SessionEntry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> replaceEntryInSession(String sessionId,
			SessionEntry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<SessionEntry> getEntryFromSession(String sessionId, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Map<String, SessionEntry>> getEntriesFromSession(
			String sessionId, Collection<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> deleteKeyFromSession(String sessionId, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
