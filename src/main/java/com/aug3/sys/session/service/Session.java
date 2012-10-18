package com.aug3.sys.session.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Session object serves as the unit of work for SessionService operations.
 * 
 * @author Roger.xia
 */
public interface Session {

	AtomicBoolean getIsActive();

	String getSessionId();

	long getSizeBytes();

	Calendar getLastUpdateDate();

	Collection<SessionEntry> getEntries();

	void addEntry(SessionEntry entry);

	void deleteEntry(SessionEntry entry);

	void replaceEntry(SessionEntry entry);

	void setEntries(Vector<SessionEntry> entries);

	Calendar getCreationDate();

}
