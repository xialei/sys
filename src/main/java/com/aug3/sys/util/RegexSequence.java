package com.aug3.sys.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Enumeration of regular expression sequences.
 */
public enum RegexSequence {
	NON_NUMERIC("[^0-9]"), NUMERIC("[0-9]"), LETTER("[a-zA-Z]"), WHITESPACE(
			"\\s"), URL("https?://[\\w./]+"), COLOR("[0-9A-Fa-f]{6}");

	private final String sequence;

	private RegexSequence(final String sequence) {
		this.sequence = sequence;
	}

	// Bean methods
	public String getSequence() {
		return sequence;
	}

	public static Set<RegexSequence> getAllRegexSequences() {
		return Collections.unmodifiableSet(EnumSet.of(NON_NUMERIC, NUMERIC,
				LETTER, WHITESPACE, URL, COLOR));
	}

	@Override
	public String toString() {
		return "[Sequence=" + sequence + "]";
	}

}