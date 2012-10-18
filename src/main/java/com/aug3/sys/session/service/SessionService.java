package com.aug3.sys.session.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Implement this session service contract.
 * 
 * Session-data-life-cycle management has been avoided on purpose in order to
 * keep things simple.
 * 
 * @author Roger.xia
 */
public interface SessionService {

	/**
	 * Create a Session which is analogous to a trusted-user login.
	 * 
	 * @return newly created Session
	 */
	Future<Session> createSession();

	/**
	 * End the Session represented by given sessionId. This is analogous to a
	 * trusted-user logout operation.
	 * 
	 * @return success status of operation
	 */
	Future<Boolean> endSession(String sessionId);

	/**
	 * Delete the Session represented by given sessionId.
	 * 
	 * @return success status of operation
	 */
	Future<Boolean> deleteSession(String sessionId);

	/**
	 * Get the Session represented by given sessionId.
	 * 
	 * @return previously created Session by given sessionId
	 */
	Future<Session> getSession(String sessionId);

	/**
	 * Add the given SessionEntry to an already-created Session represented by
	 * given sessionId.
	 * 
	 * @return success status of operation
	 */
	Future<Boolean> addEntryToSession(String sessionId, SessionEntry entry);

	/**
	 * Replace the given SessionEntry in an already-created Session represented
	 * by given sessionId.
	 * 
	 * @return success status of operation
	 */
	Future<Boolean> replaceEntryInSession(String sessionId, SessionEntry entry);

	/**
	 * Search the given key's SessionEntry from an already-created Session
	 * represented by given sessionId.
	 * 
	 * @return previously created SessionEntry searched by given sessionId and
	 *         key
	 */
	Future<SessionEntry> getEntryFromSession(String sessionId, String key);

	/**
	 * Search the given keys' SessionEntry's from an already-created Session
	 * represented by given sessionId.
	 * 
	 * @return previously created SessionEntry's searched by given sessionId and
	 *         keys
	 */
	Future<Map<String, SessionEntry>> getEntriesFromSession(String sessionId,
			Collection<String> keys);

	/**
	 * Delete the given key's SessionEntry from an already-created Session
	 * represented by given sessionId.
	 * 
	 * @return success status of operation
	 */
	Future<Boolean> deleteKeyFromSession(String sessionId, String key);

}
