package com.aug3.sys.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This container is a suite of tools for character manipulation operations.
 */
public final class CharacterTools {

	/**
	 * Mask given occurrences of a given character in a given string starting at
	 * given offset.
	 * 
	 * @param stringToMask
	 * @param maskingCharacter
	 * @param offset
	 *            Offset to start masking from
	 * @param maskCount
	 *            Number of characters to mask
	 * @return Masked string
	 */
	public static String maskCharacters(String stringToMask,
			final char maskingCharacter, final int offset, final int maskCount) {
		if (isNullOrEmpty(stringToMask)) {
			return CharacterConstants.EMPTY_STRING;
		}

		if (offset == -1 || maskCount <= 0) {
			return stringToMask;
		}

		StringBuffer maskedString = new StringBuffer();
		maskedString.append(stringToMask.substring(0, offset));

		int maskEnd = offset + maskCount;
		int stringToMaskLength = stringToMask.length();
		maskEnd = maskEnd < stringToMaskLength ? maskEnd : stringToMaskLength;
		char[] charsToMask = stringToMask.substring(offset, maskEnd)
				.toCharArray();
		for (int charIter = 0; charIter < charsToMask.length; charIter++) {
			maskedString.append(maskingCharacter);
		}

		maskedString.append(stringToMask.substring(maskEnd));

		return maskedString.toString();
	}

	/**
	 * Remove all non-numeric characters in a given string.
	 */
	public static String stripNonNumericCharacters(String stringWithNonNumerics) {
		if (isNullOrEmpty(stringWithNonNumerics)) {
			return CharacterConstants.EMPTY_STRING;
		}

		return regexReplaceAll(stringWithNonNumerics,
				RegexSequence.NON_NUMERIC, CharacterConstants.EMPTY_STRING);
	}

	/**
	 * Remove all numeric characters in a given string.
	 */
	public static String stripNumericCharacters(String stringWithNumerics) {
		if (isNullOrEmpty(stringWithNumerics)) {
			return CharacterConstants.EMPTY_STRING;
		}

		return regexReplaceAll(stringWithNumerics, RegexSequence.NUMERIC,
				CharacterConstants.EMPTY_STRING);
	}

	/**
	 * Remove all letter characters in a given string.
	 */
	public static String stripLetterCharacters(String stringWithLetters) {
		if (isNullOrEmpty(stringWithLetters)) {
			return CharacterConstants.EMPTY_STRING;
		}

		return regexReplaceAll(stringWithLetters, RegexSequence.LETTER,
				CharacterConstants.EMPTY_STRING);
	}

	/**
	 * Remove all whitespace in a given string.
	 */
	public static String stripWhiteSpace(String stringToClean) {
		if (isNullOrEmpty(stringToClean)) {
			return CharacterConstants.EMPTY_STRING;
		}

		return regexReplaceAll(stringToClean, RegexSequence.WHITESPACE,
				CharacterConstants.EMPTY_STRING);
	}

	/**
	 * Returns true if the searchValue string is found in string.
	 */
	public static boolean isCharInString(String stringToSearchIn,
			final char searchValue) {
		boolean contains = false;
		if (isNullOrEmpty(stringToSearchIn)) {
			return contains;
		}

		contains = stringToSearchIn.trim()
				.contains(String.valueOf(searchValue));

		return contains;
	}

	/**
	 * Check for a given token match in a string of tokens delimited by given
	 * delimiter. If delimiter is null or empty, default to the
	 * {@value CharacterConstants#TOKEN_DELIMITER}
	 * 
	 * This performs a strict match for string case.
	 * 
	 * @param tokens
	 *            Token string to search in
	 * @param delimiter
	 *            Delimiter between tokens
	 * @param searchValue
	 *            String to search among tokens
	 * @returns true if found, false if not found or invalid input parameters.
	 */
	public static boolean isMatchingToken(final String tokens,
			String delimiter, final String searchValue) {
		boolean isMatchingToken = false;
		if (isNullOrEmpty(tokens) && isNullOrEmpty(searchValue)) {
			return isMatchingToken;
		} else {
			delimiter = isNullOrEmpty(delimiter) ? CharacterConstants.TOKEN_DELIMITER
					: delimiter;
			StringTokenizer tokenizer = new StringTokenizer(tokens, delimiter,
					false);
			while (tokenizer.hasMoreTokens()) {
				if (searchValue.equals(tokenizer.nextToken())) {
					return true;
				}
			}

			return isMatchingToken;
		}
	}

	/**
	 * Checks if the given string is empty or null or containing only
	 * whitespace.
	 */
	public static boolean isNullOrEmpty(String string) {
		boolean isNullOrEmpty = false;
		if (string == null || string.trim().length() == 0) {
			isNullOrEmpty = true;
		}

		return isNullOrEmpty;
	}

	/**
	 * Checks if the given string array is empty or null.
	 * 
	 * If deep checking of members is desired, the first occurrence of an empty
	 * or null member halts processing and reports result.
	 */
	public static boolean isNullOrEmpty(String[] stringArray,
			boolean deepCheckMembers) {
		boolean isNullOrEmpty = false;
		if (stringArray == null || stringArray.length == 0) {
			isNullOrEmpty = true;
		} else {
			if (deepCheckMembers) {
				for (int iter = 0; iter < stringArray.length; iter++) {
					isNullOrEmpty = isNullOrEmpty(stringArray[iter]);
					if (isNullOrEmpty) {
						break;
					}
				}
			}
		}

		return isNullOrEmpty;
	}

	/**
	 * Left-pad a given string with any occurrences of a given character until
	 * the resulting string is as long as the desiredStringLength.
	 * 
	 * If length of stringToPad is less than length of the pad, return the pad
	 * itself.
	 * 
	 * If desiredStringLength is less than length of stringToPad, return
	 * stringToPad.
	 */
	public static String padToLeft(final char pad, String stringToPad,
			final int desiredStringLength) {
		if (isNullOrEmpty(stringToPad)) {
			StringBuffer onlyPads = new StringBuffer();
			for (int charCounter = desiredStringLength; charCounter-- > 0;) {
				onlyPads.append(pad);
			}

			return onlyPads.toString();
		}

		if (desiredStringLength <= 0) {
			return stringToPad;
		}

		int charsToPrepend = desiredStringLength - stringToPad.length();

		if (charsToPrepend > 0) {
			StringBuffer buffer = new StringBuffer();
			while (charsToPrepend-- != 0) {
				buffer.append(pad);
			}

			buffer.append(stringToPad);
			return buffer.toString();
		} else {
			return stringToPad;
		}
	}

	/**
	 * Trim all occurrences of the leading character in given string.
	 * 
	 * This algorithm is designed to aggressively eats all whitespace it
	 * encounters if it keeps finding multiple occurrences of spaced
	 * charToRemove.
	 */
	public static String trimLeadingChar(String stringToClean,
			final char charToRemove) {
		if (isNullOrEmpty(stringToClean)) {
			return stringToClean;
		}

		// Current pointer
		int currentPosition = 0;
		String cleanedString = stringToClean;
		char[] allCharacters = stringToClean.toCharArray();
		for (char charToCompare : allCharacters) {
			// No tolerance for mismatch
			if (charToCompare == charToRemove
					|| charToCompare == CharacterConstants.WSPACE_CHAR) {
				cleanedString = stringToClean.substring(currentPosition++)
						.trim();
			} else {
				break;
			}
		}

		return cleanedString;
	}

	/**
	 * Provides toString() implementation for an array of objects.
	 */
	public static String arrayToString(final Object[] arrayOfObjects) {
		return Arrays.deepToString(arrayOfObjects);
	}

	/**
	 * Reads a file in to a String.
	 */
	public static String readFileToString(final String fileName) {
		CharBuffer charBuffer = null;
		try {
			FileInputStream iStream = new FileInputStream(fileName);
			FileChannel channel = iStream.getChannel();

			ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY,
					0, (int) channel.size());
			charBuffer = Charset.forName("8859_1").newDecoder()
					.decode(byteBuffer);
		} catch (FileNotFoundException fileNotFoundException) {
			throw new RuntimeException(fileNotFoundException.getMessage(),
					fileNotFoundException);
		} catch (IOException ioException) {
			throw new RuntimeException(ioException.getMessage(), ioException);
		}

		return charBuffer.toString();
	}

	/**
	 * Sorts an array of String's by their length in preferred order.
	 */
	public static String[] sortStringsByLength(final String[] stringsToSort,
			final boolean sortDescending) {
		if (isNullOrEmpty(stringsToSort, true)) {
			return new String[0];
		}

		String[] arrayCopyToSort = new String[stringsToSort.length];
		System.arraycopy(stringsToSort, 0, arrayCopyToSort, 0,
				stringsToSort.length);
		Arrays.sort(arrayCopyToSort, new Comparator<String>() {
			public int compare(String stringA, String stringB) {
				int result = 0;
				if (sortDescending) {
					result = stringB.length() - stringA.length();
				} else {
					result = stringA.length() - stringB.length();
				}

				return result;
			}
		});

		return arrayCopyToSort;
	}

	/**
	 * Replace all occurrences of given pattern in given string with given
	 * replacement string.
	 */
	public static String regexReplaceAll(String stringToEdit,
			final RegexSequence regexToMatch, final String replacement) {
		Pattern pattern = Pattern.compile(regexToMatch.getSequence());
		Matcher matcher = pattern.matcher(stringToEdit);

		return matcher.replaceAll(replacement);
	}

	/**
	 * This method replaces all occurences of a String with another String. Each
	 * character in the searchValue is treated as an individual token; the whole
	 * String is not evaluated
	 * 
	 * @param str
	 *            The String that the search and replace will occur on.
	 * @param searchValue
	 *            The value that should be replaced
	 * @param replaceValue
	 *            The value to replace the search string with.
	 * @return null if any of the parameters are null. Otherwise a String with
	 *         all occurences of the searchValue replaced by the replaceValue.
	 */
	public static String replaceAllOccurences(String str, String searchValue,
			String replaceValue) {
		if (str == null || searchValue == null || replaceValue == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, searchValue, true);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.length() == 1 && searchValue.indexOf(token) > -1) {
				sb.append(replaceValue);
			} else {
				sb.append(token);
			}
		}
		return sb.toString();
	}

	private final class CharacterConstants {
		private final static char WSPACE_CHAR = ' ';
		private final static String EMPTY_STRING = "";
		private final static String TOKEN_DELIMITER = ",";
	}

	/*
	 * Prevent instance escape at all costs.
	 */
	private CharacterTools() {
	}

}